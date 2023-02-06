package com.ddcode.method;

import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

/**
 * 方法引用
 */
@Slf4j(topic = "c.Demo_1_Method_QuickStart")
public class Demo_1_Method_QuickStart {

    public static int[] ints = {1,2,3,4,5};

    public static void sum(int[] ints){
        int result = 0;
        for (int i = 0; i < ints.length; i++) {
            result = result + ints[i];
        }
        log.info("求和结果 {}", result);
    }

    public static void printTotal(Consumer<int[]> consumer){
        consumer.accept(ints);
    }

    public static void main(String[] args) {
        //普通的lambda表达式写法
        Consumer<int[]> consumer1 = i->sum(i);
        printTotal(consumer1);

        //方法引用的写法
        Consumer<int[]> consumer2 = Demo_1_Method_QuickStart::sum;
        printTotal(consumer2);
    }
}
