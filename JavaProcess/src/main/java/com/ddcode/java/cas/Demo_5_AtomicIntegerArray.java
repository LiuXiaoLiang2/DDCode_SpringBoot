package com.ddcode.java.cas;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子数组
 */
public class Demo_5_AtomicIntegerArray {

    public static void main(String[] args) {
       unSafe();
        safe();

    }

    public static void unSafe(){
        //定义线程不安全数组
        int[] ints = new int[10];
        //需求是数组的每个元素都累加到10000
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < ints.length; i++) {
            // 每个线程对数组作 10000 次操作
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ints[j%ints.length]++;
                }
            }));
        }
        ts.forEach(t -> t.start()); // 启动所有线程
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); // 等所有线程结束
        System.out.println(Arrays.toString(ints));
    }



    public static void safe(){
        //定义线程安全数组
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(10);
        //需求是数组的每个元素都累加到10000
        List<Thread> ts = new ArrayList<>();
        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            // 每个线程对数组作 10000 次操作
            ts.add(new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    //给数组的第n个元素加1
                    atomicIntegerArray.getAndIncrement(j%atomicIntegerArray.length());
                }
            }));
        }
        ts.forEach(t -> t.start()); // 启动所有线程
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }); // 等所有线程结束
        System.out.println(atomicIntegerArray);

    }
}
