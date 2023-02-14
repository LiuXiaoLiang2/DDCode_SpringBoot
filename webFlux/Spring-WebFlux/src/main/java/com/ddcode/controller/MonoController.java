package com.ddcode.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/mono")
@Slf4j
public class MonoController {

    @GetMapping("/get1")
    public String get1(){
        log.info("get1 start...");
        String result = doSomeThing();
        log.info("get1 end...");
        return result;
    }

    @GetMapping("/get2")
    public Mono<String> get2(){
        return Mono.just("do something get2");
    }

    @GetMapping("/get3")
    public Mono<String> get3(){
        log.info("get2 start...");
        //Mono<String> mono = Mono.just(doSomeThing());
        Mono<String> mono = Mono.fromFuture(CompletableFuture.supplyAsync(
                () -> doSomeThing()
        ));
        //Mono<String> mono = Mono.fromSupplier(() -> doSomeThing());
        log.info("get2 end...");
        return mono;
    }

    public String doSomeThing(){
        try {
            log.info("开始执行业务");
            TimeUnit.SECONDS.sleep(5);
            log.info("业务执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "over...";
    }
}
