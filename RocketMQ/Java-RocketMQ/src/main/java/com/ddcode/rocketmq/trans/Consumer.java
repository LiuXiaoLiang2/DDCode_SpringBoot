package com.ddcode.rocketmq.trans;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws Exception {

        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tran-group");
        // 指定Nameserver地址信息.
        consumer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        //订阅Topic和tag, 接收所有消息
        consumer.subscribe("base", "*");
        // 注册回调函数，处理消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                //遍历内容
                msgs.forEach(msg->{
                    System.out.printf("%s Receive New Messages: %s %n",
                            Thread.currentThread().getName(), new String(msg.getBody()));
                });
                //响应收到消息成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
