package com.test.mysql.util;

import com.jmc.io.Files;
import com.jmc.lang.Run;
import lombok.extern.slf4j.Slf4j;

/**
 * MySQL恢复工具
 */
@Slf4j
public class RestoreUtil {
    /**
     * 只执行一次MySQL命令就结束的模式字符串（参数为sql命令）
     */
    private static final String EXEC_ONCE_AND_EXIT = "mysql -uroot -proot -e \"%s\"";

    /**
     * 创建数据库之后就退出的模式字符串（参数是数据库名称）
     */
    private static final String CREATE_DB_AND_EXIT = EXEC_ONCE_AND_EXIT
            .formatted("create database %s");

    /**
     * 还原一个sql文件并退出的模式字符串（参数为库名称和sql文件路径）
     */
    private static final String RESTORE_AND_EXIT = "cmd /c \"mysql -uroot -p %s -proot < %s\"";

    /**
     * 还原一个目录内存放的所有sql文件
     * @param sqlDirectory 包含sql文件的文件夹，每个sql文件的名称就是数据库名
     */
    public static void fromSql(String sqlDirectory) {
        Files.findFiles(sqlDirectory).forEach(sqlFile -> {
            // 数据库名称就是sql文件名称
            var dbName = sqlFile.getName().replace(".sql", "");

            // 创建数据库命令
            var createDBCmd = CREATE_DB_AND_EXIT.formatted(dbName);
            // 还原sql文件命令
            var restoreSqlCmd = RESTORE_AND_EXIT.formatted(dbName, sqlFile.getAbsolutePath());

            log.info("Executing: {}", createDBCmd);
            Run.exec(createDBCmd);
            log.info("Executing: {}", restoreSqlCmd);
            Run.exec(restoreSqlCmd);
        });
        log.info("Restore finished!");
    }

    public static void main(String[] args) {
        fromSql("D:/Projects/IdeaProjects/seckill-system/docker/mysql/sql");
    }
}
