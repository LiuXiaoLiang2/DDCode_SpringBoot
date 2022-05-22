package com.ddcode.stream.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Demo_3_Parallel_Question {

    public static void main(String[] args) {
        question();
        resolve1();
        resolve2();
        resolve3();
    }

    public static void question(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        List<Integer> newList = new ArrayList<>();
        // 使用并行的流往集合中添加数据
        list.stream().parallel().forEach((i)->{
            newList.add(i);
        });
        System.out.println("newList size " + newList.size());
    }

    public static void resolve1(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        Object object = new Object();
        List<Integer> newList = new ArrayList<>();
        // 使用并行的流往集合中添加数据
        list.stream().parallel().forEach((i)->{
            synchronized (object){
                newList.add(i);
            }
        });
        System.out.println("resolve1 newList size " + newList.size());
    }

    public static void resolve2(){
        ArrayList<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }
        Vector<Integer> integers = new Vector<>();
        // 使用并行的流往集合中添加数据
        list.stream().parallel().forEach((i)->{
            integers.add(i);
        });
        System.out.println("integers newList size " + integers.size());
    }

    public static void resolve3(){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 1000; i++) {
            list.add(i);
        }

        List<Integer> newList = new ArrayList<>();
        //转化成线程安全的list
        List<Integer> synchronizedList = Collections.synchronizedList(newList);
        // 使用并行的流往集合中添加数据
        list.stream().parallel().forEach((i)->{
            synchronizedList.add(i);
        });
        System.out.println("newList size " + newList.size());
    }

}
