package com.ddcode.stream.optional;

import java.util.Optional;

public class Demo_1_QuickStart {

    public static void main(String[] args) {
        test1();
    }

    public static void test1(){
        Optional<String>  name = Optional.of("哈哈");

        System.out.println("isPresent " + name.isPresent());
        System.out.println("get " + name.get());
        //使用map可以对数据进行处理再返回
        System.out.println("map " + name.map((str)->str.length()).get());

        Optional<Object> empty = Optional.empty();
        System.out.println(empty.orElse("好好"));
        System.out.println(empty.orElseGet(()->"聊聊"));
    }
}
