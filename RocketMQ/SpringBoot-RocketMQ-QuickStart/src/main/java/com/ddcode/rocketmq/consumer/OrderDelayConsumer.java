package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderBatchMessage;
import com.ddcode.rocketmq.message.OrderDelayMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 发送延时消息的消费者
 */
@Component
@RocketMQMessageListener(
        topic = OrderDelayMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_delay"
)
@Slf4j
public class OrderDelayConsumer implements RocketMQListener<OrderDelayMessage> {
    @Override
    public void onMessage(OrderDelayMessage orderDelayMessage) {
        log.info("[OrderBatchConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderDelayMessage);

    }
}
