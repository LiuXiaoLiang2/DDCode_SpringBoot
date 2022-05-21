package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_5_Stream_Filter {

    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        System.out.println("普通方式-------------");
        //普通方式
        one.stream().filter(
                (String name)->{
                    return name.length() == 3;
                }
        ).forEach(System.out::println);

        //简写方式
        System.out.println("简写方式------------");
        one.stream().filter(name->name.length()==3).forEach(System.out::println);
    }
}
