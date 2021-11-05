package com.test.apply.exam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author Jmc
 */
public class LambdaPlus {
    /*
        Students

        现在有一个 students.dat 文件，其中内容是每行一个人名。
        请你完成下面的任务：

        · 将人名按字典序进行排序
        · 去掉以 B 开头 或以 n 结尾的人名
        · 将剩下的所有人名全部转换为大写，收集到 List 中并输出
     */

    public static void main(String[] args) throws IOException {
        var list = Files
                .lines(Path.of("temp/students.dat"))
                .filter(t -> !t.startsWith("B") && !t.endsWith("n"))
                .map(String::toUpperCase)
                .sorted()
                .toList();

        list.forEach(System.out::println);
    }
}
