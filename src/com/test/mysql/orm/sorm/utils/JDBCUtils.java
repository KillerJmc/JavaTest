package com.test.mysql.orm.sorm.utils;

import java.sql.PreparedStatement;

/**
 * Package the regular operations of JDBC query.
 * @author Jmc
 */
public class JDBCUtils {
    /**
     * Handle parameters
     * @param ps PreparedStatement
     * @param params parameters
     */
    public static void handleParams(PreparedStatement ps, Object[] params) {
        TryUtils.tryThis(() -> {
            if (params != null)
                for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
        });
    }
}
