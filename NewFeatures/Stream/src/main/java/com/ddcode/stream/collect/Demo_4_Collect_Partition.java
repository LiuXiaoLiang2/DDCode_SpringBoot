package com.ddcode.stream.collect;

import com.ddcode.stream.po.Student;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream的分区
 */
public class Demo_4_Collect_Partition {
    public static void main(String[] args) {
        partition();
    }

    /**
     * 按照年龄分组
     */
    public static void partition(){
        Stream<Student> studentStream = Stream.of(
                Student.builder().name("张三1").age(15).score(70).build(),
                Student.builder().name("张三2").age(25).score(40).build(),
                Student.builder().name("张三3").age(15).score(30).build(),
                Student.builder().name("张三4").age(25).score(10).build()
        );

        Map<Boolean, List<Student>> collect = studentStream.collect(Collectors.partitioningBy((s1) -> s1.getScore() > 50));
        System.out.println(collect);
    }


}
