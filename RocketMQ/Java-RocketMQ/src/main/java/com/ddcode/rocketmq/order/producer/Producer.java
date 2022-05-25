package com.ddcode.rocketmq.order.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("shunxu_group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        producer.setSendMsgTimeout(60000);
        //3.启动producer
        producer.start();
        List<OrderStep> orderStepList = new OrderStep().buildOrders();
        for (int i = 0; i < orderStepList.size(); i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            String topic = "shunxuTopic";
            String tag = "shunxuTag";
            String key = "shunxuKey:" + i;
            String body = "orderId : " + orderStepList.get(i).getOrderId() + ",desc: " + orderStepList.get(i).getDesc();
            Message msg = new Message(topic, tag, key , body.getBytes());
            //5.发送顺序消息
            //第一个参数:消息内容
            //第二个参数:消息队列的选择器,可以指定是哪个队列
            //第三个参数:选择队列的业务标志(订单ID) , 注意这个参数，最终是在 队列选择器中的 arg 使用
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    //这样可以很好的保证了每个orderId都是使用的一个队列
                    Long orderId = (Long) arg;
                    Long index = (mqs.size()) % orderId;
                    return mqs.get(index.intValue());
                }
            }, orderStepList.get(i).getOrderId());
            System.out.println(String.format("SendResult status:%s, queueId:%d, body:%s",
                    sendResult.getSendStatus(),
                    sendResult.getMessageQueue().getQueueId(),
                    body));
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        producer.shutdown();
    }
}
