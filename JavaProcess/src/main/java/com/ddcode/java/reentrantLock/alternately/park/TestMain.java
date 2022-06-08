package com.ddcode.java.reentrantLock.alternately.park;

import java.util.concurrent.locks.LockSupport;

public class TestMain {

    static Thread t1 = null;
    static Thread t2 = null;
    static Thread t3 = null;

    public static void main(String[] args) {

        SyncPark syncPark = new SyncPark(3);

        t1 = new Thread(){
            @Override
            public void run() {
                syncPark.print("a", t2);
            }
        };

        t2 = new Thread(){
            @Override
            public void run() {
                syncPark.print("b", t3);
            }
        };

        t3 = new Thread(){
            @Override
            public void run() {
                syncPark.print("c", t1);
            }
        };

        t1.start();
        t2.start();
        t3.start();
        //先唤醒t1线程, 让t1线程先执行
        LockSupport.unpark(t1);
    }
}
