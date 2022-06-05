package com.ddcode.java.multilock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 哲学家类
 */
@Slf4j(topic = "c.philosopher")
public class Philosopher extends Thread {

    Chopstick left;
    Chopstick right;

    /**
     * 创建构造函数,传递参数
     * @param name
     * @param left
     * @param right
     */
    public Philosopher(String name, Chopstick left, Chopstick right) {
        super(name);
        this.left = left;
        this.right = right;
    }

    /**
     * 定义吃的方法
     * @throws InterruptedException
     */
    private void eat() throws InterruptedException {
        log.debug("eating...");
        TimeUnit.SECONDS.sleep(1);
    }

    @Override
    public void run() {
        while (true) {
            // 获得左手筷子
            synchronized (left) {
                 // 获得右手筷子
                synchronized (right) {
                  // 吃饭
                    try {
                        eat();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            // 放下右手筷子
            }
         // 放下左手筷子
        }
    }
}
