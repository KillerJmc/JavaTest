package com.test.mysql.jdbc;

import com.jmc.lang.Strs;
import com.jmc.lang.Tries;
import com.jmc.lang.reflect.Reflects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Jdbc工具类
 * @author Jmc
 * Date: 2021.8.3
 */
@SuppressWarnings("unused")
public class JdbcUtils {
    private static final String url;
    private static final String username;
    private static final String password;

    private static final Connection DEFAULT_CONNECTION;

    static {
        var propPath = JdbcUtils.class.getPackageName().replace('.', '/') + "/jdbc";
        var rb = ResourceBundle.getBundle(propPath);

        url = rb.getString("jdbc.url");
        username = rb.getString("jdbc.username");
        password = rb.getString("jdbc.password");

        DEFAULT_CONNECTION = Tries.tryReturnsT(() -> DriverManager.getConnection(url, username, password));
        Runtime.getRuntime().addShutdownHook(new Thread(() -> Tries.tryThis(DEFAULT_CONNECTION::close)));
    }

    public static void execute(String sql, Object... args) {
        executeSql(sql, args);
    }

    public static <T> List<T> queryList(String sql, Class<T> returnType, Object... args) {
        var rs = executeSql(sql, args);
        List<T> res = new ArrayList<>();

        return Tries.tryReturnsT(() -> {
            var metaData = rs.getMetaData();

            while (rs.next()) {
                T obj = returnType.getDeclaredConstructor().newInstance();
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    Reflects.invokeMethod(obj,
                            "set" + Strs.capitalize(metaData.getColumnName(i)),
                            rs.getObject(i)
                    );
                }
                res.add(obj);
            }

            rs.close();
            return res;
        });
    }

    public static <T> T queryOne(String sql, Class<T> returnType, Object... args) {
        return queryList(sql, returnType, args).get(0);
    }

    @SuppressWarnings("unchecked")
    public static <T> T queryObject(String sql, Object... args) {
        try (var rs = executeSql(sql, args)) {
            if (rs.next()) {
                return (T) rs.getObject(1);
            }
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    private static ResultSet executeSql(String sql, Object... args) {
        return Tries.tryReturnsT(() -> {
            var ps = DEFAULT_CONNECTION.prepareStatement(sql);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> Tries.tryThis(ps::close)));

            if (args.length > 0) {
                for (int i = 0; i < args.length; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }

            if (sql.toUpperCase().startsWith("SELECT")) {
                return ps.executeQuery();
            } else {
                ps.executeUpdate();
                return null;
            }
        });
    }

    public static Connection getDefaultConnection() {
        return DEFAULT_CONNECTION;
    }
}
