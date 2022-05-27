package com.ddcode.java.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * 使用线程的 join 方法
 */
@Slf4j(topic = "c.join")
public class Demo_7_Join {

    public static int r = 0;
    public static int r1 = 0;
    public static int r2 = 0;

    public static void main(String[] args) {
//        quickstart();
//        quickstart_solve_1();
//        quickstart_solve_join();
//        test1();
        quickstart_join_waite_time();
    }


    /**
     * join的作用
     * 希望主线程能读到, 被子线程修改的静态遍历
     */
    public static void quickstart(){
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        });
        t1.start();
        log.debug("结果为:{}", r);
        log.debug("结束");
    }

    /**
     * 解决方式1: 让主线程休眠一会
     * 问题可以解决, 但是存在问题: 如果你不知道子线程会耗时多长时间怎么办,总不能让主线程无限制延时
     */
    public static void quickstart_solve_1(){
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        });
        t1.start();
        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("结果为:{}", r);
        log.debug("结束");
    }

    /**
     * 在主线程内执行 子线程的join方法，那么主线程会阻塞, 会被挂起
     * 一直等待子线程执行完毕后，主线程才会恢复继续往下执行
     */
    public static void quickstart_solve_join(){
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        });
        t1.start();
        try {
            //让主线程再次等待阻塞, 等子线程执行完毕后, 主线程才会往下执行
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("结果为:{}", r);
        log.debug("结束");
    }


    /**
     * 练习：查看耗时多久
     */
    public static void test1(){
        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    r1 = 10;
                    //子线程1休眠1s
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                try {
                    r2 = 20;
                    //子线程1休眠1s
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        long start = System.currentTimeMillis();
        //启动两个线程
        t1.start();
        t2.start();
        try {
            //主线程等待两个人线程执行完毕
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        log.debug("r1: {} r2: {} cost: {}", r1, r2, end - start);
    }


    public static void quickstart_join_waite_time(){
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
        });
        t1.start();
        try {
            //让主线程再次等待阻塞, 等子线程执行完毕后, 主线程才会往下执行
            t1.join(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("结果为:{}", r);
        log.debug("结束");
    }
}
