package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 使用线程的 yield() 方法: 让出CPU使用权
 */
@Slf4j(topic = "c.yield")
public class Demo_5_yield {
    public static void main(String[] args) {
        yield();
    }

    public static void yield(){

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    //线程1做出让步
                    Thread.yield();
                    log.info("线程1 执行 i {}", i);
                }
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    log.info("线程2 执行 i {}", i);
                }
            }
        };

        thread1.start();
        thread2.start();
    }
}
