package com.ddcode.method.demo;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 调用实例方法
 */
public class Demo_4_instance_method {
    public static void main(String[] args) {

        Function<String, Integer> f1 = (s) -> {
            return s.length();
        };
        System.out.println(f1.apply("abc"));


        Function<String, Integer> f2 = String::length;
        System.out.println(f2.apply("abc"));


        BiFunction<String, Integer, String> bif = String::substring;
        String hello = bif.apply("hello", 2);
        System.out.println("hello = " + hello);
    }
}
