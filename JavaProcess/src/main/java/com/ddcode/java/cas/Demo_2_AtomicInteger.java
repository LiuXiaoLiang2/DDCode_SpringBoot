package com.ddcode.java.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger的基本使用
 */
public class Demo_2_AtomicInteger {

    public static void main(String[] args) {
//        base();
//        advanced();
        advancedPro();
    }


    /**
     * 基本使用
     */
    public static void  base(){
        /**
         * 创建AtomicInteger, 并初始化为0
         */
        AtomicInteger i = new AtomicInteger(0);
        // 获取并自增（i = 0, 结果 i = 1, 返回 0），类似于 i++
        System.out.println(i.getAndIncrement());
        // 自增并获取（i = 1, 结果 i = 2, 返回 2），类似于 ++i
        System.out.println(i.incrementAndGet());
        // 自减并获取（i = 2, 结果 i = 1, 返回 1），类似于 --i
        System.out.println(i.decrementAndGet());
        // 获取并自减（i = 1, 结果 i = 0, 返回 1），类似于 i--
        System.out.println(i.getAndDecrement());
        // 获取并加值（i = 0, 结果 i = 5, 返回 0）
        System.out.println(i.getAndAdd(5));
        // 加值并获取（i = 5, 结果 i = 0, 返回 0）
        System.out.println(i.addAndGet(-5));
    }

    /**
     * 高级使用
     */
    public static void advanced(){

        /**
         * 创建AtomicInteger, 并初始化为0
         */
        AtomicInteger atomicInteger = new AtomicInteger(0);
        //更新并获取, 返回的是更新后的值
        int result1 = atomicInteger.updateAndGet(m -> m + 2);
        System.out.println(result1);

        //获取并更新, 返回的是更新前的值
        int result2 = atomicInteger.getAndUpdate(m -> m - 2);
        System.out.println(result2);
    }

    /**
     * 高级使用Pro
     */
    public static void advancedPro(){

        /**
         * 创建AtomicInteger, 并初始化为 2
         */
        AtomicInteger atomicInteger = new AtomicInteger(2);
        int init = 10;
        int andAccumulate = atomicInteger.accumulateAndGet(init, (x, y) -> x * y);
        System.out.println(andAccumulate);
    }

}
