package com.test.ORM.SORM.utils;

import java.lang.reflect.Method;

/**
 * Package the regular operations of Reflect.
 * @author Jmc
 */
public class ReflectUtils {
    /**
     * Invoke the get fn of Object whose associate to the attribute (fieldName)
     * @param fieldName fieldName
     * @param obj the Object that from the Po package
     * @return get result
     */
    public static Object invokeGet(Object obj, String fieldName) {
        return TryUtils.tryAndReturn(() -> {
            Class<?> c = obj.getClass();
            Method m = c.getDeclaredMethod("get" + StringUtils.firstChar2UpperCase(fieldName));
            return m.invoke(obj);
        });
    }

    public static void invokeSet(Object obj, String columnName, Object columnValue) {
        TryUtils.tryThis(() -> {
            if (columnValue != null) {
                Class<?> c = obj.getClass();
                Method m = c.getDeclaredMethod("set" + StringUtils.firstChar2UpperCase(columnName), columnValue.getClass());
                m.invoke(obj, columnValue);
            }
        });
    }
}
