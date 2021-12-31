package com.test.mysql.sorm.query;

import com.jmc.lang.Tries;
import com.test.mysql.sorm.conn.DBManager;

/**
 * Be responsible for creating the query objects.
 */
public class QueryFactory {
    private static Query protoTypeObj;

    static {
        Tries.tryThis(() -> {
            Class<?> c = Class.forName(DBManager.getConf().getQueryClass());
            protoTypeObj = (Query) c.getDeclaredConstructor().newInstance();
        });
    }

    private QueryFactory() {

    }

    public static Query createQuery() {
        return Tries.tryReturnsT(() -> (Query) protoTypeObj.clone());
    }
}
