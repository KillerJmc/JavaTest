package com.test.mysql.util;

import com.jmc.io.Files;

import java.io.File;

/**
 * MySQL工具类
 * @author Jmc
 */
public class MySQLUtils {
    /**
     * 重命名数据库（保留先前的）
     * @param oldName 旧名称
     * @param newName 新名称
     */
    public static void renameDB(String oldName, String newName) {
        var tempDir = "./temp/";
        Files.mkdirs(tempDir);

        var oldDBFile = new File(tempDir + oldName + ".sql");

        BackupUtil.backupToSql(tempDir, oldName);
        Files.rename(oldDBFile, newName);
        RestoreUtil.fromSql(tempDir);
        Files.delete(tempDir);
    }

    public static void main(String[] args) {
        renameDB("nsms_core", "nsms");
    }
}
