package com.ddcode.stream.collect;

import com.ddcode.stream.po.Student;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream的聚合
 */
public class Demo_3_Collect_Group {
    public static void main(String[] args) {
        groupByName();
        groupByScore();
        groupByMore();
    }

    /**
     * 按照年龄分组
     */
    public static void groupByName(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(50).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(15).score(30).build(),
                Student.builder().name("张三4").age(25).score(10).build()
        );

        Map<Integer, List<Student>> collect = studentStream.collect(Collectors.groupingBy(Student::getAge));
        System.out.println(collect);
    }

    public static void groupByScore(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(70).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(35).score(70).build(),
                Student.builder().name("张三4").age(45).score(40).build()
        );

//        Map<String, List<Student>> collect = studentStream.collect(Collectors.groupingBy(
//                (Student s1) -> {
//                    if (s1.getScore() > 50) {
//                        return "及格";
//                    } else {
//                        return "不及格";
//                    }
//                }
//        ));

        //简写版
        Map<String, List<Student>> collect = studentStream.collect(Collectors.groupingBy((s1)->s1.getScore()>50 ? "及格" : "不及格"));
        System.out.println(collect);
    }


    public static void groupByMore(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(60).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(15).score(30).build(),
                Student.builder().name("张三4").age(25).score(70).build()
        );
        Map<Integer, Map<String, List<Student>>> collect = studentStream.collect(Collectors.groupingBy(
                    Student::getAge,
                    Collectors.groupingBy((s1) -> s1.getScore() > 50 ? "及格" : "不及格"
                )));
        System.out.println(collect);
    }
}
