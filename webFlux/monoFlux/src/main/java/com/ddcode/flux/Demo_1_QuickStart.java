package com.ddcode.flux;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.stream.IntStream;

@Slf4j
public class Demo_1_QuickStart {
    public static void main(String[] args) {
        Mono<String> mono_test = Mono.just("mono test");
        mono_test.subscribe(i -> log.info(i));
        log.info("mono 发布消息结束");

        String[] strings = {"1","2","3"};
        Flux<Integer> flux = Flux.fromArray(strings).map(s -> Integer.parseInt(s));

        Subscriber<Integer> subscriber = new Subscriber<Integer>() {

            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                subscription = s;
                log.info("flux 订阅者建立关系");
                s.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                log.info("flux 订阅者收到消息 {}", integer);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                log.info("flux 订阅者收到消息报错， ", t);
            }

            @Override
            public void onComplete() {
                log.info("flux 订阅者结束");
            }
        };

        flux.subscribe(subscriber);
        flux.subscribe(
                i -> log.info("订阅者2 收到消息 {}", i)
        );
        IntStream.range(2, 10).forEach(i-> System.out.println(i));
    }
}
