package com.test.jlink;

import com.jmc.lang.Objs;
import com.jmc.lang.Run;

import java.io.File;
import java.util.Scanner;

/**
 * 把JLink镜像打包为可执行文件 <br>
 * 注：需要配置Java环境和安装WiX
 * @author Jmc
 */
@SuppressWarnings({"unused", "UnusedReturnValue"})
public class JLinkPackager {
    /**
     * 存放最终的执行命令
     */
    private StringBuilder cmd;

    /**
     * 初始化命令
     */
    private final String INIT_CMD = "jpackage ";

    private JLinkPackager() {}

    /**
     * 返回构造器
     * @return 构造器
     */
    public static JLinkPackager builder() {
        var instance = new JLinkPackager();
        // jpackage
        instance.cmd = new StringBuilder(instance.INIT_CMD);
        return instance;
    }

    /**
     * 软件名称
     */
    public JLinkPackager name(String name) {
        // -n "name"
        cmd.append("-n \"").append(name).append("\" ");
        return this;
    }

    /**
     * 打包类型枚举
     */
    public enum PackageType {
        /**
         * 绿色版镜像
         */
        APP_IMAGE("app-image"),

        /**
         * exe安装软件
         */
        EXE("exe"),

        /**
         * msi安装软件
         */
        MSI("msi");

        /**
         * 类型名称
         */
        public final String typeName;

        PackageType(String typeName) {
            this.typeName = typeName;
        }
    }

    /**
     * 打包类型
     */
    public JLinkPackager packageType(PackageType pType) {
        // -t type
        cmd.append("-t ").append(pType.typeName).append(' ');
        return this;
    }

    /**
     * 指定模块和启动类
     * @param moduleName 模块名
     * @param startupClassFullName 启动类全名
     */
    public JLinkPackager module(String moduleName, String startupClassFullName) {
        // -m moduleName/startupClassName
        cmd.append("-m ")
                .append(moduleName)
                .append('/')
                .append(startupClassFullName)
                .append(' ');
        return this;
    }

    /**
     * JLink镜像路径
     */
    public JLinkPackager jLinkImage(String imageAbsolutePath) {
        // --runtime-image "imageAbsolutePath"
        cmd.append("--runtime-image \"").append(imageAbsolutePath).append("\" ");
        return this;
    }

    /**
     * 打包输出路径
     */
    public JLinkPackager outputDir(String outputDirAbsolutePath) {
        // -d "destPath"
        cmd.append("-d \"").append(outputDirAbsolutePath).append("\" ");
        return this;
    }

    /**
     * 软件供应商
     */
    public JLinkPackager vendor(String vendor) {
        // --vendor "vendor"
        cmd.append("--vendor \"").append(vendor).append("\" ");
        return this;
    }

    /**
     * 版本信息
     */
    public JLinkPackager version(String version) {
        // --app-version "version"
        cmd.append("--app-version \"").append(version).append("\" ");
        return this;
    }

    /**
     * 图标
     */
    public JLinkPackager icon(String iconAbsolutePath) {
        // --icon "iconAbsolutePath"
        cmd.append("--icon \"").append(iconAbsolutePath).append("\" ");
        return this;
    }

    /**
     * 软件介绍
     */
    public JLinkPackager desc(String description) {
        // --description "description"
        cmd.append("--description \"").append(description).append("\" ");
        return this;
    }

    /**
     * 版权信息
     */
    public JLinkPackager copyright(String copyright) {
        // --copyright "copyright"
        cmd.append("--copyright \"").append(copyright).append("\" ");
        return this;
    }

    /**
     * VM参数，可调用多次
     */
    public JLinkPackager javaOptions(String option) {
        // --java-options option
        cmd.append("--java-options ").append(option).append(' ');
        return this;
    }

    /**
     * 指定为控制台应用
     */
    public JLinkPackager winConsole() {
        // --win-console
        cmd.append("--win-console ");
        return this;
    }

    /**
     * 创建桌面图标
     * @param askUser 是否询问用户是否创建
     */
    public JLinkPackager winShortcut(boolean askUser) {
        // --win-shortcut (--win-shortcut-prompt)
        cmd.append("--win-shortcut ");
        if (askUser) {
            cmd.append("--win-shortcut-prompt ");
        }
        cmd.append(' ');
        return this;
    }

    /**
     * 让用户选择安装路径
     */
    public JLinkPackager winDirChooser() {
        // --win-dir-chooser
        cmd.append("--win-dir-chooser ");
        return this;
    }

    /**
     * 开始构建
     */
    public void start() {
        Run.exec(cmd.toString());
    }

