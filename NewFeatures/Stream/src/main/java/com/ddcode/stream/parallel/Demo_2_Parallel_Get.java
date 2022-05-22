package com.ddcode.stream.parallel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class Demo_2_Parallel_Get {

    public static void main(String[] args) {
        //直接获取
        List<String> names = new ArrayList<>();
        Collections.addAll(names, "张无忌", "周芷若", "赵敏", "张强", "张三丰");

        // 直接 获取并行流
        names.parallelStream().forEach((s)->{
            System.out.println(Thread.currentThread().getName() + ", " + s);
        });

        names.stream().parallel().forEach((s)->{
            System.out.println(Thread.currentThread().getName() + ", " + s);
        });

    }
}
