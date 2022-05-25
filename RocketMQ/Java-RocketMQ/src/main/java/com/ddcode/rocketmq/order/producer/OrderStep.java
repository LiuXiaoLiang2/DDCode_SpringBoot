package com.ddcode.rocketmq.order.producer;

import java.util.ArrayList;
import java.util.List;

public class OrderStep {

    private long orderId;
    private String desc;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderStep{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }

    /**
     * 生成模拟订单数据
     */
    public static List<OrderStep> buildOrders() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        // orderId=1L 流程: 创建->付款->推送->完成
        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        // orderId=2L 流程: 创建->付款->完成
        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        // orderId=3L 流程: 创建->完成
        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);


        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);


        return orderList;
    }


    public static List<OrderStep> buildOrders1() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();

        // orderId=1L 流程: 创建->付款->推送->完成
        OrderStep orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("推送");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(1L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);
        return orderList;
    }

    public static List<OrderStep> buildOrders2() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();
        OrderStep orderDemo = new OrderStep();
        // orderId=2L 流程: 创建->付款->完成
        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("付款");
        orderList.add(orderDemo);

        orderDemo = new OrderStep();
        orderDemo.setOrderId(2L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);

        return orderList;
    }

    public static List<OrderStep> buildOrders3() {
        List<OrderStep> orderList = new ArrayList<OrderStep>();
        OrderStep orderDemo = new OrderStep();
        // orderId=3L 流程: 创建->完成
        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("创建");
        orderList.add(orderDemo);


        orderDemo = new OrderStep();
        orderDemo.setOrderId(3L);
        orderDemo.setDesc("完成");
        orderList.add(orderDemo);
        return orderList;
    }
}
