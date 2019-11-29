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
     * @param obj Object
     * @return get result
     */
    public static Object invokeGet(String fieldName, Object obj) {
        return TryUtils.tryAndReturn(() -> {
            Class c = obj.getClass();
            Method m = c.getMethod("get" + firstChar2UpperCase(fieldName), null);
            return m.invoke(obj, null);
        });
    }
}
