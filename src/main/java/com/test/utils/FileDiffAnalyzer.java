package com.test.utils;

import com.jmc.aop.DefaultArg;
import com.jmc.io.Files;
import com.jmc.lang.Objs;
import com.jmc.lang.ref.Func;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件差别分析类
 * @author Jmc
 */
public class FileDiffAnalyzer {
    /**
     * 判断两个文件是否相等
     * @param filePath1 文件路径1
     * @param filePath2 文件路径2
     * @return 两个文件是否相等
     */
    public static boolean isFileTheSame(String filePath1, String filePath2) {
        // 检查是否都是文件
        if (!Files.isFile(filePath1) && !Files.isFile(filePath2)) {
            return false;
        }

        // 判断两个文件是否相等
        var bs1 = Files.readToBytes(filePath1);
        var bs2 = Files.readToBytes(filePath2);
        return bs1.length == bs2.length && Arrays.equals(bs1, bs2);
    }

    /**
     * 判断两个文件夹是否相等（文件数、目录数和总大小相等）
     * @param dirPath1 文件夹路径1
     * @param dirPath2 文件夹路径2
     * @return 两个文件夹是否相等
     */
    public static boolean isDirTheSame(String dirPath1, String dirPath2) {
        // 检查是否都是文件夹
        if (!Files.isDir(dirPath1) && !Files.isDir(dirPath2)) {
            return false;
        }

        // 判断两个文件夹是否相等
        return Files.getFileInfo(dirPath1).equals(Files.getFileInfo(dirPath2));
    }

    /**
     * 获取两个文件夹不同部分的详细报告
     * @param dirPath1 第一个文件夹
     * @param dirPath2 第二个文件夹
     * @param depth 搜索深度（默认为2）
     * @return 详细报告的字符串
     */
    public static String dirDiffReport(String dirPath1, String dirPath2, @DefaultArg("2") Long depth) {
        Objs.throwsIfNullOrEmpty("请打开默认参数特性！", depth);

        // 检查是否都是文件夹
        if (!Files.isDir(dirPath1) && !Files.isDir(dirPath2)) {
            throw new RuntimeException("非文件夹路径！");
        }

        // 将文件列表转换成原来路径的相对路径字符串
        var filesToRelative = Func.of((List<File> files, Path rootPath) ->
                files.stream()
                        .map(File::toPath)
                        .map(rootPath::relativize)
                        .map(Path::toString)
                        .toList()
        );

        // 将目录展开一层收集起来并转换成对于原来路径的相对路径
        var listFilesToRelative = Func.of((Path rootPath) ->
                filesToRelative.invoke(Files.list(rootPath.toFile()), rootPath)
        );

        // 两个文件夹相互而言不存在的文件/文件夹的相对路径保存类
        class NotExistsRelatives {
            final List<String> srcNotExists;
            final List<String> desNotExists;

            // 获取列出单层目录后，两个文件夹相互对比各自没有的文件/文件夹
            NotExistsRelatives(Path srcPath, Path desPath) {
                var srcRelatives = listFilesToRelative.invoke(srcPath);
                var desRelatives = listFilesToRelative.invoke(desPath);
                this.srcNotExists = new ArrayList<>(desRelatives) {{ removeAll(srcRelatives); }};
                this.desNotExists = new ArrayList<>(srcRelatives) {{ removeAll(desRelatives); }};
            }
        }

        // 获取两个文件夹单层列出后不同的文件/文件夹的相对路径
        class FileDiff {
            final List<String> diffRelatives;

            // 判断两个路径的不同，返回不同的相对路径列表（只包含两个路径共有的文件/文件夹）
            FileDiff(String path1, String path2) {
                var fileList1 = Files.list(path1);
                var fileList2 = Files.list(path2);

                var getRelative = Func.of((String path, String rootPath) ->
                        Path.of(rootPath).relativize(Path.of(path)).toString()
                );

                // 文件列表1相对于文件列表2不同的文件/文件夹列表
                var list1DiffFiles = fileList1.stream()
                        .filter(f1 -> {
                            for (var f2 : fileList2) {
                                // 找到对应文件/文件夹f2
                                if (getRelative.invoke(f2.getAbsolutePath(), path2).equals(getRelative.invoke(f1.getAbsolutePath(), path1))) {
                                    // 返回是否有差异
                                    return f1.isFile() ? Files.getLength(f1) != Files.getLength(f2)
                                            : !isDirTheSame(f1.getAbsolutePath(), f2.getAbsolutePath());
                                }
                            }
                            // 找不到文件就直接排除
                            return false;
                        })
                        .toList();

                // 获取相对路径列表
                this.diffRelatives = filesToRelative.invoke(list1DiffFiles, Path.of(path1));
            }
        }

        var res = new StringBuilder();
        var lineBreak = "\n";

        var fileDiff = new FileDiff(dirPath1, dirPath2);
        if (!fileDiff.diffRelatives.isEmpty()) {
            res.append(lineBreak);
        }
        fileDiff.diffRelatives.forEach(relative -> {
            res.append("当前文件夹：“%s” 和 “%s”\n  - 需要更新的文件/文件夹：“%s”\n".formatted(dirPath1, dirPath2, relative));
        });

        // 判断是否需要递归
        if (depth > 1) {
            fileDiff.diffRelatives.forEach(relative -> {
                var newDirPath1 = Path.of(dirPath1, relative).toString();
                var newDirPath2 = Path.of(dirPath2, relative).toString();
                var newRes = dirDiffReport(newDirPath1, newDirPath2, depth - 1);
                res.append(newRes);
            });
        }

        var notExistsRelatives = new NotExistsRelatives(Path.of(dirPath1), Path.of(dirPath2));
        if (!notExistsRelatives.srcNotExists.isEmpty()) {
            res.append(lineBreak);
        }

        notExistsRelatives.srcNotExists.forEach(relative ->
            res.append("当前文件夹：“%s”\n  - 多出文件/文件夹：“%s”\n".formatted(dirPath2, relative))
        );

        if (!notExistsRelatives.desNotExists.isEmpty()) {
            res.append(lineBreak);
        }

        notExistsRelatives.desNotExists.forEach(relative ->
            res.append("当前文件夹：“%s”\n  - 多出文件/文件夹：“%s”\n".formatted(dirPath1, relative))
        );

        return res.toString();
    }

    public static void main(String[] args) {
        var path1 = "D:\\Temp\\ffmpeg";
        var path2 = "D:\\Temp\\ffmpeg - 副本";
        System.out.println(dirDiffReport(path1, path2, 2L));
    }
}



