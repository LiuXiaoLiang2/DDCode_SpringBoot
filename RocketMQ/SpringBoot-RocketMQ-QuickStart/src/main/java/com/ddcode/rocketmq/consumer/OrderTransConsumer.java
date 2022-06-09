package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderMessage;
import com.ddcode.rocketmq.message.OrderTranMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = OrderTranMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_trans")
@Slf4j
public class OrderTransConsumer implements RocketMQListener<OrderTranMessage> {

    @Override
    public void onMessage(OrderTranMessage orderMessage) {
        log.info("[OrderTransConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderMessage);
    }
}

