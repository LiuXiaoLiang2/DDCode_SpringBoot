package com.ddcode.rocketmq.controller;

import com.ddcode.rocketmq.message.*;
import com.ddcode.rocketmq.po.OrderPay;
import com.ddcode.rocketmq.producer.OrderProducer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/rocketmq")
public class OrderController {

    @Resource
    private OrderProducer orderProducer;

    @RequestMapping("/sendSync")
    public String sendSync(Integer id){
        OrderMessage message = OrderMessage.builder().id(id).msg("苹果").build();
        orderProducer.sendSync(message);
        return "ok";
    }

    @RequestMapping("/sendAsync")
    public String sendAsync(Integer id){
        OrderMessage message = OrderMessage.builder().id(id).msg("苹果").build();
        orderProducer.sendAsync(message);
        return "ok";
    }

    @RequestMapping("/sendOneWay")
    public String sendOneWay(Integer id){
        OrderMessage message = OrderMessage.builder().id(id).msg("苹果").build();
        orderProducer.sendOneWay(message);
        return "ok";
    }


    @RequestMapping("/sendSyncBatch")
    public String sendSyncBatch(){
        OrderBatchMessage message1 = OrderBatchMessage.builder().id(1).msg("苹果1").build();
        OrderBatchMessage message2 = OrderBatchMessage.builder().id(2).msg("苹果2").build();
        OrderBatchMessage message3 = OrderBatchMessage.builder().id(3).msg("苹果3").build();

        List list =  new ArrayList<OrderBatchMessage>();
        list.add(message1);
        list.add(message2);
        list.add(message3);
        orderProducer.sendBatch(list);
        return "ok";
    }

    @RequestMapping("/sendSyncDelay")
    public String sendSyncDelay(Integer id){
        OrderDelayMessage message = OrderDelayMessage.builder().id(id).msg("苹果").build();
        orderProducer.sendSyncDelay(message);
        return "ok";
    }

    @RequestMapping("/sendSyncRetry")
    public String sendSyncRetry(Integer id){
        OrderRetryMessage message = OrderRetryMessage.builder().id(id).msg("苹果").build();
        orderProducer.sendSyncRetry(message);
        return "ok";
    }

    @RequestMapping("/sendSyncBroadcast")
    public String sendSyncBroadcast(Integer id){
        OrderBroadcastMessage message = OrderBroadcastMessage.builder().id(id).msg("苹果").build();
        orderProducer.sendSyncBroadcast(message);
        return "ok";
    }


    @RequestMapping("/sendSyncOrder")
    public String sendSyncOrder(){

        List<OrderPay> orderPays1 = OrderPay.create1();
        List<OrderPay> orderPays2 = OrderPay.create2();
        List<OrderPay> orderPays3 = OrderPay.create3();

        new Thread("t1"){
            @Override
            public void run() {
                for (OrderPay orderPay : orderPays1) {
                    orderProducer.sendSyncOrder(orderPay);
                }
            }
        }.start();

        new Thread("t1"){
            @Override
            public void run() {
                for (OrderPay orderPay : orderPays2) {
                    orderProducer.sendSyncOrder(orderPay);
                }
            }
        }.start();

        new Thread("t1"){
            @Override
            public void run() {
                for (OrderPay orderPay : orderPays3) {
                    orderProducer.sendSyncOrder(orderPay);
                }
            }
        }.start();

        return "ok";
    }

}
