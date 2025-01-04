package com.test.native_invoke;

import com.jmc.lang.Tries;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/*
var res = Switch.<String> judge(Integer.class)
            .cases(int.class, () -> "我是int！")
            .cases(double.class, () -> "我是double！")
            .cases(long.class, () -> "我是long！")
            .orElseGet(() -> "没有这个类啊！！！");
    print(res);
 */
public class Switch<T> {
    private Object judgeObj;
    private Callable<T> returnAction;

    private Switch() {}

    public static <T> Switch<T> judge(Object judgeObj) {
        var instance = new Switch<T>();
        instance.judgeObj = judgeObj;
        return instance;
    }

    public Switch<T> cases(Object caseObj, Callable<T> action) {
        if (Objects.equals(caseObj, judgeObj)) {
            this.returnAction = action;
        }
        return this;
    }

    public T orElse(T other) {
        if (this.returnAction != null) {
            return Tries.tryReturnsT(this.returnAction::call);
        }
        return other;
    }

    public T orElseGet(Supplier<T> other) {
        if (this.returnAction != null) {
            return Tries.tryReturnsT(this.returnAction::call);
        }
        return other.get();
    }

    @SuppressWarnings("preview")
    public T orElseThrow() {
        if (this.returnAction != null) {
            return Tries.tryReturnsT(this.returnAction::call);
        }
        throw new NoSuchElementException(STR."目标值是：\{this.judgeObj}，没有命中Switch！");
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X {
        if (this.returnAction != null) {
            return Tries.tryReturnsT(this.returnAction::call);
        }
        throw exceptionSupplier.get();
    }
}
