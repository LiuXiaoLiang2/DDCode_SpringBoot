package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.Priority")
public class Demo_6_Priority {
    public static void main(String[] args) {
        priority();
    }

    public static void priority(){

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
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

        //设置thread1最高优先级
        thread1.setPriority(Thread.MAX_PRIORITY);
        //设置thread2最低优先级
        thread2.setPriority(Thread.MIN_PRIORITY);
    }
}
