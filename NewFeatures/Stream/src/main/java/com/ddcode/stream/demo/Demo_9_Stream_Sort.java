package com.ddcode.stream.demo;

import com.ddcode.stream.po.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Demo_9_Stream_Sort {

    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "1","5","3", "9");

        //普通方式
        System.out.println("字符串排序");
        one.stream().sorted().forEach(System.out :: println);

        //针对对象
        List<Person> onePerson = new ArrayList<>();
        Collections.addAll(onePerson,
                Person.builder().age(10).name("张三10").build(),
                Person.builder().age(29).name("张三29").build(),
                Person.builder().age(5).name("张三5").build(),
                Person.builder().age(30).name("张三30").build(),
                Person.builder().age(1).name("张三1").build()
                );

        //升序排序【普通版】
        System.out.println("对象排序, 普通版");
        onePerson.stream().sorted(
                (Person p1, Person p2)->{
                    return p1.getAge() - p2.getAge();
                }
        ).forEach(System.out::println);

        //升序排序【简写版】
        System.out.println("对象排序, 简写版");
        onePerson.stream().sorted((p1, p2)->p1.getAge()-p2.getAge()).forEach(System.out::println);

    }
}
