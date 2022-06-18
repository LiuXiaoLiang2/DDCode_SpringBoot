package com.ddcode.java.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.Semaphore")
public class Demo_1_Semaphore {
    public static void main(String[] args) {

        // 1. 创建 semaphore 对象, 最多有三个线程同时运行
        Semaphore semaphore = new Semaphore(3);

        // 2. 10个线程同时运行
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 3. 获取许可
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    log.debug("running...");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    log.debug("end...");
                } finally {
                // 4. 释放许可
                    semaphore.release();
                }
            }).start();
        }

    }
}
