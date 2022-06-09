package com.ddcode.rocketmq.producer;

import com.ddcode.rocketmq.message.OrderSqlMessage;
import com.ddcode.rocketmq.message.OrderTagMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OrderSqlProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 发送同步消息
     */
    public void sendSyncSql(){

        OrderSqlMessage orderMessage = OrderSqlMessage.builder().id(1).msg("sql过滤").build();

        // 同步发送
        // 参数1 : 主题topic名称
        // 参数2 : 发送的消息实体
        // 参数3 : 是header, 用了sql过滤
        Map<String, Object> headers = new HashMap<>() ;
        headers.put("type", "pay") ;
        headers.put("a", 10) ;
        rocketMQTemplate.convertAndSend(OrderSqlMessage.TOPIC, orderMessage, headers);


        OrderSqlMessage orderMessage2 = OrderSqlMessage.builder().id(1).msg("sql过滤2").build();
        Map<String, Object> headers2 = new HashMap<>() ;
        headers2.put("type", "user") ;
        headers2.put("a", 5) ;
        rocketMQTemplate.convertAndSend(OrderSqlMessage.TOPIC, orderMessage2, headers2);
    }
}
