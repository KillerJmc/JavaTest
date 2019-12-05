package com.test.ORM.SORM.Core;

import com.test.Main.Tools;
import com.test.ORM.SORM.Bean.Configuration;
import com.test.ORM.SORM.Utils.TryUtils;

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
    public static Configuration conf;

    static {
        var pros = new Properties();
        TryUtils.tryThis(() -> pros.load(
            new FileInputStream(Tools.getJavaParentPath(DBManager.class) + "db.properties")));
        conf = new Configuration();
        conf.setPoPackage(pros.getProperty("poPackage"));
        conf.setUsingDB(pros.getProperty("usingDB"));
        conf.setSrcPath(pros.getProperty("srcPath"));
        conf.setUrl(pros.getProperty("url"));
        conf.setUser(pros.getProperty("user"));
        conf.setPwd(pros.getProperty("pwd"));
        conf.setQueryClass(pros.getProperty("queryClass"));
    }

    public static Connection getConn() {
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
     * @return Configuration Object
     */
    public static Configuration getConf() {
        return conf;
    }
}
