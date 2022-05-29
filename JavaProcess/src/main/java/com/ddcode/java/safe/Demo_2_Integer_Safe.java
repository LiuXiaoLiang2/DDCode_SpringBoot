package com.ddcode.java.safe;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j(topic = "c.integer")
public class Demo_2_Integer_Safe {

    private static Integer i = 0;

    public static void main(String[] args) {
        List<Thread> list = new ArrayList<>();
        for (int j = 0; j < 2; j++) {
            Thread thread = new Thread(() -> {
                for (int k = 0; k < 5000; k++) {
                    synchronized (Demo_2_Integer_Safe.class) {
                        i++;
                    }
                }
            }, "" + j);
            list.add(thread);
        }
        list.stream().forEach(t -> t.start());
        list.stream().forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("i {}" , i);
    }
}
