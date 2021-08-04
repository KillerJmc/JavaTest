package com.test.mysql.sorm.config;

import lombok.Data;

/**
 * Manage the configuration information.
 * @author Jmc
 */
@Data
public class Configuration {
    /**
     * JDBC url
     */
    private String url;

    /**
     * Database user
     */
    private String username;

    /**
     * Database password
     */
    private String password;

    /**
     * Scan the generated Java packages. (Persistence Object)
     */
    private String pojoPackage;

    /**
     * The class used for database query.
     */
    private String queryClass;

    /**
     * The max active connection amount in the connection pool.
     */
    private int poolMaxActive;

    /**
     * The initial connection amount in the connection pool.
     */
    private int poolInitialSize;

    public Configuration() {

    }
}
