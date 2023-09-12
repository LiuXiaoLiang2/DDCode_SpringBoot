package com.ddcode.stream;

import com.ddcode.po.User;
import com.google.errorprone.annotations.Var;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * Stream的创建
 */
@Slf4j(topic = "c.Demo_2_Stream_Create")
public class Demo_2_Stream_Create {

    public static void main(String[] args) {
        method6();
    }

    /**
     * 通过集合的方式创建
     */
    public static void method1(){
        List<String> list = new ArrayList<>();
        //从集合创建
        //串行流
        Stream<String> stream = list.stream();
        //并行流
        Stream<String> stream1 = list.parallelStream();
    }

    /**
     * 通过数组创建
     */
    public static void method2(){

        int[] ints = {1,2,3};
        IntStream intStream = Arrays.stream(ints);

        //可以直接操作自定义对象
        User user1 = User.builder().id(1L).userName("jack").pwd("123").build();
        User user2 = User.builder().id(2L).userName("tom").pwd("123").build();
        User[] users = {user1, user2};

        Stream<User> userStream = Arrays.stream(users);

        Stream<User> stream = Stream.of(user1, user2);

    }

    /**
     * 自带的数字类型流
     */
    public static void method3(){
        //直接使用JDK自带的
        IntStream intStream = IntStream.of(1, 2, 3);
        LongStream longStream = LongStream.of(1L, 2L, 3L);
    }

    /**
     * 创建无限流
     */
    public static void method4(){
        Random random = new Random();
        IntStream ints = random.ints();
        LongStream longs = random.longs();
        //会一直输出
        ints.forEach(i -> log.info("输出无限流 {}" , i));
    }

    /**
     * 有限制的无限流
     */
    public static void method5(){
        Random random = new Random();
        LongStream longs = random.longs();
        //限制输出10个
        longs.limit(10);
        //会一直输出
        longs.forEach(i -> log.info("输出有限流 {}" , i));
    }

    /**
     * 输出自定义流
     */
    public static void method6(){
        Random random = new Random();
        Stream<Integer> integerStream = Stream.generate(() -> random.nextInt()).limit(20);
        integerStream.forEach(i -> log.info("输出自定义流 {}" , i));
    }

}
