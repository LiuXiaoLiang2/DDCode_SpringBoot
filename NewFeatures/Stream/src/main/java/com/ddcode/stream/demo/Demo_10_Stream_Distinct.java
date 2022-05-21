package com.ddcode.stream.demo;

import com.ddcode.stream.po.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Demo_10_Stream_Distinct {

    public static void main(String[] args) {
        List<String> one = new ArrayList<>();
        Collections.addAll(one, "1","5","3","9","5","3");

        //普通方式
        System.out.println("字符串去重");
        one.stream().distinct().forEach(System.out :: println);

        //针对对象
        List<Person> onePerson = new ArrayList<>();
        Collections.addAll(onePerson,
                Person.builder().age(1).name("张三1").build(),
                Person.builder().age(2).name("张三2").build(),
                Person.builder().age(3).name("张三3").build(),
                Person.builder().age(1).name("张三1").build(),
                Person.builder().age(2).name("张三2").build(),
                Person.builder().age(3).name("张三3").build(),
                Person.builder().age(1).name("张三11").build()
         );

        onePerson.stream().distinct().forEach(System.out :: println);


    }
}
