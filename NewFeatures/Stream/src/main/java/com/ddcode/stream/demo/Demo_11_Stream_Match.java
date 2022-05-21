package com.ddcode.stream.demo;

import com.ddcode.stream.po.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_11_Stream_Match {

    public static void main(String[] args) {
        List<Integer> one = new ArrayList<>();
        Collections.addAll(one, 1, 5, 6, 8);

        //普通方式
        System.out.println("普通匹配");
        //判断集合中的数据是否都大于3
        //如果都大于3才能返回 true
        boolean b = one.stream().allMatch(
                (Integer i) -> {
                    return i > 3;
                }
        );
        System.out.println(b);

        System.out.println("简写版");
        //简写版
        boolean b1 = one.stream().allMatch(i -> i > 3);
        System.out.println(b1);

    }
}
