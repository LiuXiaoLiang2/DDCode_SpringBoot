package com.ddcode.java.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 筷子类, 继承了 ReentrantLock
 */
public class Chopstick extends ReentrantLock {
    String name;
    public Chopstick(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        return "筷子{" + name + '}';
    }
}
