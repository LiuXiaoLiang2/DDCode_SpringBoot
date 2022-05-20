package com.ddcode.interfacePlus.demo;

import java.util.function.Predicate;

public class Demo_6_Predicate {
    public static void main(String[] args) {

        check(
                (String str) -> {
                    return str.length() > 3;
                }

        );

        check(
                (String str) -> {
                    return str.contains("H");
                },
                (String str) -> {
                    return str.contains("W");
                }

        );
    }

    public static void check(Predicate<String> predicate){
        String str = "哈哈哈哈";
        boolean test = predicate.test(str);
        System.out.println(test);
    }

    public static void check(Predicate<String> predicate1, Predicate<String> predicate2){
        String str = "Hello World";
        boolean test1 = predicate1.test(str);
        boolean test2 = predicate2.test(str);

        if(test1 && test2){
            System.out.println("既有H又有W");
        }

        boolean test = predicate1.and(predicate2).test(str);
        System.out.println(test);
    }
}
