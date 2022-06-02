package com.ddcode.java.wait;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 同步模式
 */
@Slf4j(topic = "c.guarded")
public class Demo_4_Guarded {

    //线程之间通信的对象
    private Object response;
    // 锁对象
    private final Object lock = new Object();

    /**
     * 获取对象
     * @return
     */
    public Object get(){
        synchronized (lock){
            while (response == null){
                try {
                    log.info("获取response...wait");
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        Demo_4_Guarded guardedObject = new Demo_4_Guarded();
        new Thread(() -> {
            try {
                // 子线程执行下载
                List<String> response = new ArrayList<>();
                response.add("哈哈哈");
                log.debug("set complete...");
                guardedObject.complete(response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        log.debug("waiting...");
        // 主线程阻塞等待
        Object response = guardedObject.get();
        log.debug("get response: [{}]", response);
    }
}
