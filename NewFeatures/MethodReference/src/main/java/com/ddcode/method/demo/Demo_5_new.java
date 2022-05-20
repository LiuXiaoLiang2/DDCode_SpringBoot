package com.ddcode.method.demo;

import com.ddcode.method.po.Person;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class Demo_5_new {
    public static void main(String[] args) {
        Supplier<Person> sup = () -> {
            return new Person();
        };
        System.out.println(sup.get());


        Supplier<Person> sup2 = Person::new;
        System.out.println(sup2.get());


        BiFunction<String, Integer, Person> fun2 = Person::new;
        System.out.println(fun2.apply("张三", 18));
    }
}
