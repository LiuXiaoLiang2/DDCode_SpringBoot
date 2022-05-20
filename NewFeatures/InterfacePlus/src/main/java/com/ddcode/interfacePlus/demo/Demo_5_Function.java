package com.ddcode.interfacePlus.demo;

import java.util.function.Function;

public class Demo_5_Function {

    public static void main(String[] args) {

        convertType(
                (String str)->{
                    //将传来的字符串转换成int类型
                    return Integer.parseInt(str);
                }
        );

        convertType(
                (String str)->{
                    //将传来的字符串转换成int类型
                    return Integer.parseInt(str);
                },
                (Integer apply)->{
                    //将传来的字符串转换成int类型
                    return apply * 3;
                }
        );
    }
    
    public static void convertType(Function<String, Integer> function){
        String str = "10";
        Integer apply = function.apply(str);
        System.out.println(apply);
    }

    /**
     * 使用andThen组合操作
     * @param function1
     * @param function2
     */
    public static void convertType(Function<String, Integer> function1, Function<Integer, Integer> function2){
        String str = "10";
        Integer apply = function1.apply(str);
        System.out.println(apply);
        Integer result = function2.apply(apply);
        System.out.println(result);

        Integer result2 = function1.andThen(function2).apply(str);
        System.out.println(result2);
    }
}
