package com.ddcode.stream;

import com.ddcode.po.User;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream流的中间操作
 */
@Slf4j(topic = "c.Demo_3_Stream_Operation")
public class Demo_3_Stream_Operation {

    /**
     * 操作map方法
     * 计算每个单词的长度
     */
    public static void mapMethod1(){
        String str = "my name is 007";

        //返回最终的流
        Stream<Integer> integerStream = Stream.of(str.split(" ")).map(s -> s.length());
        //输出打印
        integerStream.forEach(s->log.info("mapMethod1 {}", s));

    }


    /**
     * 将集合中的每个字符串，全部转化成大写
     */
    public static void mapMethod2(){
        List<String> alpha = Arrays.asList("Monkey", "Lion", "Giraffe", "Lemur");
        // 使用Stream管道流
        List<String> collect = alpha.stream().map(String::toUpperCase).collect(Collectors.toList());
        //上面使用了方法引用，和下面的lambda表达式语法效果是一样的
        //List<String> collect = alpha.stream().map(s -> s.toUpperCase()).collect(Collectors.toList())
        log.info("mapMethod2 {}", collect);
    }

    /**
     * 再复杂一点：处理对象数据格式转换
     */
    public static void mapMethod3(){
        User user1 = User.builder().id(1L).userName("lucy").age(10).sex("M").build();
        User user2 = User.builder().id(2L).userName("jack").age(20).sex("F").build();
        User user3 = User.builder().id(3L).userName("tom").age(15).sex("F").build();

        //需求将age增加1岁， 将性别中的“M”换成“male”，F换成Female
        List<User> userList = Arrays.asList(user1, user2, user3);
        //使用stream转化
        List<User> users = userList.stream().map(u -> {
            u.setAge(u.getAge() + 1);
            u.setSex(u.getSex().equals("M") ? "Male" : "Female");
            // 因为这里返回的是 u , 所以最终返回的是 List<User>, 如果返回是Integer类型, 那么最终返回的是 List<Integer>
            return u;
        }).collect(Collectors.toList());
        log.info("mapMethod3 {}", users);
    }

    /**
     * peek函数
     * peek函数是一种特殊的map函数，当函数没有返回值或者参数就是返回值的时候可以使用peek函数
     */
    public static void peekMethod(){
        User user1 = User.builder().id(1L).userName("lucy").age(10).sex("M").build();
        User user2 = User.builder().id(2L).userName("jack").age(20).sex("F").build();
        User user3 = User.builder().id(3L).userName("tom").age(15).sex("F").build();

        //需求将age增加1岁， 将性别中的“M”换成“male”，F换成Female
        List<User> userList = Arrays.asList(user1, user2, user3);
        //使用stream转化
        List<User> users = userList.stream().peek(u -> {
            u.setAge(u.getAge() + 1);
            u.setSex(u.getSex().equals("M") ? "Male" : "Female");
        }).collect(Collectors.toList());
        log.info("peekMethod {}", users);
    }

    /**
     * map的扩展方法
     */
    public static void mapToIntMethod(){
        String str = "my name is 007";
        //这里返回的IntStream其实就是相当于Stream<Integer>
        //要保证的是mapToInt方法内部返回的是int类型
        IntStream intStream = Stream.of(str.split(" ")).mapToInt(s -> s.length());
        //输出打印
        intStream.forEach(i->log.info("mapToIntMethod {}",i));
    }


    /**
     *
     */
    public static void flatMapMethod1(){
        List<String> words = Arrays.asList("h e l l o", "w o r d");
        Stream<String[]> stream = words.stream().map(w ->
        {
            log.info("w, {}", w);
            log.info("split, {}", w.split(" ").length);
            //这里返回的就是String[],所以最终返回的就是 Stream<String[]>
            return w.split(" ");

        });
        log.info("flatMapMethod count {}", stream.count());

    }

    public static void flatMapMethod3(){
        List<String> words = Arrays.asList("h e l l o", "w o r d");
        Stream<String> stream = words.stream().flatMap(
                word -> {
                    log.info("word {}", word);
                    return Stream.of(word.split(" "));
                }
        );

        log.info("flatMapMethod3 count {}", stream.count());
    }

    public static void flatMapMethod2(){
        List<String> words = Arrays.asList("h e l l o", "w o r d");
        Stream<String> stream = words.stream().flatMap(
                word ->{
                    log.info("word {}", word);
                    return Stream.of(word.split(" "));
                }
        );
        log.info("-------------");
        stream.forEach(i->log.info("flatMapMethod2 {}", i));
    }


    public static void flatMapMethod4(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        List<String[]> a = Arrays.stream(words)
                .map(word -> word.split(""))
                .distinct()
                .collect(Collectors.toList());

        a.forEach(System.out::print);
    }


    public static void flatMapMethod5(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        Stream<String> stream = Arrays.stream(words);
        Stream<String[]> stream1 = stream.map(word -> word.split(""));
        Stream<String> stream2 = stream1.flatMap(wordsArray -> Arrays.stream(wordsArray));
        Stream<String> distinct = stream2.distinct();
        log.info("-------------");
        distinct.forEach(System.out::print);
    }


    public static void flatMapMethod6(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        Stream<String> stream = Arrays.stream(words);
        Stream<String[]> stream1 = stream.map(word -> word.split(""));
        Stream<String> stream2 = stream1.flatMap(wordsArray -> Stream.of(wordsArray));
        //Stream<String> distinct = stream2.distinct();
        log.info("-------------");
        stream2.forEach(System.out::print);
    }


    public static void flatMapMethod7(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        List<String> collect = Stream.of(words).flatMap(word -> Stream.of(word.split(""))).distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    /**
     * 遍历
     */
    public static void foreach(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        Stream.of(words).flatMap(word -> Stream.of(word.split(""))).forEach(System.out::print);
    }

    /**
     * 将Stream转化成集合
     */
    public static void collectSet(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        Set<String> set = Stream.of(words).flatMap(word -> Stream.of(word.split(""))).collect(Collectors.toSet());
    }


    public static void collectList(){
        System.out.println();
        String[] words = new String[]{"Hello","World"};
        List<String> collect = Stream.of(words).flatMap(word -> Stream.of(word.split(""))).collect(Collectors.toList());

    }


    public static void main(String[] args) {
        foreach();
    }
}
