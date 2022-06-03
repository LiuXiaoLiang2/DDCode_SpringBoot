package com.ddcode.java.asyn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.test")
public class MainTest {
    public static void main(String[] args) throws InterruptedException {

        for(int i=0 ; i<4; i++){
            //开启4个线程,生产者
            int id = i;
            new Thread("生产者" + i){
                @Override
                public void run() {
                    Message message = new Message(id, "内容1");
                    MessageQueue.put(message);
                }
            }.start();
        }

        TimeUnit.SECONDS.sleep(1);

        new Thread("消费者"){
            @Override
            public void run() {
                while (true){
                    Message message = MessageQueue.get();
                }
            }
        }.start();
    }
}
