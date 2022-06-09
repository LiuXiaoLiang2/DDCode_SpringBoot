package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderMessage;
import com.ddcode.rocketmq.message.OrderTagMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = OrderTagMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_tag",
        selectorType = SelectorType.TAG,
        selectorExpression = "TAG_1 || TAG_2")
@Slf4j
public class OrderTagConsumer implements RocketMQListener<OrderTagMessage> {

    @Override
    public void onMessage(OrderTagMessage orderMessage) {
        log.info("[OrderTagConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderMessage);
    }
}

