package com.ddcode.interfacePlus.demo;

import java.util.function.Consumer;

public class Demo_4_Consumer {

    public static void main(String[] args) {

        convert(
                (String str)->{
                    System.out.println(str.toLowerCase());
                }
        );

        convert(
                (String str)->{
                    System.out.println(str.toLowerCase());
                }
                ,
                (String str)->{
                    System.out.println(str.toUpperCase());
                }
        );

        convertAndThen(
                (String str)->{
                    System.out.println(str.toLowerCase());
                }
                ,
                (String str)->{
                    System.out.println(str.toUpperCase());
                }
        );
    }

    /**
     * 一个参数
     * @param consumer
     */
    public static void convert(Consumer<String> consumer){
        consumer.accept("ABCD");
    }

    /**
     * 两个参数
     * @param consumer1
     * @param consumer2
     */
    public static void convert(Consumer<String> consumer1, Consumer<String> consumer2){
        String str = "Hello world";
        consumer1.accept(str);
        consumer2.accept(str);
    }

    /**
     * 调用AndThen
     * @param consumer1
     * @param consumer2
     */
    public static void convertAndThen(Consumer<String> consumer1, Consumer<String> consumer2){
        String str = "Hello world";
        consumer1.andThen(consumer2).accept(str);
    }
}
