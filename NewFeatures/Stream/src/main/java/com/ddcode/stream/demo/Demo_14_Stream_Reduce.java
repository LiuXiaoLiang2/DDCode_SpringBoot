package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Demo_14_Stream_Reduce {

    public static void main(String[] args) {
        List<Integer> one = new ArrayList<>();
        Collections.addAll(one, 1, 2, 3, 4);

        //求和-普通版
        Integer reduce = one.stream().reduce(0, (Integer i1, Integer i2) -> {
            System.out.println("i1 : " + i1 + ", i2: " + i2);
            return i1 + i2;
        });
        System.out.println(reduce);


        //求和-简写版
        Integer reduce1 = one.stream().reduce(0, (i1, i2) -> i1 + i2);
        System.out.println(reduce);

    }
}
