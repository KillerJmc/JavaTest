package com.test.juc.apply;

/**
 * 实现Java标准库的Exchange
 * @param <T> 交换数据类型
 * @author Jmc, Sqj
 */
public class MyExchanger<T> {
    /**
     * 暂存的数据
     */
    T data;

    /**
     * 空对象
     */
    final Object NULL_OBJECT = new Object();

    @SuppressWarnings("unchecked")
    public T exchange(T data) throws InterruptedException {
        // 处理传入空对象的情况
        data = data == null ? (T) NULL_OBJECT : data;

        synchronized (this) {
            // 如果对象内的data为空说明第一次调用
            if (this.data == null) {
                // 赋值对象内的data并等待
                this.data = data;
                this.wait();

                // 被第二次调用者唤醒，并获取其data
                var otherData = this.data;

                // 重置对象内data的值供下一组调用
                this.data = null;

                // 返回第二次调用者的data
                return otherData.equals(NULL_OBJECT) ? null : otherData;
            }

            // 这里说明是第二次调用
            // 直接获取到第一次调用的data作为返回值
            var otherData = this.data;
            // 赋值对象内的data给第一次的调用者
            this.data = data;
            // 唤醒第一次调用者
            this.notify();
            // 返回第一次调用者的data
            return otherData.equals(NULL_OBJECT) ? null : otherData;
        }
    }
}
