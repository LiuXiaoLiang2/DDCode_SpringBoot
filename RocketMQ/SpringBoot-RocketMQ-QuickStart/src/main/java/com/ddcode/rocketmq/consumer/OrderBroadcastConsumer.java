package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderBroadcastMessage;
import com.ddcode.rocketmq.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = OrderBroadcastMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_broadcast",
        messageModel = MessageModel.BROADCASTING // 设置为广播消费
)
@Slf4j
public class OrderBroadcastConsumer implements RocketMQListener<OrderBroadcastMessage> {

    @Override
    public void onMessage(OrderBroadcastMessage orderMessage) {
        log.info("[OrderBroadcastConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderMessage);
    }
}

