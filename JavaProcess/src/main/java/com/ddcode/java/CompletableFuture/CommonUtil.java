package com.ddcode.java.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.common")
public class CommonUtil {

    //休眠
    public static void sleep(Integer second){
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
