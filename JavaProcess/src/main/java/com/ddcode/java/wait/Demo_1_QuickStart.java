package com.ddcode.java.wait;

public class Demo_1_QuickStart {

    private static Object obj = new Object();

    public static void main(String[] args) {

        try {
            //直接调用 wait() 方法
            obj.wait();
            obj.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
