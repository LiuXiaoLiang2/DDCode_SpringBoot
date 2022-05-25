package com.ddcode.rocketmq.filter.sql;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Producer {

    public static void main(String[] args) throws Exception{
        test1();
        test2();
    }

    /**
     * 生产者的发送标签是Tag1
     * @throws Exception
     */
    public static void test1() throws Exception{
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        producer.setSendMsgTimeout(60000);
        //3.启动producer
        producer.start();
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        for (int i = 0; i < 3; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("springboot-mq", "Tag1", ("Tag1 age:"+(i + "")+",Hello World" + i).getBytes());
            //给msg设置属性
            msg.putUserProperty("age" , i + "");
            //5.发送消息, 会阻塞等待消息的发送
            SendResult result = producer.send(msg);
            //发送状态
            SendStatus status = result.getSendStatus();
            System.out.printf("发送消息%s%n", sd.format(new Date())+" == "+ new String(msg.getBody()));
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        producer.shutdown();
    }

    /**
     * 生产者的发送标签是Tag2
     * @throws Exception
     */
    public static void test2() throws Exception{
        //1.创建消息生产者producer，并制定生产者组名
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        //2.指定Nameserver地址
        producer.setNamesrvAddr("119.3.160.28:9876;119.3.160.28:9877");
        producer.setSendMsgTimeout(60000);
        //3.启动producer
        producer.start();
        SimpleDateFormat sd = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        for (int i = 0; i < 3; i++) {
            //4.创建消息对象，指定主题Topic、Tag和消息体
            /**
             * 参数一：消息主题Topic
             * 参数二：消息Tag
             * 参数三：消息内容
             */
            Message msg = new Message("springboot-mq", "Tag2", ("Tag2 , 没有 age Hello World" + i).getBytes());
            //5.发送消息, 会阻塞等待消息的发送
            SendResult result = producer.send(msg);
            //发送状态
            SendStatus status = result.getSendStatus();
            System.out.printf("发送消息%s%n", sd.format(new Date())+" == "+ new String(msg.getBody()));
            //线程睡1秒
            TimeUnit.SECONDS.sleep(1);
        }

        //6.关闭生产者producer
        producer.shutdown();
    }
}
