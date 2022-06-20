package com.ddcode.java.cyclicBarrier;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

@Slf4j(topic = "c.cyclicBarrier")
public class Demo_1_CyclicBarrier_Create {

    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(2, ()->{
            log.info("t1 和 t2 线程执行完毕");
        }); // 个数为2时才会继续执行

        new Thread(()->{
            log.info("线程1开始..");
            try {
                log.info("线程1执行完毕..");
                cb.await(); // 当个数不足时，等待
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();


        new Thread(()->{
            log.info("线程2开始..");
            try { Thread.sleep(2000); } catch (InterruptedException e) { }
            log.info("线程2执行完毕...");
            try {
                cb.await(); // 2 秒后，线程个数够2，继续运行
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
