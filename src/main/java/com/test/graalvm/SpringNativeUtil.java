package com.test.graalvm;

import com.jmc.io.Files;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Spring Native生成工具类 <br>
 * 参考：<a href="https://docs.spring.io/spring-native/docs/current/reference/htmlsingle/#_with_code_native_image_code">Spring Native Doc</a>
 */
@Slf4j
public class SpringNativeUtil {
    /**
     * 从Spring Jar文件生成exe二进制文件
     * @param jarPath jar文件路径
     * @param binName 生成的二进制文件名称
     * @return 需要执行的命令（需要提前安装GraalVM，并在VS命令行执行）
     */
    public static String generateFromJar(String jarPath, String binName) {
        // 执行的命令
        var command = new StringBuilder();

        // 生成二进制根目录
        var binPath = Path.of(jarPath).getParent();

        // jar文件名称
        var jarName = new File(jarPath).getName().replace(".jar", "");
        // jar文件根目录
        var jarRootPath = binPath.resolve(jarName);

        log.info("正在解压jar文件...");
        // 解压jar文件
        Files.unzip(jarPath, jarRootPath.toString());
        log.info("解压完成...");

        // cd $jarRootPath && mv META-INF BOOT-INF/classes
        Files.move(jarRootPath.resolve("META-INF").toString(), jarRootPath.resolve("BOOT-INF/classes").toString());

        // 找到$jarRootPath/BOOT-INF/lib的所有jar文件依赖，用分号连接
        var libs = Optional.of(jarRootPath.resolve("BOOT-INF/lib").toFile())
                .map(File::listFiles)
                .map(Stream::of)
                .map(stream -> stream.map(File::getAbsolutePath).reduce((a, b) -> a + ";" + b).orElseThrow())
                .orElseThrow();

        return command
                // cd $jarRootPath &&
                .append("cd /d ").append(binPath).append(" &&")
                // native-image -H:Name=$binName
                .append(" native-image -H:Name=").append(binName)
                // -cp $jarRootPath/BOOT-INF/classes;
                .append(" -cp ").append(jarRootPath.resolve("BOOT-INF/classes")).append(";")
                // jarPath1;jarPath2;jarPath3
                .append(libs).toString();
    }
}
