package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderSqlMessage;
import com.ddcode.rocketmq.message.OrderTagMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = OrderSqlMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_sql",
        selectorType = SelectorType.SQL92,
        selectorExpression = "type='user' or a <7")
@Slf4j
public class OrderSQLConsumer implements RocketMQListener<OrderSqlMessage> {

    @Override
    public void onMessage(OrderSqlMessage orderMessage) {
        log.info("[OrderTagConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderMessage);
    }
}

