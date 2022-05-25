package com.ddcode.rocketmq.trans;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.concurrent.TimeUnit;

/**
 * 发送事务消息
 */
public class Producer {


    public static void main(String[] args) throws Exception {
        //1.创建消息生产者producer，并制定生产者组名
        TransactionMQProducer producer = new TransactionMQProducer("tran-group");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        //设置事务消息的回调对象
        producer.setTransactionListener(new TransactionListenerImpl());
        //设置超时时间
        producer.setSendMsgTimeout(60000);
        //3.启动producer
        producer.start();
        String[] tags = new String[]{"tag1", "tag2", "tag3"};
        for (int i = 0; i < 3; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic, 消息的大类别
             * 参数二：消息Tag : 主题下面可以有可以有多个tag, tag算一个小类别
             * 参数三：消息内容
             */

            Message msg = new Message("base", tags[i], ("Hello World" + i).getBytes());
            //5.发送消息, 会阻塞等待消息的发送
            SendResult result = producer.sendMessageInTransaction(msg, "tran-msg-"+ i);
            //发送状态
            SendStatus status = result.getSendStatus();
            System.out.println("事务发送结果:" + result);
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer, 这里不关闭生产者，否则无法调用回查接口
        //producer.shutdown();
    }
}
