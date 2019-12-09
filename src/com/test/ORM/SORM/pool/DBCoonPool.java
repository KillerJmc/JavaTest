package com.test.ORM.SORM.pool;

import com.test.ORM.SORM.core.DBManager;
import com.test.ORM.SORM.utils.CloseUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Provide database connection pool function.
 * @author Jmc
 */
public class DBCoonPool {
    /**
     * the connection pool Object
     */
    private static List<Connection> pool;
    /**
     * the max connection amount
     */
    public static final int POOL_MAX_SIZE = DBManager.getConf().getPoolMaxSize();
    /**
     * the min connection amount
     */
    public static final int POOL_MIN_SIZE = DBManager.getConf().getPoolMinSize();

    public DBCoonPool() {
        initPool();
    }

    /**
     * Initialize the connection pool, make the connections to the min amount.
     */
    public void initPool() {
        if (pool == null) {
            pool = new ArrayList<>();
        }

        while (pool.size() < POOL_MIN_SIZE) {
            pool.add(DBManager.createConn());
            System.out.println("初始化，池中连接数：" + pool.size());
        }
    }

    /**
     * Get a connection from the connection pool
     * @return the connection Object
     */
    public synchronized Connection getConnection() {
        int lastIndex = pool.size() - 1;
        var conn = pool.get(lastIndex);
        pool.remove(lastIndex);
        return conn;
    }

    /**
     * Get a connection from the connection pool
     * @param conn connection
     */
    public synchronized void closeConnection(Connection conn) {
        if (pool.size() >= POOL_MAX_SIZE) {
            CloseUtils.closeAll(conn);
        } else {
            pool.add(conn);
        }
    }
}
