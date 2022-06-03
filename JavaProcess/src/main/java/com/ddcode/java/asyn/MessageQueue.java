package com.ddcode.java.asyn;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * 消息队列
 */
@Slf4j(topic = "c.queue")
public class MessageQueue {


    //存储消息的双向链表
    static LinkedList<Message> queue = new LinkedList<>();

    //最多能存储的消息个数
    private static int capacity = 2;


    //集合中放消息
    public static void put(Message msg){
        synchronized(MessageQueue.class){
            //说明集合已满
            if(queue.size() >= capacity){
                try {
                    //则挂起线程
                    log.debug("库存已达上限, wait");
                    MessageQueue.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //双向链表, 放在最后
            queue.addLast(msg);
            log.info("生产者存放消息 {}", msg);
            //唤醒线程, 唤醒消费者的线程
            MessageQueue.class.notifyAll();
        }

    }

    //从集合中取消息
    public static Message get(){
        synchronized (MessageQueue.class){
            //没有消息就一直循环
            while (queue.isEmpty()){
                log.debug("没货了, wait");
                try {
                    MessageQueue.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            log.info("消费者获取消息 {}" , message);
            //唤醒正在等待线程,有可能生产者在等待放数据
            MessageQueue.class.notifyAll();
            return message;
        }
    }
}
