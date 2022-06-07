package com.ddcode.rocketmq.consumer;

import com.ddcode.rocketmq.message.OrderBatchMessage;
import com.ddcode.rocketmq.message.OrderRetryMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 批量发送消息的消费者
 */
@Component
@RocketMQMessageListener(
        topic = OrderRetryMessage.TOPIC,
        consumerGroup = "quick_start_consumer_group_retry"
)
@Slf4j
public class OrderRetryConsumer implements RocketMQListener<OrderRetryMessage> {
    @Override
    public void onMessage(OrderRetryMessage orderRetryMessage) {
        log.info("[orderRetryMessage], [onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), orderRetryMessage);
        //throw new RuntimeException("主动抛出异常");
    }
}
