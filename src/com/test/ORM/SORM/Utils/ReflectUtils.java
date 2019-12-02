package com.test.ORM.SORM.Utils;

import java.lang.reflect.Method;

import static com.test.ORM.SORM.Utils.StringUtils.firstChar2UpperCase;

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
            Class c = obj.getClass();
            Method m = c.getDeclaredMethod("get" + firstChar2UpperCase(fieldName), null);
            return m.invoke(obj, null);
        });
    }

    public static void invokeSet(Object obj, String columnName, Object columnValue) {
        TryUtils.tryThis(() -> {
            Class c = obj.getClass();
            Method m = c.getDeclaredMethod("set" + firstChar2UpperCase(columnName), columnValue.getClass());
            m.invoke(obj, columnValue);
        });
    }
}
