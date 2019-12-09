package com.test.ORM.SORM.core;

import com.test.ORM.SORM.utils.TryUtils;

/**
 * Be responsible for creating the query objects.
 */
public class QueryFactory {
    private static Query protoTypeObj;

    static {
        TryUtils.tryThis(() -> {
            Class<?> c = Class.forName(DBManager.getConf().getQueryClass());
            protoTypeObj = (Query) c.getDeclaredConstructor().newInstance();
        });
    }

    private QueryFactory() {

    }

    public static Query createQuery() {
        return TryUtils.tryAndReturn(() -> (Query) protoTypeObj.clone());
    }
}
