package com.ddcode.java.cas;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@Slf4j(topic = "c.AtomicIntegerFieldUpdater")
public class Demo_6_AtomicIntegerFieldUpdater {

    //原子性操作字段
    private volatile int age = 10;

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        //获取操作的原子字段对象
        AtomicIntegerFieldUpdater<Demo_6_AtomicIntegerFieldUpdater> age = AtomicIntegerFieldUpdater.newUpdater(Demo_6_AtomicIntegerFieldUpdater.class, "age");

        Demo_6_AtomicIntegerFieldUpdater demo_6_atomicIntegerFieldUpdater = new Demo_6_AtomicIntegerFieldUpdater();
        //如果当前age的结果是10, 就更改成 20
        boolean b = age.compareAndSet(demo_6_atomicIntegerFieldUpdater, 10, 20);

        log.info("更改结果 {} , 最终结果 {}", b, demo_6_atomicIntegerFieldUpdater.getAge());
    }
}
