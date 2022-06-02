package com.ddcode.java.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.wait_sleep")
public class Demo_3_Wait_Sleep {

    //定义锁对象 room
    static final Object room = new Object();
    // 判断是否买到了烟
    static boolean hasCigarette = false;
    // 判断外卖是否送到
    static boolean hasTakeout = false;

    public static void main(String[] args) throws InterruptedException {

//        demo1();
//        demo2();
//        demo3();
        demo4();

    }

    public static void demo1() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                //判断有没有烟
                if (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        //其他开启五个线程干活, 锁对象都是 room
        for (int i = 0; i < 5; i++){
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }

        //主线程
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            hasCigarette = true;
            log.debug("烟到了噢！");
        }, "送烟的").start();
    }


    public static void demo2() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                //判断有没有烟
                if (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        //暂且wait, 释放锁
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        //其他开启五个线程干活, 锁对象都是 room
        for (int i = 0; i < 5; i++){
            new Thread(() -> {
                synchronized (room) {
                    log.debug("可以开始干活了");
                }
            }, "其它人").start();
        }

        //主线程
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            synchronized (room){
                hasCigarette = true;
                log.debug("烟到了噢！");
                //唤醒小脑等烟的线程
                room.notify();
            }
        }, "送烟的").start();
    }

    public static void demo3() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                //判断有没有烟
                if (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        //暂且wait, 释放锁
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        //主线程
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            synchronized (room){
                hasTakeout = true;
                log.debug("外卖到了噢！");
                //唤醒小脑等烟的线程
                room.notifyAll();
            }
        }, "送外卖的").start();
    }


    public static void demo4() throws InterruptedException {
        new Thread(() -> {
            synchronized (room) {
                log.debug("有烟没？[{}]", hasCigarette);
                //判断有没有烟
                while (!hasCigarette) {
                    log.debug("没烟，先歇会！");
                    try {
                        //暂且wait, 释放锁
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("有烟没？[{}]", hasCigarette);
                if (hasCigarette) {
                    log.debug("可以开始干活了");
                }
            }
        }, "小南").start();

        new Thread(() -> {
            synchronized (room) {
                Thread thread = Thread.currentThread();
                log.debug("外卖送到没？[{}]", hasTakeout);
                while (!hasTakeout) {
                    log.debug("没外卖，先歇会！");
                    try {
                        room.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("外卖送到没？[{}]", hasTakeout);
                if (hasTakeout) {
                    log.debug("可以开始干活了");
                } else {
                    log.debug("没干成活...");
                }
            }
        }, "小女").start();

        //主线程
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            synchronized (room){
                hasTakeout = true;
                log.debug("外卖到了噢！");
                //唤醒小脑等烟的线程
                room.notifyAll();
            }
        }, "送外卖的").start();
    }
}
