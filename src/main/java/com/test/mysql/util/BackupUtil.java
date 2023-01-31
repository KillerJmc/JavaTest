package com.test.mysql.util;

import com.jmc.io.Files;
import com.jmc.lang.Run;
import lombok.extern.slf4j.Slf4j;

/**
 * MySQL备份工具
 * @author Jmc
 */
@Slf4j
public class BackupUtil {
    /**
     * 备份到一个sql文件并退出的模式字符串（参数为库名称和sql文件路径）
     */
    private static final String BACKUP_AND_EXIT = "cmd /c \"mysqldump -uroot -proot %s > %s\"";

    /**
     * 备份数据库到sql文件
     * @param outputDir 输出sql文件的文件夹，每个备份sql文件的名称就是数据库名
     */
    public static void backupToSql(String outputDir, String... dbNames) {
        // 创建输出文件夹
        Files.mkdirs(outputDir);

        for (String dbName : dbNames) {
            // sql文件名就是数据库名
            var sqlPath = outputDir + "/" + dbName + ".sql";

            // 备份sql的命令
            var backupSqlCmd = BACKUP_AND_EXIT.formatted(dbName, sqlPath);

            log.info("Executing: {}", backupSqlCmd);
            Run.exec(backupSqlCmd);
        }
        log.info("Backup finished!");
    }

    public static void main(String[] args) {
        BackupUtil.backupToSql(
                "D:/Projects/IdeaProjects/seckill-system/docker/mysql/sql",
                "erupt", "seckill_account", "seckill_payment", "seckill_mock", "seckill_service"
        );
    }
}

