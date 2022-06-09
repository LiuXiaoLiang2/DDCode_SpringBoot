package com.ddcode.rocketmq.producer;

import com.ddcode.rocketmq.message.OrderTranMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderTransProducer {

    public final static String TRAN_GROUP = "Quick_Start_Trans_Group";

    @Resource
    private RocketMQTemplate rocketMQTemplate;


    public void sendTrans(OrderTranMessage orderTranMessage){
        //转化消息, rocketmq识别的消息
        Message message = MessageBuilder.withPayload(orderTranMessage).build();
        TransactionSendResult transactionSendResult = rocketMQTemplate.sendMessageInTransaction(TRAN_GROUP, orderTranMessage.TOPIC, message, orderTranMessage.getId());
        log.info("发送事务消息同步返回 {}", transactionSendResult);
    }

}

/**
 * 创建事务回查监听器
 */
// 添加要监听的事务组
@RocketMQTransactionListener(txProducerGroup = OrderTransProducer.TRAN_GROUP)
@Slf4j
class TransactionListenerImpl implements RocketMQLocalTransactionListener {

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object args) {
        log.info("执行本地事务 message {}, args {}", message, args);
        Long orderId = Long.parseLong(args.toString());
        if(orderId == 1){
            // 本地事务提交, 这样消费者就能收到消息
            return RocketMQLocalTransactionState.COMMIT;
        }
        if(orderId == 2){
            // 回滚消息, 这样rocketmq就会删除半消息, 这样消费者不会收到消息
            return RocketMQLocalTransactionState.ROLLBACK;
        }
        if(orderId == 3){
            // 回复UNKNOW, 这时候rocketq会调用下面的回查方法
            return RocketMQLocalTransactionState.UNKNOWN;
        }
        return null;
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        log.info("执行回查消息 message {}, =", message);
        //在回查方法中提交
        return RocketMQLocalTransactionState.COMMIT;
    }
}
