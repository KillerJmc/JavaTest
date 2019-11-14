package com.test.JDBC;

import com.jmc.chatserver.CloseUtils;
import com.test.Main.Tools;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class JDBCUtil {
    private static Connection conn;
    private static Properties pros;

    static {
        pros = new Properties();
        Tools.tryThis(() -> {
            pros.load(new FileInputStream(Tools.getFilePath(JDBCUtil.class, "db.properties")));
        });
    }

    public static synchronized Connection getMySQLConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(
                        pros.getProperty("mySQLURL"),
                        pros.getProperty("mySQLUser"),
                        pros.getProperty("mySQLPwd")
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    public static void exec(String sql, List list) {
        try (var ps = getMySQLConn().prepareStatement(sql)) {
            for (int i = 1; i <= list.size(); i++) ps.setObject(i, list.get(i - 1));
            ps.execute();
            CloseUtils.closeAll(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void simpleExec(String sql) {
        try (var stm = getMySQLConn().createStatement()) {
            stm.execute(sql);
            CloseUtils.closeAll(stm);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        Tools.tryThis(() -> conn.close());
        conn = null;
    }
}
