package com.ddcode.stream.collect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 将Stream中的数据转换成集合或数组
 */
public class Demo_5_Collect_Join {

    public static void main(String[] args) {

        //转换成list集合
        Stream<String> stringStream = Stream.of("aa", "bb", "cc", "aa", "bb");
        String collect = stringStream.collect(Collectors.joining("#", "^", "&"));
        System.out.println(collect);


    }
}
