package com.ddcode.stream.demo;

import com.ddcode.stream.po.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Demo_14_Stream_Reduce_Map {

    public static void main(String[] args) {
        //针对对象
        List<Person> onePerson = new ArrayList<>();
        Collections.addAll(onePerson,
                Person.builder().age(1).name("张三1").build(),
                Person.builder().age(2).name("张三2").build(),
                Person.builder().age(3).name("张三3").build()
        );


        //求集合中年龄之和【普通版】
        //map方法：重写生成一个stream, 里面放的是年龄
        Integer total = onePerson.stream().map(
                (Person p1) -> {
                    return p1.getAge();
                }
        ).reduce(0, (Integer i1, Integer i2) -> {
            return i1 + i2;
        });
        System.out.println("年龄之和 : " + total);

        //求集合中年龄之和【简写版1】
        Integer total2 = onePerson.stream().map(Person::getAge).reduce(0, (i1, i2) -> i1 + i2);
        System.out.println("年龄之和 : " + total2);

        //求集合中年龄之和【简写版2】
        Integer total3 = onePerson.stream().map(Person::getAge).reduce(0, Integer::sum);
        System.out.println("年龄之和 : " + total3);


        //找出最大年龄【普通版】
        Integer max = onePerson.stream().map(Person::getAge).reduce(0, (Integer i1, Integer i2) -> {
            return i1 > i2 ? i1 : i2;
        });
        System.out.println("最大值 : " + max);

        Integer max2 = onePerson.stream().map(Person::getAge).reduce(0, Math::max);
        System.out.println("最大值 : " + max2);


        //统计2中出现的次数
        Stream<Integer> stream = Stream.of(1, 2, 2, 1, 3, 2);
        //使用map生成一个新的stream
        Integer times = stream.map((Integer i1) -> {
            if (i1 == 2) {
                return 1;
            } else {
                return 0;
            }
        }).reduce(0, Integer::sum);
        System.out.println("2出现的次数 : " + times);

    }
}
