package com.test.interview.thread;

public class DCLTest {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> System.out.println(Singleton.getInstance().hashCode())
                ).start();
        }
    }

    private static class Singleton {
        private Singleton() {}
        //一定要加volatile防止指令乱序造成对象的半初始化
        private static volatile Singleton instance;
        public int i = 100;

        public static Singleton getInstance() {
            if (instance == null) {
                synchronized(Singleton.class) {
                    if (instance == null) instance = new Singleton();
                }
            }
            return instance;
        }
    }
}
