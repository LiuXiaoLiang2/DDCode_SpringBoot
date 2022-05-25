package com.ddcode.rocketmq.order.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class Consumer {

    public static void main(String[] args) throws Exception {

        //创建消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("shunxu_group1");
        // 指定Nameserver地址信息.
        consumer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        //订阅Topic和tag
        String topic = "shunxuTopic";
        String tag = "shunxuTag";
        consumer.subscribe(topic, tag);
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 注册回调函数，处理消息
        //MessageListenerOrderly 顺序消息徐需要用这个监听器
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println("线程名称：【" + Thread.currentThread().getName() + "】:" + new String(msg.getBody()) +
                            ", getQueueId : "  + msg.getQueueId());
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("消费者启动");
    }
}
