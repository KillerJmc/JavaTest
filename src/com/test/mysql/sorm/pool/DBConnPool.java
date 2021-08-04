package com.test.mysql.sorm.pool;

import com.jmc.lang.extend.Tries;
import com.test.mysql.sorm.conn.DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;

/**
 * Provide database connection pool function.
 * @author Jmc
 */
public class DBConnPool {
    /**
     * idle connection list
     */
    private static LinkedList<Connection> idleConnList;

    /**
     * active connection list
     */
    private static LinkedList<Connection> activeConnList;

    /**
     * the initial connection amount
     */
    public static final int POOL_INITIAL_SIZE = DBManager.getConf().getPoolInitialSize();

    /**
     * the max active connection amount
     */
    public static final int POOL_MAX_ACTIVE = DBManager.getConf().getPoolMaxActive();

    public DBConnPool() {
        initPool();
    }

    /**
     * Initialize the connection pool, make the connections to the initial amount.
     */
    public void initPool() {
        if (idleConnList == null) {
            idleConnList = new LinkedList<>();
        }

        if (activeConnList == null) {
            activeConnList = new LinkedList<>();
        }

        while (idleConnList.size() < POOL_INITIAL_SIZE) {
            idleConnList.add(createConn());
        }
        System.err.println("连接池：初始化完毕\n");
    }


    /**
     * Create a new connection
     * @return connection Object
     */
    private static Connection createConn() {
        return Tries.tryReturnsT(() ->
                DriverManager.getConnection(
                    DBManager.getConf().getUrl(),
                    DBManager.getConf().getUsername(),
                    DBManager.getConf().getPassword()
                )
        );
    }

    /**
     * Get a connection from the connection pool
     * @return the connection Object
     */
    public synchronized Connection getConnection() {
        if (activeConnList.size() == POOL_MAX_ACTIVE) {
            // wait
            throw new RuntimeException("Active connection amount reached the max amount!");
        }

        if (idleConnList.isEmpty()) {
            while (idleConnList.size() < POOL_INITIAL_SIZE) {
                idleConnList.add(createConn());
            }
        }

        var conn = idleConnList.removeLast();
        activeConnList.add(conn);
        return conn;
    }

    /**
     * Get a connection from the connection pool
     * @param conn connection
     */
    public synchronized void closeConnection(Connection conn) {
        activeConnList.remove(conn);
        idleConnList.add(conn);
    }
}
