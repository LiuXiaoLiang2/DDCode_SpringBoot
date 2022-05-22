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
public class Demo_1_Collect_QuickStart {

    public static void main(String[] args) {

        //转换成list集合
        Stream<String> stringStream = Stream.of("aa", "bb", "cc", "aa", "bb");
        List<String> stringList = stringStream.collect(Collectors.toList());
        System.out.println("list size " + stringList.size());

        //转换成set集合, 自带去重
        Stream<String> stringStream1 = Stream.of("aa", "bb", "cc", "aa", "bb");
        Set<String> stringSet = stringStream1.collect(Collectors.toSet());
        System.out.println("set size " + stringSet.size());

        //转化成ArrayList
        Stream<String> stringStream2 = Stream.of("aa", "bb", "cc", "aa", "bb");
        ArrayList<String> stringArrayList = stringStream2.collect(Collectors.toCollection(ArrayList::new));
        System.out.println("arrayList size " + stringArrayList.size());

        //转化成HashSet
        Stream<String> stringStream3 = Stream.of("aa", "bb", "cc", "aa", "bb");
        HashSet<String> stringHashSet = stringStream3.collect(Collectors.toCollection(HashSet::new));
        System.out.println("stringHashSet size " + stringHashSet.size());

        //转化成数组
        Stream<String> stringStream4 = Stream.of("aa", "bb", "cc", "aa", "bb");
        //Object[] objects = stringStream4.toArray(); //不方便操作

        String[] strings = stringStream4.toArray(String[]::new);
        System.out.println(strings);

    }
}
