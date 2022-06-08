package com.ddcode.java.reentrantLock.alternately.park;

import java.util.concurrent.locks.LockSupport;

public class SyncPark {

    private int loopNumber;

    public SyncPark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }
}
