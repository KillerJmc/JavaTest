package com.test.mysql.jdbc;

import com.test.mysql.orm.sorm.utils.CloseUtils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.jmc.lang.extend.Tries.tryThis;

public class JDBCUtil {
    private Connection conn;
    private Properties pros;

    public JDBCUtil(Class<?> propertyPosition) {
        pros = new Properties();
        tryThis(() -> pros.load(new FileInputStream("src/com/test/mysql/orm/sorm/db.properties")));
    }
    
    public synchronized Connection getMySQLConn() {
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

    private String readRS(ResultSet rs) {
        try {
            int fieldCount = rs.getMetaData().getColumnCount();
            var sb = new StringBuilder();

            while (rs.next()) {
                for (int i = 1; i <= fieldCount; i++) {
                    Object o = rs.getObject(i);

                    if (o == null) {
                        sb.append("null ");
                        continue;
                    }

                    if (o instanceof byte[]) {
                        var bigText = new String((byte[]) o);
                        sb.append("BigText（");
                        if (bigText.length() > 20) {
                            sb.append(bigText.substring(0, 20) + "...");
                        } else {
                            sb.append(bigText);
                        }
                        sb.append("）");
                    } else {
                        String content = o.toString();
                        if (content.length() > 30) {
                            sb.append("BigText（")
                                    .append(content.substring(0, 20) + "...")
                                    .append("） ");
                        } else {
                            sb.append(content + " ");
                        }
                    }
                }
                sb.append("\n");
            }
            CloseUtils.closeAll(rs);
            return sb.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String select(String tableName) {
        try (var ps = getMySQLConn().prepareStatement("select * from " + tableName)) {
            ResultSet rs = ps.executeQuery();
            return readRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void exec(String sql) {
        try (var stm = getMySQLConn().createStatement()) {
            stm.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getResultSet(String sql, Object... params) {
        try {
            var ps = getMySQLConn().prepareStatement(sql);
            for (int i = 1; i <= params.length; i++) ps.setObject(i, params[i - 1]);
            ResultSet rs = ps.executeQuery();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String execWithParams(String sql, Object... params) {
        try (var ps = getMySQLConn().prepareStatement(sql)) {
            for (int i = 1; i <= params.length; i++) ps.setObject(i, params[i - 1]);
            ResultSet rs = ps.executeQuery();
            return readRS(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() {
        tryThis(() -> conn.close());
        conn = null;
    }
}
