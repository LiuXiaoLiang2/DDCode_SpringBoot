package com.ddcode.flow;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Flow.*;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

/**
 * 带处理器的Processor
 * 处理器的作用：过滤和加工
 */
@Slf4j(topic = "c.Demo_3_Flow_Processor")
public class Demo_3_Flow_Processor {

    public static void main(String[] args) throws InterruptedException {
        // 1. 定义发布者, 发布的数据类型是 Integer
        // 直接使用jdk自带的SubmissionPublisher
        SubmissionPublisher<Integer> publisher = new SubmissionPublisher<Integer>();

        // 2. 定义处理器, 对数据进行过滤, 并转换为String类型
        MyProcessor processor = new MyProcessor();

        // 3. 发布者 和 处理器 建立订阅关系
        publisher.subscribe(processor);

        // 4. 定义最终订阅者, 消费 String 类型数据
        Subscriber<String> subscriber = new Subscriber<String>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription subscription) {
                log.info("消费者: 与处理器建立关系");
                // 保存订阅关系, 需要用它来给发布者响应
                this.subscription = subscription;

                // 请求一个数据
                log.info("消费者: 请求一个数据");
                this.subscription.request(1);
            }

            @Override
            public void onNext(String item) {
                // 接受到一个数据, 处理
                log.info("消费者: 接受到数据 {}", item);
                // 处理完调用request再请求一个数据
                this.subscription.request(1);

                // 或者 已经达到了目标, 调用cancel告诉发布者不再接受数据了
                // this.subscription.cancel();
            }

            @Override
            public void onError(Throwable throwable) {
                // 出现了异常(例如处理数据的时候产生了异常)
                throwable.printStackTrace();

                // 我们可以告诉发布者, 后面不接受数据了
                this.subscription.cancel();
            }

            @Override
            public void onComplete() {
                // 全部数据处理完了(发布者关闭了)
                log.info("消费者: 处理完毕");
            }
        };


        // 5. 处理器 和 最终订阅者 建立订阅关系
        processor.subscribe(subscriber);

        // 6. 生产数据, 并发布
        // 这里忽略数据生产过程
        publisher.submit(-111);
        publisher.submit(111);

        // 7. 结束后 关闭发布者
        // 正式环境 应该放 finally 或者使用 try-resouce 确保关闭
        publisher.close();

        // 主线程延迟停止, 否则数据没有消费就退出
        TimeUnit.SECONDS.sleep(1);
    }
}


/**
 * 自定义处理器
 * Processor, 需要继承SubmissionPublisher并实现Processor接口
 * 输入源数据 integer, 过滤掉小于0的, 然后转换成字符串发布出去
 */
@Slf4j(topic = "c.MyProcessor")
class MyProcessor extends SubmissionPublisher<String> implements Processor<Integer, String> {

    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
        log.info("处理器: 与发布者建立关系");
        // 保存订阅关系, 需要用它来给发布者响应
        this.subscription = subscription;
        // 请求一个数据
        log.info("处理器: 请求一个数据");
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        // 接受到一个数据, 处理
        log.info("处理器: 收到数据 {}", item);

        // 过滤掉小于0的, 然后发布出去
        if (item > 0) {
            log.info("处理器: 收到数据 {}, 满足条件", item);
            String data = "conversion " + item;
            log.info("处理器: 收到数据 {}, 进行转化 {}", item, data);
            this.submit(data);
        } else {
            log.info("处理器: 收到数据 {}, 不满足条件", item);
        }

        // 处理完调用request再请求一个数据
        this.subscription.request(1);

        // 或者 已经达到了目标, 调用cancel告诉发布者不再接受数据了
        // this.subscription.cancel();
    }

    @Override
    public void onError(Throwable throwable) {
        // 出现了异常(例如处理数据的时候产生了异常)
        throwable.printStackTrace();

        // 我们可以告诉发布者, 后面不接受数据了
        this.subscription.cancel();
    }

    @Override
    public void onComplete() {
        // 全部数据处理完了(发布者关闭了)
        log.info("处理器: 处理完毕");
        // 关闭发布者
        this.close();
    }
}