package com.ddcode.stream.collect;

import com.ddcode.stream.po.Student;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream的聚合
 */
public class Demo_2_Collect_Aggregation {
    public static void main(String[] args) {
        collectMax();
        collectMin();
        sum();
        avg();
        count();
    }

    /**
     * 获取最大值
     */
    public static void collectMax(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(50).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(35).score(30).build(),
                Student.builder().name("张三4").age(45).score(10).build()
        );

        //普通版
//        Optional<Student> collect = studentStream.collect(Collectors.maxBy(
//                (s1, s2) -> {
//                    return s1.getAge() - s2.getAge();
//                }
//        ));

        //简写版
        Optional<Student> collect = studentStream.collect(Collectors.maxBy((s1,s2)->s1.getAge()-s2.getAge()));
        System.out.println(collect.get());
    }

    /**
     * 获取最大值
     */
    public static void collectMin(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(50).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(35).score(30).build(),
                Student.builder().name("张三4").age(45).score(10).build()
        );

        Optional<Student> collect = studentStream.collect(Collectors.minBy((s1, s2) -> s1.getAge() - s2.getAge()));
        System.out.println(collect.get());
    }

    /**
     * 求和
     */
    public static void sum(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(50).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(35).score(30).build(),
                Student.builder().name("张三4").age(45).score(10).build()
        );

        //Integer collect = studentStream.collect(Collectors.summingInt((s) -> s.getAge()));
        //简写
        Integer collect = studentStream.collect(Collectors.summingInt(Student::getAge));
        System.out.println(collect);
    }


    /**
     * 求平均值
     */
    public static void avg(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(50).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(35).score(30).build(),
                Student.builder().name("张三4").age(45).score(10).build()
        );

        Double collect = studentStream.collect(Collectors.averagingInt(Student::getAge));
        System.out.println(collect);
    }

    /**
     * 统计数量
     */
    public static void count(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(50).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(35).score(30).build(),
                Student.builder().name("张三4").age(45).score(10).build()
        );

        Long collect = studentStream.collect(Collectors.counting());
        System.out.println(collect);
    }

}
