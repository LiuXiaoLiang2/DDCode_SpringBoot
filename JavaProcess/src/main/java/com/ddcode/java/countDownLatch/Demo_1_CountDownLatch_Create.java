package com.ddcode.java.countDownLatch;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.countDownLatch")
public class Demo_1_CountDownLatch_Create {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread("t1") {
            @Override
            public void run() {
                log.info("t1 线程开始执行");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("t1 线程执行完毕");
                //计数器减1
                countDownLatch.countDown();
            }
        }.start();

        new Thread("t2") {
            @Override
            public void run() {
                log.info("t2 线程开始执行");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("t2 线程执行完毕");
                //计数器减1
                countDownLatch.countDown();
            }
        }.start();

        log.info("主线程阻塞等待");
        countDownLatch.await();
        log.info("主线程 , 阻塞等待 t1 和 t2 都执行完毕");
    }
}
