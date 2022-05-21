package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Demo_8_Stream_Map {

    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "1","22","333");

        //普通方式
        Stream<Integer> integerStream = one.stream().map(
                (String name) -> {
                    return Integer.parseInt(name);
                }
        );
        integerStream.forEach(System.out::println);

        //简写方式
        Stream<Integer> integerStream1 = one.stream().map(Integer::parseInt);
    }
}
