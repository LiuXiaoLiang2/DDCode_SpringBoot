package com.ddcode.java.reentrantLock;

import lombok.extern.slf4j.Slf4j;

/**
 * 顺序执行
 */
@Slf4j(topic = "c.order")
public class Demo_5_Order_Wait {

    public static final Object obj = new Object();

    public static void main(String[] args) {

        new Thread("t1"){
            @Override
            public void run() {
                synchronized (obj){
                    try {
                        //进入线程阻塞
                        obj.wait();
                        log.info("t1线程, 执行, 输出 1");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread("t2"){
            @Override
            public void run() {
                synchronized (obj){
                    //进入线程阻塞
                    log.info("t2线程, 执行, 输出 2");
                    //唤醒等待的线程 t1
                    obj.notifyAll();
                }
            }
        }.start();
    }
}
