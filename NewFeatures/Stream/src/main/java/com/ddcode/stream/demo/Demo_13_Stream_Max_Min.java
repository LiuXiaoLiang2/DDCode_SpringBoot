package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Demo_13_Stream_Max_Min {

    public static void main(String[] args) {
        List<Integer> one = new ArrayList<>();
        Collections.addAll(one, 1, 5, 6, 8);

        /**
         * max的具体实现
         * 第一步：先排序
         * 第二步：取最后一个值, 所以为了能实现最后一个值是max最大值,那么排序必须是升序, 否则按照降序的, 最后一个值是最小的
         */
        //普通方式
        Optional<Integer> max = one.stream().max(
                (Integer i1, Integer i2) -> {
                    return i1 - i2;
                }
        );
        System.out.println(max.get());

        //简写版
        Optional<Integer> max1 = one.stream().max((i1, i2) -> i1 - i2);
        System.out.println(max1.get());


        /**
         * min的具体实现
         * 第一步：先排序
         * 第二步：取第一个值, 所以为了能实现最后一个值是max最大值,那么排序必须是升序, 否则按照降序的, 第一个值是最大的
         */
        Optional<Integer> min = one.stream().min((i1, i2) -> i1 - i2);
        System.out.println(min.get());

    }
}
