package com.test.mysql.sorm.conn;

import com.jmc.lang.Strs;
import com.test.mysql.sorm.config.Configuration;
import com.test.mysql.sorm.pool.DBConnPool;

import java.sql.Connection;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Keep the Management of the Connected Objects according to the configuration information.
 * (add the ThreadPool Function)
 * @author Jmc
 */
public class DBManager {
    /**
     * configuration Object
     */
    private static final Configuration conf;

    /**
     * connection pool Object
     */
    private static DBConnPool pool;

    /**
     * a map matches db and query class
     */
    private static final Map<String, String> DB_QUERY_MAP = Map.of("mysql", "com.transfer.mysql.sorm.query.impl.MySQLQuery");

    static {
        var rb = ResourceBundle.getBundle("com/test/mysql/sorm/db");
        conf = new Configuration();
        conf.setPojoPackage(rb.getString("pojoPackage"));
        conf.setUrl(rb.getString("url"));
        conf.setUsername(rb.getString("username"));
        conf.setPassword(rb.getString("password"));
        conf.setQueryClass(resolveQueryClass(rb.getString("url")));
        conf.setPoolInitialSize(Integer.parseInt(rb.getString("poolInitialSize")));
        conf.setPoolMaxActive(Integer.parseInt(rb.getString("poolMaxActive")));
    }

    private static String resolveQueryClass(String url) {
        String usingDB = Strs.subExclusive(url, "jdbc:", "://");
        return DB_QUERY_MAP.get(usingDB);
    }

    /**
     * Get the connection Object
     * @return connection Object
     */
    public static synchronized Connection getConn() {
        if (pool == null) {
            pool = new DBConnPool();
        }
        return pool.getConnection();
    }

    /**
     * Get the configuration Object
     * @return Configuration Object
     */
    public static Configuration getConf() {
        return conf;
    }

    /**
     * Get the connection pool
     * @return connection pool
     */
    public static DBConnPool getPool() {
        return pool;
    }
}
