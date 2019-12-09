package com.test.ORM.SORM.core;

import com.test.ORM.SORM.bean.Configuration;
import com.test.ORM.SORM.pool.DBCoonPool;
import com.test.ORM.SORM.utils.TryUtils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Keep the Management of the Connected Objects according to the configuration information.
 * (add the ThreadPool Function)
 * @author Jmc
 */
public class DBManager {
    /**
     * configuration Object
     */
    private static Configuration conf;

    /**
     * connection pool Object
     */
    private static DBCoonPool pool;

    static {
        var pros = new Properties();
        TryUtils.tryThis(() -> pros.load(
            new FileInputStream( "./src/com/test/ORM/SORM/db.properties")));
        conf = new Configuration();
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUser(pros.getProperty("user"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setQueryClass(pros.getProperty("queryClass"));
        conf.setPoolMaxSize(Integer.parseInt(pros.getProperty("poolMaxSize")));
        conf.setPoolMinSize(Integer.parseInt(pros.getProperty("poolMinSize")));
    }

    /**
     * Get the connection Object
     * @return connection Object
     */
    public static Connection getConn() {
        if (pool == null) pool = new DBCoonPool();
        return pool.getConnection();
    }

    /**
     * Create a new connection
     * @return connection Object
     */
    public static Connection createConn() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    conf.getUrl(),
                    conf.getUser(),
                    conf.getPwd()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
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
    public static DBCoonPool getPool() {
        return pool;
    }
}
