package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Demo_12_Stream_Find {

    public static void main(String[] args) {
        List<Integer> one = new ArrayList<>();
        Collections.addAll(one, 1, 5, 6, 8);

        //普通方式
        Optional<Integer> any = one.stream().findAny();
        System.out.println(any.get());

        Optional<Integer> first = one.stream().findFirst();
        System.out.println(first.get());

    }
}
