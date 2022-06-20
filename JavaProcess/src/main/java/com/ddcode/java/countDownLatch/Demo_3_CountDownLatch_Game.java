package com.ddcode.java.countDownLatch;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "c.countDownLatch.game")
public class Demo_3_CountDownLatch_Game {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger num = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(3);

        //创建线程池,同时给线程起别名
        ExecutorService service = Executors.newFixedThreadPool(10, (r)->{
            return new Thread(r, "t" + num.getAndIncrement());
        });


        String[] all = new String[10];
        Random r = new Random();
        for (int j = 0; j < 10; j++) {
            int x = j;
            service.submit(() -> {
                for (int i = 0; i <= 100; i++) {
                    try {
                        Thread.sleep(r.nextInt(100));
                    } catch (InterruptedException e) {
                    }
                    all[x] = Thread.currentThread().getName() + "(" + (i + "%") + ")";
                    System.out.print("\r" + Arrays.toString(all));
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("\n游戏开始...");
        service.shutdown();
    }
}
