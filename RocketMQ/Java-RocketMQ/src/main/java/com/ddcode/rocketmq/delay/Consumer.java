package com.ddcode.rocketmq.delay;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Consumer {

    public static void main(String[] args) throws Exception {

        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group2");
        // 指定Nameserver地址信息.
        consumer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        //订阅Topic和tag
        consumer.subscribe("springboot-mq", "Tag1");
        //广播模式消费
        consumer.setMessageModel(MessageModel.BROADCASTING);
        // 注册回调函数，处理消息
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt message : msgs) {
                    // Print approximate delay time period
                    // getStoreTimestamp 消息的存储时间
                    //System.out.println("Receive message[msgId=" + message.getMsgId() + "] " + (System.currentTimeMillis() - message.getStoreTimestamp()) + "ms later");
                    System.out.printf("接受消息%s%n", sd.format(new Date())+" == "+message);
                }
                //返回提接受成功, 否则会一直重试
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
