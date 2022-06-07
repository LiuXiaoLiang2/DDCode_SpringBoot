package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.po.OrderPay;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 顺序消费
 */
@Component
@RocketMQMessageListener(
        topic = OrderPay.TOPIC,
        consumerGroup = "quick_start_consumer_group_order",
        consumeMode = ConsumeMode.ORDERLY // 设置为顺序消费
)
@Slf4j
public class OrderOrderConsumer implements RocketMQListener<OrderPay> {

    @Override
    public void onMessage(OrderPay orderPay) {
        log.info("[线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderPay.getUserName() + ": " + orderPay.getDesc());
    }
}
