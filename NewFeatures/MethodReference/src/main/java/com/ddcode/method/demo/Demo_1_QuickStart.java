package com.ddcode.method.demo;

import java.util.function.Consumer;

/**
 * lambda的方法引用
 */
public class Demo_1_QuickStart {

    /**
     * 获取数组之和
     * @param ints
     * @return
     */
    public static Integer getTotal(int[] ints){
        Integer result =0;
        for (int anInt : ints) {
            result = result + anInt;
        }
        System.out.println("result : " + result);
        return result;
    }


    public static void printTotal(Consumer<int[]> consumer){
        int[] ints = {1,2,3,4,5};
        consumer.accept(ints);
    }

    public static void main(String[] args) {

        //普通写法
        printTotal(
                (int[] ints) -> {
                    getTotal(ints);
                }
        );


        //方法引用
        printTotal(
                Demo_1_QuickStart::getTotal
        );
    }
}
