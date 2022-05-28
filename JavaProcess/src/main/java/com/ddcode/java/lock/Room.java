package com.ddcode.java.lock;

public class Room {

    int value = 0;

    public void increment() {
        synchronized (this) {
            value++;
        }
    }
    public void decrement() {
        synchronized (this) {
            value--;
        }
    }

    public int get() {
        return value;
    }


}
