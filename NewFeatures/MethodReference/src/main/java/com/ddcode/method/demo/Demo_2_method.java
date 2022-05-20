package com.ddcode.method.demo;

import java.util.Date;
import java.util.function.Supplier;

/**
 * 引入成员方法
 */
public class Demo_2_method {

    public static void main(String[] args) {
        Date now = new Date();

        //普通方式的lambda表达式
        Supplier<Long> supp = () -> {
            return now.getTime();
        };
        System.out.println(supp.get());

        //使用成员方法引入
        Supplier<Long> supp2 = now::getTime;
        System.out.println(supp2.get());
    }
}
