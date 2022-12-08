package com.ddcode.generics;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 泛型数组的使用
 */
public class Demo_9_Generics_Array {

    public static void main(String[] args) {
        testGenericsArray2();
    }

    public static void testGenericsArray1(){
        //创建数组对象，不需要使用<>
        ArrayList<String>[] arrayLists = new ArrayList[5];

        //创建两个对象
        ArrayList<String> stringArrayList = new ArrayList();
        ArrayList<Integer> integerArrayList = new ArrayList();

        arrayLists[0] = stringArrayList;
        //报错，说明泛型在编译阶段的拦截起到了作用
        // arrayLists[1] = integerArrayList;
    }

    public static void testGenericsArray2(){
        GenericsArray<String> stringGenericsArray = new GenericsArray<>(String.class, 3);
        stringGenericsArray.put("张三", 0);
        stringGenericsArray.put("王五", 1);
        stringGenericsArray.put("赵四", 2);

        String[] strings = stringGenericsArray.get();
        System.out.println(Arrays.toString(strings));
        String str = stringGenericsArray.get(1);
        System.out.println(str);
    }
}


class GenericsArray<T>{
    //创建数组对象
    public T[] arrays;

    public  GenericsArray(Class<T> clz, Integer length){
        //参数1: 表示给哪个类型对象创建数组
        //参数2: 数组的长度
        //需要强转
        arrays = (T[]) Array.newInstance(clz, length);
    }

    public void put(T t, Integer index){
        arrays[index] = t;
    }

    public T get(Integer index){
        return arrays[index];
    }

    public T[] get(){
        return arrays;
    }
}