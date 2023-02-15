package com.ddcode.controller;

import com.ddcode.po.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.server.HttpServerResponse;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

@RestController
@Slf4j
@RequestMapping("/flux")
public class FluxController {

    /**
     * 异步响应
     * @return
     */
    @GetMapping("/get1")
    public Flux<String> get1(){
        log.info("flux start...");
        Flux<String> flux = Flux.fromStream(IntStream.range(1, 5).mapToObj(
                i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "flux:" + i;
                }
        ));
        log.info("flux end...");
        return flux;
    }

    /**
     * 改变了响应方式，返回一个响应一个，响应就像流一样，一条一条返回
     * @return
     */
    //@GetMapping(value = "/get2", produces = "text/event-stream")
    @GetMapping(value = "/get2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)//效果一样
    public Flux<String> get2(){
        log.info("flux start...");
        Flux<String> flux = Flux.fromStream(IntStream.range(1, 5).mapToObj(
                i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "flux:" + i;
                }
        ));
        log.info("flux end...");
        return flux;
    }


    @GetMapping(value = "/get3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)//效果一样
    public Flux<User> get3(){
        log.info("flux start...");
        Flux<User> flux = Flux.fromStream(LongStream.range(1, 5).mapToObj(
                i -> {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return User.builder().id(i).userName("张三" + i).pwd("密码" + i).build();
                }
        ));
        log.info("flux end...");
        return flux;
    }
}
