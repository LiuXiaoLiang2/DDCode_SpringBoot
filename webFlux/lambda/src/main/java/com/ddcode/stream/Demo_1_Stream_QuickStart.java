package com.ddcode.stream;

import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

/**
 * 外部迭代和内部迭代
 */
@Slf4j(topic = "c.Demo_1_Stream_QuickStart")
public class Demo_1_Stream_QuickStart {

    public static void main(String[] args) {
        externalIteration();
        internalIteration();
        noEnd();
        doEnd();
    }

    /**
     * 外部迭代，传统方式
     */
    public static void externalIteration(){
        int[] nums = {1,2,3};
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        log.info("传统方式 {}", sum);
    }

    /**
     * 内部迭代，Stream方式
     */
    public static void internalIteration(){
        int[] nums = {1,2,3};
        int sum = IntStream.of(nums).sum();
        log.info("Stream方式 {}", sum);
    }

    /**
     * 惰性求值：终止操作没有调用的情况下，中间操作不会被执行
     */
    public static void noEnd(){
        log.info("没有终结");
        int[] nums = {1,2,3};
        IntStream intStream = IntStream.of(nums).map(Demo_1_Stream_QuickStart::multiNum);
    }

    /**
     * 有终止
     */
    public static void doEnd(){
        log.info("有终结");
        int[] nums = {1,2,3};
        int sum = IntStream.of(nums).map(Demo_1_Stream_QuickStart::multiNum).sum();
        log.info("有终结, 最终结果 {}", sum);
    }

    public static int multiNum(int i){
        log.info("i: {} 执行了乘法", i);
        return i * 2;
    }
}
