package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_3_Stream_Foreach {
    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子");

        //普通方式
        System.out.println("普通方式-----------");
        one.stream().forEach(
                (String name)-> {
                    System.out.println(name);
                }
        );

        //简写1: 省略 参数类型, 抽象方法的具体实现只有一行代码, 可以省略 {} , return 和 后面的 ;
        System.out.println("简写1-----------");
        one.stream().forEach(
                name-> System.out.println(name)
        );

        //简写2: 调用的是静态方法, 可以使用 ::
        System.out.println("简写2-----------");
        one.stream().forEach(System.out::println);
    }


}
