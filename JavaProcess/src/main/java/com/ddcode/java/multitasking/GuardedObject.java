package com.ddcode.java.multitasking;

import lombok.extern.slf4j.Slf4j;

/**
 * 多任务
 */
@Slf4j(topic = "c.GuardedObject")
public class GuardedObject {

    //每个小格子的编号id
    private int id;

    // 每个小格子的内容, 也就是邮件内容
    private Object response;

    public GuardedObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

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
                // 4) 假设 millis 是 1000，结果在 400 时唤醒了，那么还有 600 要等
                long waitTime = millis - timePassed;
                if (waitTime <= 0) {
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
            response = obj;
            //唤醒等待线程
            lock.notifyAll();
        }
    }
}
