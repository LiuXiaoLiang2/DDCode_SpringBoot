package com.ddcode.java.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

@Slf4j(topic = "c.AtomicStampedReference")
public class Demo_3_AtomicStampedReference {

    //定义一个原子引用类
    static AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        log.info("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.get();
        other();
        TimeUnit.SECONDS.sleep(3);
        // 尝试改为 C
        log.info("change A->C {}", ref.compareAndSet(prev, "C"));
    }

    private static void other() throws InterruptedException {
        new Thread(() -> {
            log.info("change A->B {}", ref.compareAndSet(ref.get(), "B"));
        }, "t1").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            log.info("change B->A {}", ref.compareAndSet(ref.get(), "A"));
        }, "t2").start();
    }
}


@Slf4j(topic = "c.AtomicStampedReference_1")
class Demo_3_AtomicStampedReference_1 {

    //定义一个原子引用类, 并定义初始版本是0
    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) throws InterruptedException {
        log.info("main start...");
        // 获取值 A
        // 这个共享变量被它线程修改过？
        String prev = ref.getReference();
        //获取版本号, 先获取版本
        int stamp = ref.getStamp();
        other();
        TimeUnit.SECONDS.sleep(3);
        // 尝试改为 C
        // 增加两个参数
        // 第三个参数 : 要想执行成功, 需要的哪个版本, 这里的stamp变量, 是我们在执行其他线程就创建好了
        // 第四个参数 : 执行成功后, 更改的版本
        boolean compareAndSet = ref.compareAndSet(prev, "C", stamp, stamp + 1);
        log.info("change A->C {}", compareAndSet);
    }

    private static void other() throws InterruptedException {
        new Thread(() -> {
            //执行的时候, 都获取最新的版本, 所以可以执行成功
            log.info("change A->B {}", ref.compareAndSet(ref.getReference(), "B", ref.getStamp(), ref.getStamp() + 1));
        }, "t1").start();
        TimeUnit.SECONDS.sleep(1);
        new Thread(() -> {
            log.info("change B->A {}", ref.compareAndSet(ref.getReference(), "A", ref.getStamp(), ref.getStamp() + 1));
        }, "t2").start();
    }
}
