package com.ddcode.java.reentrantLock;

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
    private void eat(){
        log.debug("eating...");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            String currentName = Thread.currentThread().getName();
            log.debug("currentName {} 尝试获取左筷子" , currentName);
            boolean tryLockLeft = left.tryLock();
            if(tryLockLeft){
                try {
                    log.debug("currentName {} 尝试获取左筷子, 成功" , currentName);
                    log.debug("currentName {} 尝试获取右筷子" , currentName);
                    boolean tryLockRight = right.tryLock();
                    if(tryLockRight){
                        try {
                            log.info("currentName {} 成功获取两个筷子, 成功" , currentName);
                            log.info("*************************currentName {} 执行 eat() 方法" , currentName);
                            eat();
                        } finally {
                            log.debug("currentName {} 尝试获取右筷子, 成功, 最终释放右筷子" , currentName);
                            right.unlock();
                        }
                    } else {
                        log.debug("currentName {} 尝试获取右筷子, 失败" , currentName);
                    }

                } finally {
                    log.debug("currentName {} 尝试获取左筷子, 成功, 最终释放左筷子" , currentName);
                    left.unlock();
                }
            } else {
                log.debug("currentName {} 尝试获取左筷子, 失败" , currentName);
            }
        }
    }
}
