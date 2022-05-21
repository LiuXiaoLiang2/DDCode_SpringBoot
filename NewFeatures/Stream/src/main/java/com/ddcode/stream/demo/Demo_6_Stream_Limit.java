package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_6_Stream_Limit {

    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        //普通方式
        one.stream().limit(2).forEach(System.out::println);
    }
}
