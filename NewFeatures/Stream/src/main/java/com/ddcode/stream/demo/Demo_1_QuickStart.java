package com.ddcode.stream.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_1_QuickStart {

    /**
     * 一个 ArrayList集合中存储有以下数据:张无忌,周芷若,赵敏,张强,张三丰
     * 需求:
     * 1.拿到所有姓张的
     * 2.拿到名字长度为3个字的
     * 3.打印这些数据
     */
    public static void oldList(){
        //组装集合
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "张无忌", "周芷若", "赵敏", "张强", "张三丰");

        // 1.拿到所有姓张的
        ArrayList<String> zhangList = new ArrayList<>(); // {"张无忌", "张强", "张三丰"}
        for (String name : names) {
            if(name.startsWith("张")){
                zhangList.add(name);
            }
        }

        // 2.拿到名字长度为3个字的
        ArrayList<String> threeList = new ArrayList<>(); // {"张无忌", "张三丰"}
        for (String name : zhangList) {
            if(name.length() == 3){
                threeList.add(name);
            }
        }

        // 3.打印这些数据
        for (String name : threeList) {
            System.out.println(name);
        }
    }

    public static void stream(){
        //组装集合
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "张无忌", "周芷若", "赵敏", "张强", "张三丰");
        names.stream()
             .filter(
                (String name)->{
                    return name.startsWith("张");
                }
             ).filter(
                (String name)->{
                    return name.length() == 3;
                }
             ).forEach(
                (String name)->{
                    System.out.println(name);
                }
        );
    }

    public static void streamPlus(){
        //组装集合
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "张无忌", "周芷若", "赵敏", "张强", "张三丰");
        names.stream().filter(name -> name.startsWith("张")).filter(name -> name.length() == 3).forEach(System.out::println);
    }


    public static void main(String[] args) {
        System.out.println("传统方式");
        oldList();
        System.out.println("stream方式");
        stream();
        streamPlus();
    }
}
