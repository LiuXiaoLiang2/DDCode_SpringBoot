package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderBatchMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 批量发送消息的消费者
 */
@Component
@RocketMQMessageListener(
        topic = OrderBatchMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_batch"
)
@Slf4j
public class OrderBatchConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String orderBatchMessage) {
        log.info("[OrderBatchConsumer], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderBatchMessage);
    }
}
