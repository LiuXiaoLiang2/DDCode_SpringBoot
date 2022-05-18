package com.ddcode.lambda.demo;

import com.ddcode.lambda.po.Person;
import com.ddcode.lambda.service.OrderService;
import com.ddcode.lambda.service.impl.OrderServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Demo_02_Lambda_Use_ParamReturn_Plus {

    public static void main(String[] args) {

        Person person1 = Person.builder().age(10).name("张三").build();
        Person person2 = Person.builder().age(15).name("李四").build();
        Person person3 = Person.builder().age(20).name("王五").build();

        List<Person> list = new ArrayList<>();
        list.add(person1);
        list.add(person2);
        list.add(person3);

        oldSort(list);
        lambdaSort(list);

    }

    /**
     * 传统方式升序排序
     * @param list
     */
    public static void oldSort(List<Person> list){
        System.out.println("传统方式");
        Collections.sort(list, new Comparator<Person>(){
            @Override
            public int compare(Person o1, Person o2) {
                return o1.age - o2.age;
            }
        });
        for (Person person : list) {
            System.out.println(person);
        }
    }


    public static void lambdaSort(List<Person> list){
        System.out.println("lambda方式");
        Collections.sort(list, (Person p1, Person p2)-> {
            return p1.getAge() - p2.getAge();
        });

        list.forEach((Person p1)->{
            System.out.println(p1);
        });
    }


    public static void lambdaSortPlus(List<Person> list){
        System.out.println("lambda省略方式");

        Collections.sort(list, (p1, p2)-> p1.getAge() - p2.getAge());

        list.forEach(p1-> System.out.println(p1));
    }
}
