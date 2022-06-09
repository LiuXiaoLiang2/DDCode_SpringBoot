package com.ddcode.rocketmq.message;

import lombok.Builder;
import lombok.Data;

/**
 * 事务消息
 */
@Data
@Builder
public class OrderSqlMessage {
    public static final String TOPIC = "Quick_Start_Sql";
    //消息id
    private Integer id;
    //消息内容
    private String msg;
}
