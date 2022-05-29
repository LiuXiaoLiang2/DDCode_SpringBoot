package com.ddcode.java.safe;

/**
 * 成员变量线程安全测试
 */
public class Demo_1_safe_Memeber {

    static final int THREAD_NUMBER = 2;
    static final int LOOP_NUMBER = 200;

    public static void main(String[] args) {

        ThreadUnsafe test = new ThreadUnsafe();
        //开启一百个线程,操作静态变量 list
        for (int i = 0; i < THREAD_NUMBER; i++) {
            new Thread(() -> {
                test.method(LOOP_NUMBER);
            }, "Thread" + i).start();
        }
    }
}
