package com.ddcode.stream.demo;

import com.ddcode.stream.po.PersonName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Demo_15_Last {
    public static void main(String[] args) {
        List<String> one = Arrays.asList("迪丽热巴", "宋远桥", "苏星河", "老子", "庄子", "孙子", "洪七公");
        List<String> two = Arrays.asList("古力娜扎", "张无忌", "张三丰", "赵丽颖", "张二狗", "张天爱", "张三");

        // 第一个队伍只要名字为3个字的成员姓名；
        // 第一个队伍筛选之后只要前3个人
        one.stream().filter(name->name.length()==3).limit(3).forEach(System.out::println);

        // 第二个队伍只要姓张的成员姓名；
        // 第二个队伍筛选之后不要前2个人
        two.stream().filter(name->name.startsWith("张")).skip(2).forEach(System.out::println);

        // 将两个队伍合并为一个队伍；
        Stream<String> concat = Stream.concat(one.stream(), two.stream());
        // 根据姓名创建Person对象；
        // 打印整个队伍的Person对象信息

        /**
         * 实现:
         * 将concat中的对象set到personName这个实体类
         * 使用map方法进行转换
         * PersonName::new 这里调用的是有参构造
         */
        concat.map(PersonName::new).forEach(System.out::println);

    }
}
