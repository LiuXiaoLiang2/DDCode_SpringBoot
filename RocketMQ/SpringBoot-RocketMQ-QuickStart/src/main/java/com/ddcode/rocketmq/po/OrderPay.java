package com.ddcode.rocketmq.po;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class OrderPay {

    public static final String TOPIC = "Quick_Start_Order";

    private Integer id;
    private Integer orderId;
    private String userName;
    private String desc;

    public static List<OrderPay> create1(){
        List<OrderPay> list =  new ArrayList<OrderPay>();
        OrderPay orderPay1 = OrderPay.builder().id(1).orderId(1).userName("张三").desc("下单").build();
        OrderPay orderPay2 = OrderPay.builder().id(2).orderId(1).userName("张三").desc("付款").build();
        OrderPay orderPay3 = OrderPay.builder().id(3).orderId(1).userName("张三").desc("发货").build();
        OrderPay orderPay4 = OrderPay.builder().id(4).orderId(1).userName("张三").desc("完成").build();
        list.add(orderPay1);
        list.add(orderPay2);
        list.add(orderPay3);
        list.add(orderPay4);
        return list;
    }

    public static List<OrderPay> create2(){
        List<OrderPay> list =  new ArrayList<OrderPay>();
        OrderPay orderPay1 = OrderPay.builder().id(5).orderId(2).userName("李四").desc("下单").build();
        OrderPay orderPay2 = OrderPay.builder().id(6).orderId(2).userName("李四").desc("付款").build();
        OrderPay orderPay3 = OrderPay.builder().id(7).orderId(2).userName("李四").desc("发货").build();
        list.add(orderPay1);
        list.add(orderPay2);
        list.add(orderPay3);
        return list;
    }

    public static List<OrderPay> create3(){
        List<OrderPay> list =  new ArrayList<OrderPay>();
        OrderPay orderPay1 = OrderPay.builder().id(8).orderId(3).userName("王五").desc("下单").build();
        OrderPay orderPay2 = OrderPay.builder().id(9).orderId(3).userName("王五").desc("付款").build();
        list.add(orderPay1);
        list.add(orderPay2);
        return list;
    }
}
