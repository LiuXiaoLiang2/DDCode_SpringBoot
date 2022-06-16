package com.ddcode.java.timer;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * 使用timerTask
 */
@Slf4j(topic = "c.timerTask")
public class Demo_1_TimerTask {

    public static void main(String[] args) {
        useTimerTask();
    }

    public static void useTimerTask(){
        Timer timer = new Timer();

        TimerTask timerTask1 = new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("task1, 开始执行");
                    TimeUnit.SECONDS.sleep(1);
                    log.info("task1, 执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };


        TimerTask timerTask2 = new TimerTask() {
            @Override
            public void run() {
                try {
                    log.info("task2, 开始执行");
                    TimeUnit.SECONDS.sleep(2);
                    log.info("task2, 执行完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        log.info("主线程, 1s 后执行两个定时");
        timer.schedule(timerTask1, 1000);
        timer.schedule(timerTask2, 1000);
    }
}
