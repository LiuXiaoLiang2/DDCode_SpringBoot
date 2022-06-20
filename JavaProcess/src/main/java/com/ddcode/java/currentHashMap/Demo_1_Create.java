package com.ddcode.java.currentHashMap;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j(topic = "c.currentHashMap")
public class Demo_1_Create {

    static final String ALPHA = "abcedfghijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        createData();

        demo(
            // 创建 map 集合
            // 创建 ConcurrentHashMap 对不对？
                () -> new ConcurrentHashMap<String,LongAdder>(),
            // 进行计数
                (map, words) -> {
                    for (String word : words) {
                        LongAdder longAdder = map.computeIfAbsent(word, (key) -> new LongAdder());
                        longAdder.increment();
                    }
                }
        );
    }


    public static void createData(){
        int length = ALPHA.length();
        int count = 200;
        List<String> list = new ArrayList<>(length * count);
        for (int i = 0; i < length; i++) {
            char ch = ALPHA.charAt(i);
            for (int j = 0; j < count; j++) {
                list.add(String.valueOf(ch));
            }
        }
        Collections.shuffle(list);

        for (int i = 0; i < 26; i++) {
            try (
                    PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(Demo_1_Create.class.getClassLoader().getResource("tmp").getPath() + "/" + (i+1) + ".txt")))
            ) {
                String collect = list.subList(i * count, (i + 1) * count).stream()
                        .collect(Collectors.joining("\n"));
                out.print(collect);
            } catch (IOException e) {
                log.error("e,", e);
            }

        }
    }


    public static List<String> readFromFile(int i) {
        ArrayList<String> words = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(Demo_1_Create.class.getClassLoader().getResource("tmp").getPath() + "/" + (i+1) + ".txt")))) {
            while(true) {
                String word = in.readLine();
                if(word == null) {
                    break;
                }
                words.add(word);
            }
            return words;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封装成模块地方
     * @param supplier
     * @param consumer
     * @param <V>
     */
    private static <V> void demo(Supplier<Map<String,V>> supplier,
                                 BiConsumer<Map<String,V>,List<String>> consumer) {
        Map<String, V> counterMap = supplier.get();
        List<Thread> ts = new ArrayList<>();
        for (int i = 1; i <= 26; i++) {
            int idx = i;
            Thread thread = new Thread(() -> {
                List<String> words = readFromFile(idx -1);
                //将每个读到的集合都执行这个方法
                consumer.accept(counterMap, words);
            });
            ts.add(thread);
        }
        //开启线程
        ts.forEach(t->t.start());
        ts.forEach((t)->{
            try {
                //等待每个线程执行完毕
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(counterMap);
    }
}
