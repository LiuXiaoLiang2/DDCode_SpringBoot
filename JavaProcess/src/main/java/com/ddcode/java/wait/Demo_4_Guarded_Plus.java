package com.ddcode.java.wait;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 同步模式
 */
@Slf4j(topic = "c.guarded.plus")
public class Demo_4_Guarded_Plus {

    //线程之间通信的对象
    private Object response;
    // 锁对象
    private final Object lock = new Object();

    /**
     * 获取对象 设置等待时间
     * @return
     */
    public Object get(Long millis){

        synchronized (lock){
            // 1) 记录最初时间
            long begin = System.currentTimeMillis();
            // 2) 已经经历的时间
            long timePassed = 0;

            while (response == null){
                log.info("获取response...wait");
                // 4) 假设 millis 是 1000，结果在 400 时唤醒了，那么还有 600 要等
                long waitTime = millis - timePassed;
                log.debug("waitTime: {}", waitTime);
                if (waitTime <= 0) {
                    log.debug("break...");
                    break;
                }
                try {
                    //这里需要等待的时间是算出来的, 并不是一个固定值,是每次唤醒后, 剩下需要等待的时间
                    lock.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 3) 如果提前被唤醒，这时已经经历的时间假设为 400
                timePassed = System.currentTimeMillis() - begin;
            }
        }
        return response;
    }

    /**
     * 设置对象
     * @param obj
     */
    public void complete(Object obj){
        synchronized (lock){
            log.info("设置response...");
            response = obj;
            //唤醒等待线程
            lock.notifyAll();
        }
    }


    public static void main(String[] args) {
        Demo_4_Guarded_Plus guardedObject = new Demo_4_Guarded_Plus();
        new Thread(() -> {
            try {
                // 子线程执行下载
                log.info("set start...");
                List<String> response = new ArrayList<>();
                response.add("哈哈哈");
                TimeUnit.SECONDS.sleep(2);
                guardedObject.complete(response);
                log.info("set complete...");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        log.debug("waiting...");
        // 主线程阻塞等待
        Object response = guardedObject.get(1000L);
        log.debug("get response: [{}]", response);
    }
}
