package com.ddcode.java.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j(topic = "c.AtomicMarkableReference")
public class Demo_4_AtomicMarkableReference {

    //定义一个原子引用类
    //定义初始标志是 true
    static AtomicMarkableReference<String> ref = new AtomicMarkableReference<>("A", true);

    public static void main(String[] args) throws InterruptedException {
        log.info("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.getReference();
        other();
        TimeUnit.SECONDS.sleep(1);
        // 尝试改为 C
        // 第三个参数 : 希望ref的标志是true, 如果是true, 才能被修改
        // 第四个参数 : 如果满足了第三个参数后, 更改ref的标志为 false
        log.info("change A->C {}", ref.compareAndSet(prev, "C", true, false));
    }

    private static void other() throws InterruptedException {
        new Thread(() -> {
            //该线程先执行, 所以会将ref的标志更改为false
            log.info("change A->B {}", ref.compareAndSet(ref.getReference(), "A", true, false));
        }, "t1").start();
    }
}
