package com.test.newfeatures.lambda.func;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.Test;

public class FuncTest {
    @Data
    @AllArgsConstructor
    static class Stu {
        String name;
        int age;
    }

    @Test
    public void test() {
        Stu s = new Stu("Jmc", 18);

        interface GetFunc<T, R> {
            R apply(T t);
        }

        var getName = (GetFunc<Stu, String>) Stu::getName;

        String name = getName.apply(s);
        System.out.println(name);

        interface SetFunc<T, R> {
            void apply(T t, R r);
        }

        var setAge = (SetFunc<Stu, Integer>) Stu::setAge;

        System.out.println(s.getAge());
        setAge.apply(s, 666);
        System.out.println(s.getAge());
    }

    static class AmazingFunc {
        @FunctionalInterface
        interface Type<T> {
            void printType(Class<T> c);
        }

        public static <T> Type<T> m() {
            return c -> System.out.println("Type is: " + c.getSimpleName());
        }
    }


    @Test
    public void test2() throws Exception {
        var type = AmazingFunc.<Stu>m();
        type.printType(Stu.class);

        var type2 = AmazingFunc.m();
        type2.printType(Object.class);
    }
}
