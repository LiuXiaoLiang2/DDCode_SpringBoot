package com.ddcode.java.stampedLock;

import java.util.concurrent.TimeUnit;

public class Demo_1_StampedLock {

    public static void main(String[] args) throws Exception{
//        readRead();
        readWrite();
    }

    public static void readRead() throws Exception{
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            try {
                dataContainer.read(1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                dataContainer.read(2);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, "t2").start();
    }


    public static void readWrite() throws Exception{
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            try {
                dataContainer.read(2);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, "t1").start();
        new Thread(() -> {
            try {
                dataContainer.write(1);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }, "t2").start();
    }
}
