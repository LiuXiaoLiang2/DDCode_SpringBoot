package com.ddcode.generics;

import java.util.ArrayList;

/**
 * 泛型的背景
 */
public class Demo_1_QuickStart {

    public static void main(String[] args) {
        test1();
    }


    public static void test1(){
        //使用集合不指定泛型
        ArrayList arrayList = new ArrayList();
        arrayList.add(100);
        arrayList.add("123");
        arrayList.add(true);

        for (int i = 0; i < arrayList.size(); i++) {
            Object o = arrayList.get(i);
            String str = (String) o;

            System.out.println(str);
        }
    }

    public static void test2(){
        //使用集合不指定泛型
        ArrayList<String> arrayList = new ArrayList();
        arrayList.add("123");
        arrayList.add("456");
        arrayList.add("789");

        for (int i = 0; i < arrayList.size(); i++) {
            String str = arrayList.get(i);
            System.out.println(str);
        }
    }


    public static void test3(){
        //使用集合不指定泛型
        ArrayList<Integer> arrayList = new ArrayList();
        //将int类型自动装箱转化成Integer
        arrayList.add(123);
        arrayList.add(456);
        arrayList.add(789);

        for (int i = 0; i < arrayList.size(); i++) {
            //将Integer自动拆箱转化成int类型
            int str = arrayList.get(i);
            System.out.println(str);
        }
    }
}