    public static void buildInteractively() {
        checkEnv();

        var builder = builder();
        var in = new Scanner(System.in);

        System.err.print("""
        ---------------------------------JLink Packager------------------------------------
        软件名称（必填）：""");
        builder.name(in.nextLine());

        boolean isAppImage = false;
        System.err.print("""
        
        打包类型（必选）：
        1. exe安装包 2. msi安装包 3. 绿色版镜像

        请输入你的选择：""");
        builder.packageType(switch (in.nextInt()) {
            case 1 -> PackageType.EXE;
            case 2 -> PackageType.MSI;
            case 3 -> {
                isAppImage = true;
                yield PackageType.APP_IMAGE;
            }
            default -> throw new IllegalArgumentException("参数输入错误！");
        });
        in.nextLine();

        System.err.print("\n模块名称（必填）：");
        var moduleName = in.nextLine();
        System.err.print("\n模块启动类（全类名，必填）：");
        var appName = in.nextLine();

        Objs.throwsIfNullOrEmpty("参数输入为空！", moduleName, appName);
        builder.module(moduleName, appName);

        System.err.print("\nJLink镜像（绝对路径，必填）：");
        var imageAbsolutePath = in.nextLine();
        Objs.throwsIfNullOrEmpty("参数输入为空！", imageAbsolutePath);
        builder.jLinkImage(imageAbsolutePath);

        System.err.print("\n打包输出路径（绝对路径，必填）：");
        var outputDirAbsolutePath = in.nextLine();
        Objs.throwsIfNullOrEmpty("参数输入为空！", outputDirAbsolutePath);
        builder.outputDir(outputDirAbsolutePath);

        System.err.print("\n软件供应商（可选）：");
        var vendor = in.nextLine();
        if (!vendor.isBlank()) {
            builder.vendor(vendor);
        }

        System.err.print("\n软件版本（可选）：");
        var version = in.nextLine();
        if (!version.isBlank()) {
            builder.version(version);
        }

        System.err.print("\n软件图标（绝对路径，可选）：");
        var iconAbsolutePath = in.nextLine();
        if (!iconAbsolutePath.isBlank()) {
            builder.icon(iconAbsolutePath);
        }

        System.err.print("\n软件介绍（可选）：");
        var desc = in.nextLine();
        if (!desc.isBlank()) {
            builder.desc(desc);
        }

        System.err.print("\n版权声明（可选）：");
        var copyright = in.nextLine();
        if (!copyright.isBlank()) {
            builder.copyright(copyright);
        }

        System.err.print("\nVM参数（可选）：");
        var option = in.nextLine();
        if (!option.isBlank()) {
            builder.javaOptions(option);
        }

        System.err.print("""
        
        运行时显示控制台：
        1. 是 2. 否

        请输入你的选择：""");

        switch (in.nextInt()) {
            case 1 -> builder.winConsole();
            case 2 -> {}
            default -> throw new IllegalArgumentException("参数输入错误！");
        }
        in.nextLine();

        if (!isAppImage) {
            System.err.print("""
            
            是否创建桌面图标：
            1. 是 2. 否
    
            请输入你的选择：""");

            switch (in.nextInt()) {
                case 1 -> {
                    System.err.print("""
                    
                    是否自动创建桌面图标：
                    1. 是 2. 否，由用户自己决定
            
                    请输入你的选择：""");

                    switch (in.nextInt()) {
                        case 1 -> builder.winShortcut(false);
                        case 2 -> builder.winShortcut(true);
                        default -> throw new IllegalArgumentException("参数输入错误！");
                    }
                }
                case 2 -> {}
                default -> throw new IllegalArgumentException("参数输入错误！");
            }
            in.nextLine();

            System.err.print("""
            
            是否让用户选择安装路径：
            1. 是 2. 否
    
            请输入你的选择：""");

            switch (in.nextInt()) {
                case 1 -> builder.winDirChooser();
                case 2 -> {}
                default -> throw new IllegalArgumentException("参数输入错误！");
            }
            in.nextLine();
        }

        System.err.println("\n正在构建...");
        builder.start();
        System.err.println("""
        
        构建成功！
        
        ---------------------------------JLink Packager------------------------------------
        """);
    }

    /**
     * 检查环境
     */
    private static void checkEnv() {
        // 检查jpackage
        Run.execToStr("jpackage");

        // 检查WiX
        var programPath = "C:/Program Files (x86)";

        var programDirs = new File(programPath).listFiles();
        if (programDirs == null) {
            throw new RuntimeException("\"" + programPath + "\" can't read!");
        }

        var foundWix = false;
        for (var f : programDirs) {
            if (f.getName().contains("WiX Toolset")) {
                foundWix = true;
                break;
            }
        }

        if (!foundWix) {
            throw new RuntimeException("WiX environment not found!");
        }
    }

    public static void main(String[] args) {
        JLinkPackager.buildInteractively();
    }
}
