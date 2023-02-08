package com.ddcode.reactive;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * flow的简单使用--多个订阅者
 */
@Slf4j(topic = "c.Demo_2_Flow")
public class Demo_5_Flow_More_Subscription {

    public static void main(String[] args) throws InterruptedException {
        //定义创建发布者，发布的数据类型是Integer
        //直接使用JDK自带的SubmissionPublisher，它实现了Publisher接口
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>();

        //创建消费者
        Subscriber<Integer> subscriber1 = new Subscriber<>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                log.info("订阅者1, 初始化:保存订阅关系同时请求一条数据");
                //保存订阅关系，需要用它来给发布者响应. 也就是将subscription保存起来
                this.subscription = subscription;
                //请求一条数据
                subscription.request(1);
            }

            /**
             * 响应式流的关键再有该方法，我们消费完以后可以再次调用request方法再次获取数据，也就是发布者和订阅者之间的关系是一直在的
             * 同时有一个优点就是可以控制请求速率，如果这里在该方法处理的慢，调用request请求数据的方法也可以慢，可以达到控制速率的目的
             * @param item
             */
            @Override
            public void onNext(Integer item) {
                //接收到一个数据，处理
                log.info("订阅者1, 接收到一个数据 {}", item);

                //处理完再次调用request请求一个数据
                subscription.request(1);

                //或者已经达到了目标,就不再请求，调用cancel方法告诉发布者不再接收数据
                //subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                //如果在onNext方法中处理数据出错后，就会进入该方法
                log.error("订阅者1, 处理数据错误, {}" , throwable.getMessage());
                //直接打印错误消息
                throwable.printStackTrace();
                //这时候也可以告诉发布者，不再接收数据
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                //该方法是在发布者关闭的时候触发，也就是执行publisher.close();
                log.info("订阅者1, 收到了发布者的关闭");
            }
        };

        //创建消费者
        Subscriber<Integer> subscriber2 = new Subscriber<>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                log.info("订阅者2, 初始化:保存订阅关系同时请求一条数据");
                //保存订阅关系，需要用它来给发布者响应. 也就是将subscription保存起来
                this.subscription = subscription;
                //请求一条数据
                subscription.request(1);
            }

            /**
             * 响应式流的关键再有该方法，我们消费完以后可以再次调用request方法再次获取数据，也就是发布者和订阅者之间的关系是一直在的
             * 同时有一个优点就是可以控制请求速率，如果这里在该方法处理的慢，调用request请求数据的方法也可以慢，可以达到控制速率的目的
             * @param item
             */
            @Override
            public void onNext(Integer item) {
                //接收到一个数据，处理
                log.info("订阅者2, 接收到一个数据 {}", item);

                //处理完再次调用request请求一个数据
                subscription.request(1);

                //或者已经达到了目标,就不再请求，调用cancel方法告诉发布者不再接收数据
                //subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                //如果在onNext方法中处理数据出错后，就会进入该方法
                log.error("订阅者2, 处理数据错误, {}" , throwable.getMessage());
                //直接打印错误消息
                throwable.printStackTrace();
                //这时候也可以告诉发布者，不再接收数据
                subscription.cancel();
            }

            @Override
            public void onComplete() {
                //该方法是在发布者关闭的时候触发，也就是执行publisher.close();
                log.info("订阅者2, 收到了发布者的关闭");
            }
        };


        //发布者和订阅者建立关系
        publisher.subscribe(subscriber1);
        publisher.subscribe(subscriber2);
        log.info("发布者, 开始发布消息");

        //生产数据并发布
        Integer data = 111;
        log.info("发布者, 发布消息 {}", data);
        publisher.submit(data);
        TimeUnit.MILLISECONDS.sleep(10);

        data = 123;
        log.info("发布者, 发布消息 {}", data);
        publisher.submit(data);
        TimeUnit.MILLISECONDS.sleep(10);

        data = 456;
        log.info("发布者, 发布消息 {}", data);
        publisher.submit(data);
        TimeUnit.MILLISECONDS.sleep(10);

        //结束后关闭发布者
        //正式环境应该放在finally或者使用 try-resource确保关闭
        log.info("发布者, 关闭");
        publisher.close();

        //主线程延迟停止，否则数据还未消费就退出
        TimeUnit.SECONDS.sleep(1);

    }
}
