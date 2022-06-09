package com.ddcode.rocketmq.producer;

import com.ddcode.rocketmq.message.OrderMessage;
import com.ddcode.rocketmq.message.OrderTagMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderTagProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     */
    public void sendSyncTag(){

        OrderTagMessage orderMessage = OrderTagMessage.builder().id(1).msg("发送到tag1").build();

        // 同步发送
        // 参数1 : 主题topic名称
        // 参数2 : 发送的消息实体
        SendResult sendResult = rocketMQTemplate.syncSend(OrderTagMessage.TOPIC + ":" + OrderTagMessage.TAG1, orderMessage);
        log.info("orderMessage {} 同步发送消息结果 {} ", orderMessage, sendResult);

        OrderTagMessage orderMessage2 = OrderTagMessage.builder().id(1).msg("发送到tag1").build();
        SendResult sendResult2 = rocketMQTemplate.syncSend(OrderTagMessage.TOPIC + ":" + OrderTagMessage.TAG2, orderMessage2);
        log.info("orderMessage {} 同步发送消息结果 {} ", sendResult2, sendResult2);
    }
}
