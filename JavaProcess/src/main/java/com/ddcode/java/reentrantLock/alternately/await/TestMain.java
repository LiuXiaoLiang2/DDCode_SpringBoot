package com.ddcode.java.reentrantLock.alternately.await;

import java.util.concurrent.locks.Condition;

public class TestMain {

    public static void main(String[] args) {
        AwaitSignal as = new AwaitSignal(3);
        Condition aWaitSet = as.newCondition();
        Condition bWaitSet = as.newCondition();
        Condition cWaitSet = as.newCondition();
        new Thread(() -> {
            as.print("a", aWaitSet, bWaitSet);
        }).start();
        new Thread(() -> {
            as.print("b", bWaitSet, cWaitSet);
        }).start();
        new Thread(() -> {
            as.print("c", cWaitSet, aWaitSet);
        }).start();
        as.start(aWaitSet);
    }
}
