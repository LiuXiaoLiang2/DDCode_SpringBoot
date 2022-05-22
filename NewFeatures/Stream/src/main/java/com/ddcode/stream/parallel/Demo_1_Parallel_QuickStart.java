package com.ddcode.stream.parallel;

import java.util.stream.Stream;

public class Demo_1_Parallel_QuickStart {

    public static void main(String[] args) {
        //转化成数组
        Stream<String> stream = Stream.of("aa", "bb", "cc", "aa", "bb");

        stream.forEach((String str)->{
            System.out.println(Thread.currentThread().getName() + ": " + str);
        });
    }
}
