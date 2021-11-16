package com.test.incubator.clinker;

import java.util.function.Function;

/**
 * 指针类
 * @param <T> 指针储存类型
 */
public class Pointer<T> {
    /**
     * 指针指向值
     */
    private T value;

    private Pointer() {}

    /**
     * 获得一个指针
     * @param initValue 指向的初始值
     * @return 指针
     */
    public static <T> Pointer<T> of(T initValue) {
        var ptr = new Pointer<T>();
        ptr.value = initValue;
        return ptr;
    }

    /**
     * 让指针指向一个新值
     * @param newValue 新值
     * @return 旧值
     */
    public T set(T newValue) {
        T oldValue = value;
        this.value = newValue;
        return oldValue;
    }

    /**
     * 让指针指向一个新值（无类型检查）
     * @param newValue 新值
     */
    @SuppressWarnings("unchecked")
    public void setUnchecked(Object newValue) {
        this.value = (T) newValue;
    }

    /**
     * 获取指针指向的值
     * @return 指向的值
     */
    public T get() {
        return value;
    }

    /**
     * 更新指针指向的值
     * @param updateFunc 更新函数
     * @return 旧值
     */
    public T update(Function<T, T> updateFunc) {
        T oldValue = value;
        this.value = updateFunc.apply(oldValue);
        return oldValue;
    }

    /**
     * 获取指针指向的值的类型
     * @return 值的类型
     */
    @SuppressWarnings("unchecked")
    public Class<T> type() {
        return (Class<T>) value.getClass();
    }
}
