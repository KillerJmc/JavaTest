package com.test.phone;

import com.jmc.io.Files;
import com.test.main.Tools;

import java.io.File;
import java.util.Scanner;

public class SrcHandler {
    private static final String QQDownloadPath = "D:/Programs/Tencent/QQ/Records/1934497212/FileRecv/MobileFile/";
    private static final String ProjectRootPath = "D:/Programs/Projects/IdeaProjects/JavaTest/src/";
    private static final String DesktopPath = "C:/Users/Jmc/Desktop/";

    static {
        Files.basicDetails = false;
    }

    public static void update() {
        String zipPath = QQDownloadPath + "src.zip";
        System.out.println("\n正在等待手机QQ发送src.zip...\n");

        while (!new File(zipPath).exists()) Tools.sleep(100);
        Files.delete(ProjectRootPath + "com/jmc");
        Files.unzip(zipPath, ProjectRootPath);
        Files.delete(zipPath);
        System.out.println("\n已完成!\n");

        Files.basicDetails = true;
    }

    public static void push() {
        Files.copy(ProjectRootPath + "com/", DesktopPath);
        Files.delete(DesktopPath + "com/test");
        Files.zip(DesktopPath + "com/", DesktopPath + "src.zip", false);
        Files.delete(DesktopPath + "com/");
        System.out.println("\nsrc.zip已创建于桌面\n");
        System.out.println("\n请使用本机QQ手动推送至我的Android手机！\n");
        System.out.println("\n推送完毕后输入y删除src.zip\n");
        if (new Scanner(System.in).next().equals("y")) Files.delete(DesktopPath + "src.zip");
        System.out.println("\n已完成！\n");

        Files.basicDetails = true;
    }
}
