package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = OrderMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group")
@Slf4j
public class OrderConsumer implements RocketMQListener<OrderMessage> {

    @Override
    public void onMessage(OrderMessage orderMessage) {
        log.info("[OrderConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderMessage);
    }
}

