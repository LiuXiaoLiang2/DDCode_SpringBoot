package com.ddcode.stream.optional;

import com.ddcode.stream.po.Person;

import java.util.Optional;

public class Demo_2_More {

    public static void main(String[] args) {
        Person hello = Person.builder().name("Hello").build();
        System.out.println(test1(hello));
        System.out.println(test2(hello));

    }

    //定义一个方法将User中的用户名转成大写并返回

    //传统方式
    public static String test1(Person p1){
        if(p1 != null){
            String name = p1.getName();
            if(name != null){
                return name.toUpperCase();
            }
        }
        return null;
    }

    //optional方式
    public static String test2(Person p1){
        Optional<Person> optionalPerson = Optional.of(p1);
        String result = optionalPerson.map(Person::getName).map(String::toUpperCase).orElse(null);
        return result;
    }

}
