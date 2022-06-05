package com.ddcode.java.multilock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.bigroom")
class BigRoom {

    private final Object studyRoom = new Object();
    private final Object bedRoom = new Object();

    public void sleep() throws InterruptedException {
        synchronized (bedRoom) {
            log.debug("sleeping 2 小时");
            TimeUnit.SECONDS.sleep(2);
        }
    }
    public void study() throws InterruptedException {
        synchronized (studyRoom) {
            log.debug("study 1 小时");
            TimeUnit.SECONDS.sleep(2);
        }
    }
}