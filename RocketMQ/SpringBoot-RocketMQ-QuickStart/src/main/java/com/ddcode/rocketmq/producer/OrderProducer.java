package com.ddcode.rocketmq.producer;

import com.ddcode.rocketmq.message.*;
import com.ddcode.rocketmq.po.OrderPay;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 生产者
 */
@Service
@Slf4j
public class OrderProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     * @param orderMessage
     */
    public void sendSync(OrderMessage orderMessage){
        // 同步发送
        // 参数1 : 主题topic名称
        // 参数2 : 发送的消息实体
        SendResult sendResult = rocketMQTemplate.syncSend(OrderMessage.TOPIC, orderMessage);
        log.info("orderMessage {} 同步发送消息结果 {} ", orderMessage, sendResult);
    }

    /**
     * 异步发送消息
     * @param orderMessage
     */
    public void sendAsync(OrderMessage orderMessage){
        rocketMQTemplate.asyncSend(OrderMessage.TOPIC, orderMessage, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("orderMessage {} 异步发送成功消息结果 {} ", orderMessage, sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("orderMessage {} 异步发送失败结果 {} ", orderMessage, throwable);
            }
        });
    }


    /**
     * 发送oneWay消息
     * @param orderMessage
     */
    public void sendOneWay(OrderMessage orderMessage){
        rocketMQTemplate.sendOneWay(OrderMessage.TOPIC, orderMessage);
        log.info("orderMessage {} 发送oneWay消息结束 ", orderMessage);

    }

    /**
     * 批量发送消息
     * @param orderBatchMessages
     */
    public void sendBatch(Collection<OrderBatchMessage> orderBatchMessages){
        //创建批量发送消息的集合
        rocketMQTemplate.syncSend(OrderBatchMessage.TOPIC, orderBatchMessages);
    }


    /**
     * 发送同步延时消息
     * @param
     */
    public void sendSyncDelay(OrderDelayMessage orderDelayMessage){
        // 同步发送
        // 参数1 : 主题topic名称
        // 参数2 : 发送的消息实体, 需要转化成rocketmq可以识别的message
        // 参数3 : 同步发送超时设置
        // 参数4 : 延时等级 2, 表示5s后发送
        Message message = MessageBuilder.withPayload(orderDelayMessage).build();
        SendResult sendResult = rocketMQTemplate.syncSend(OrderDelayMessage.TOPIC,  message, 3*1000L, 2);
        log.info("orderMessage {} 发送同步延时消息 {} ", orderDelayMessage, sendResult);
    }

    /**
     * 发送同步消息重试
     * @param orderMessage
     */
    public void sendSyncRetry(OrderRetryMessage orderMessage){
        // 同步发送
        // 参数1 : 主题topic名称
        // 参数2 : 发送的消息实体
        SendResult sendResult = rocketMQTemplate.syncSend(OrderRetryMessage.TOPIC, orderMessage);
        log.info("orderMessage {} 同步发送消息结果 {} ", orderMessage, sendResult);
    }

    /**
     * 发送同步消息广播
     * @param orderMessage
     */
    public void sendSyncBroadcast(OrderBroadcastMessage orderMessage){
        // 同步发送
        // 参数1 : 主题topic名称
        // 参数2 : 发送的消息实体
        SendResult sendResult = rocketMQTemplate.syncSend(OrderBroadcastMessage.TOPIC, orderMessage);
        log.info("orderMessage {} 同步发送消息广播结果 {} ", orderMessage, sendResult);
    }

    /**
     * 发送顺序消息
     * @param orderPay
     */
    public void sendSyncOrder(OrderPay orderPay){

        //发送顺序消息
        //参数1 : 主题
        //参数2 : 发送的消息内容
        //参数3 : 消息队列选择策略, 默认是hash
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(OrderPay.TOPIC, orderPay, orderPay.getOrderId().toString());
       // log.info("orderMessage {} 发送顺序消息结果 {} ", orderPay, sendResult);
    }

}
