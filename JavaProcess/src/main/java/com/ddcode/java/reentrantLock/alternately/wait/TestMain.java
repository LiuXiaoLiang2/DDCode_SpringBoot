package com.ddcode.java.reentrantLock.alternately.wait;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "c.wait")
public class TestMain {

    public static void main(String[] args) {
        SyncWaitNotify syncWaitNotify = new SyncWaitNotify(1, 3);
        new Thread(() -> {
            syncWaitNotify.print(1, 2, "a");
        }).start();
        new Thread(() -> {
            syncWaitNotify.print(2, 3, "b");
        }).start();
        new Thread(() -> {
            syncWaitNotify.print(3, 1, "c");
        }).start();
    }
}
