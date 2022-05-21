package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

/**
 * Stream注意的事项
 */
public class Demo_2_Stream_Notice {

    public static void main(String[] args) {
        //组装集合
        Stream<String> stream = Stream.of("1", "2", "3");
//        stream.count();
//        stream.count();

        stream.filter(
                (String s)->{
                    System.out.println("执行stream filter : " + s);
                    return true;
                }
        ).count();

    }
}
