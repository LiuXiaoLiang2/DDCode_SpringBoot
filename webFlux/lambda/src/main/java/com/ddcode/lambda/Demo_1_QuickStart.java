package com.ddcode.lambda;


import lombok.extern.slf4j.Slf4j;

import java.util.stream.IntStream;

@Slf4j(topic = "c.Demo_1_QuickStart")
public class Demo_1_QuickStart {

    public static void main(String[] args) {
        commandCode();
        responseCode();
        responseCodePro();
    }

    /**
     * 命令式编程，有具体解决实现
     */
    public static void commandCode(){
        int[] nums = {10,20,30,-90,55};
        int min = nums[0];

        for(int i=0; i<nums.length; i++){
            if(nums[i] < min) {
                min = nums[i];
            }
        }
        log.info("commandCode min {}", min);
    }


    /**
     * 响应式编程, 不需要关注内部实现过程，利用现有的api直接实现
     * 更关注的是结果
     */
    public static void responseCode(){
        int[] nums = {10,20,30,-90,55};
        int min = IntStream.of(nums).min().getAsInt();
        log.info("responseCode min {}", min);
    }


    /**
     * 响应式编程扩展
     * 假设数组的数据有几亿条，如果这个时候再单纯的时候for循环，计算速度就很慢了, 因为是单线程
     * 为了优化计算，可以使用多线程，如果使用命令行编程就需要你自己实现，需要创建线程池，给数组拆分计算，这样很麻烦
     * 如果使用响应式编程，可以直接使用现成的方法 parallel()：该方法表示流stream要并行执行
     * 响应式编程更关注的是结果
     */
    public static void responseCodePro(){
        int[] nums = {10,20,30,-90,55};
        int min = IntStream.of(nums).parallel().min().getAsInt();
        log.info("responseCodePro min {}", min);
    }
}
