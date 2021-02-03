package com.jmc.reference;

/**
 * <p> 方法引用
 * <p> 作者：Jmc
 * <p> 时间：2021.2.3
 * <p> 功能：
 * <pre> 可将方法作为参数传入另一个方法以便调用，且支持直接传入lambda（参数均必须小于等于7个）<br> 提供了of和bind方法 </pre>
 * @param <R> 返回值类型
 */
@SuppressWarnings("unchecked")
public abstract class MethodRef<R> {
    private MethodRef() {}

    public abstract R invoke(Object... args);

    private static void checkParams(int realSize, int assertSize) {
        if (realSize != assertSize)
            throw new IllegalArgumentException("调用参数个数不匹配，需要%d个，实际提供了%d个".formatted(assertSize, realSize));
    }

    public static MethodRef<Object> of(Void0 m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke();
                return null;
            }
        };
    }

    public static <T> MethodRef<Object> of(Void1<T> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 1);
                m.invoke((T) args[0]);
                return null;
            }
        };
    }

    public static <T, U> MethodRef<Object> of(Void2<T, U> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 2);
                m.invoke((T) args[0], (U) args[1]);
                return null;
            }
        };
    }

    public static <T, U, V> MethodRef<Object> of(Void3<T, U, V> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 3);
                m.invoke((T) args[0], (U) args[1], (V) args[2]);
                return null;
            }
        };
    }

    public static <T, U, V, W> MethodRef<Object> of(Void4<T, U, V, W> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 4);
                m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3]);
                return null;
            }
        };
    }

    public static <T, U, V, W, X> MethodRef<Object> of(Void5<T, U, V, W, X> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 5);
                m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3], (X) args[4]);
                return null;
            }
        };
    }

    public static <T, U, V, W, X, Y> MethodRef<Object> of(Void6<T, U, V, W, X, Y> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 6);
                m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3], (X) args[4], (Y) args[5]);
                return null;
            }
        };
    }

    public static <T, U, V, W, X, Y, Z> MethodRef<Object> of(Void7<T, U, V, W, X, Y, Z> m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 7);
                m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3], (X) args[4], (Y) args[5], (Z) args[6]);
                return null;
            }
        };
    }

    public static <R> MethodRef<R> of(Object0<R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke();
            }
        };
    }

    public static <T, R> MethodRef<R> of(Object1<T, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 1);
                return m.invoke((T) args[0]);
            }
        };
    }

    public static <T, U, R> MethodRef<R> of(Object2<T, U, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 2);
                return m.invoke((T) args[0], (U) args[1]);
            }
        };
    }

    public static <T, U, V, R> MethodRef<R> of(Object3<T, U, V, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 3);
                return m.invoke((T) args[0], (U) args[1], (V) args[2]);
            }
        };
    }

    public static <T, U, V, W, R> MethodRef<R> of(Object4<T, U, V, W, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 4);
                return m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3]);
            }
        };
    }

    public static <T, U, V, W, X, R> MethodRef<R> of(Object5<T, U, V, W, X, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 5);
                return m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3], (X) args[4]);
            }
        };
    }

    public static <T, U, V, W, X, Y, R> MethodRef<R> of(Object6<T, U, V, W, X, Y, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 6);
                return m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3], (X) args[4], (Y) args[5]);
            }
        };
    }

    public static <T, U, V, W, X, Y, Z, R> MethodRef<R> of(Object7<T, U, V, W, X, Y, Z, R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 7);
                return m.invoke((T) args[0], (U) args[1], (V) args[2], (W) args[3], (X) args[4], (Y) args[5], (Z) args[6]);
            }
        };
    }

    public static MethodRef<Object> bind(Void0 m) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke();
                return null;
            }
        };
    }

    public static <T> MethodRef<Object> bind(Void1<T> m, T t) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t);
                return null;
            }
        };
    }

    public static <T, U> MethodRef<Object> bind(Void2<T, U> m, T t, U u) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t, u);
                return null;
            }
        };
    }

    public static <T, U, V> MethodRef<Object> bind(Void3<T, U, V> m, T t, U u, V v) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t, u, v);
                return null;
            }
        };
    }

    public static <T, U, V, W> MethodRef<Object> bind(Void4<T, U, V, W> m, T t, U u, V v, W w) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t, u, v, w);
                return null;
            }
        };
    }

    public static <T, U, V, W, X> MethodRef<Object> bind(Void5<T, U, V, W, X> m, T t, U u, V v, W w, X x) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t, u, v, w, x);
                return null;
            }
        };
    }

    public static <T, U, V, W, X, Y> MethodRef<Object> bind(Void6<T, U, V, W, X, Y> m, T t, U u, V v, W w, X x, Y y) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t, u, v, w, x, y);
                return null;
            }
        };
    }

    public static <T, U, V, W, X, Y, Z> MethodRef<Object> bind(Void7<T, U, V, W, X, Y, Z> m, T t, U u, V v, W w, X x, Y y, Z z) {
        return new MethodRef<>() {
            @Override
            public Object invoke(Object... args) {
                checkParams(args.length, 0);
                m.invoke(t, u, v, w, x, y, z);
                return null;
            }
        };
    }

    public static <R> MethodRef<R> bind(Object0<R> m) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke();
            }
        };
    }

    public static <T, R> MethodRef<R> bind(Object1<T, R> m, T t) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t);
            }
        };
    }

    public static <T, U, R> MethodRef<R> bind(Object2<T, U, R> m, T t, U u) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t, u);
            }
        };
    }

    public static <T, U, V, R> MethodRef<R> bind(Object3<T, U, V, R> m, T t, U u, V v) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t, u, v);
            }
        };
    }

    public static <T, U, V, W, R> MethodRef<R> bind(Object4<T, U, V, W, R> m, T t, U u, V v, W w) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t, u, v, w);
            }
        };
    }

    public static <T, U, V, W, X, R> MethodRef<R> bind(Object5<T, U, V, W, X, R> m, T t, U u, V v, W w, X x) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t, u, v, w, x);
            }
        };
    }

    public static <T, U, V, W, X, Y, R> MethodRef<R> bind(Object6<T, U, V, W, X, Y, R> m, T t, U u, V v, W w, X x, Y y) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t, u, v, w, x, y);
            }
        };
    }

    public static <T, U, V, W, X, Y, Z, R> MethodRef<R> bind(Object7<T, U, V, W, X, Y, Z, R> m, T t, U u, V v, W w, X x, Y y, Z z) {
        return new MethodRef<>() {
            @Override
            public R invoke(Object... args) {
                checkParams(args.length, 0);
                return m.invoke(t, u, v, w, x, y, z);
            }
        };
    }

    public interface Void0 {
        void invoke();
    }

    public interface Void1<T> {
        void invoke(T a);
    }

    public interface Void2<T, U> {
        void invoke(T t, U u);
    }

    public interface Void3<T, U, V> {
        void invoke(T t, U u, V v);
    }

    public interface Void4<T, U, V, W> {
        void invoke(T t, U u, V v, W w);
    }

    public interface Void5<T, U, V, W, X> {
        void invoke(T t, U u, V v, W w, X x);
    }

    public interface Void6<T, U, V, W, X, Y> {
        void invoke(T t, U u, V v, W w, X x, Y y);
    }

    public interface Void7<T, U, V, W, X, Y, Z> {
        void invoke(T t, U u, V v, W w, X x, Y y, Z z);
    }

    public interface Object0<R> {
        R invoke();
    }

    public interface Object1<T, R> {
        R invoke(T t);
    }

    public interface Object2<T, U, R> {
        R invoke(T t, U u);
    }

    public interface Object3<T, U, V, R> {
        R invoke(T t, U u, V v);
    }

    public interface Object4<T, U, V, W, R> {
        R invoke(T t, U u, V v, W w);
    }

    public interface Object5<T, U, V, W, X, R> {
        R invoke(T t, U u, V v, W w, X x);
    }

    public interface Object6<T, U, V, W, X, Y, R> {
        R invoke(T t, U u, V v, W w, X x, Y y);
    }

    public interface Object7<T, U, V, W, X, Y, Z, R> {
        R invoke(T t, U u, V v, W w, X x, Y y, Z z);
    }
}

