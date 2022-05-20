package com.ddcode.interfacePlus.demo;

import java.util.Arrays;
import java.util.function.Supplier;

public class Demo_3_Supplier {

    public static void main(String[] args) {
        //调用下面的静态方法
        printMax(
                //没有参数
                ()->{
                    Integer[] arr ={1,45,6,34};
                    Arrays.sort(arr);
                    return arr[arr.length - 1];
                }
        );

    }


    public static void printMax(Supplier<Integer> supplier){
        Integer max = supplier.get();
        System.out.println("max :" + max);
    }
}
