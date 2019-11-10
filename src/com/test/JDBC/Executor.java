package com.test.JDBC;

import com.test.Main.Tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Executor {
    public static Connection conn;

    private static synchronized Connection getConn() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test_jdbc?useSSL=false", "root", "123456");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    @FunctionalInterface
    public interface Prepare {
        List prepare();
    }

    public static void exec(String sql, Prepare p) {
        try (var ps = getConn().prepareStatement(sql)) {
            List list = p.prepare();
            for (int i = 1; i <= list.size(); i++) ps.setObject(i, list.get(i - 1));
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void simpleExec(String sql) {
        try (Statement stm = getConn().createStatement()) {
            stm.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeConn() {
        Tools.tryThis(() -> conn.close());
    }
}
