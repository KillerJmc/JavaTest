package com.jmc.lang.reflect;

import com.jmc.lang.Tries;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @date 2020.11.26
 */
@SuppressWarnings({"unchecked", "unused"})
public class ReflectUtils {
    public static Object getField(Object instance, String fieldName) {
        return Tries.tryReturnsT(() -> {
            Field f = instance.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            return f.get(instance);
        });
    }

    public static <T> T getField(Object instance, String fieldName, Class<T> returnType) {
        return (T) getField(instance, fieldName);
    }

    public static Method getMethod(Object instance, String MethodName, Class<?>... parameterTypes) {
        return Tries.tryReturnsT(() -> {
            Method m = instance.getClass().getDeclaredMethod(MethodName, parameterTypes);
            m.setAccessible(true);
            return m;
        });
    }

    public static Object invokeMethod(Object instance, String MethodName) {
        return Tries.tryReturnsT(() -> getMethod(instance, MethodName).invoke(instance));
    }

    public static <T> T invokeMethod(Object instance, String MethodName, Class<T> returnType) {
        return (T) invokeMethod(instance, MethodName);
    }
}
