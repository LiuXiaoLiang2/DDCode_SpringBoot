package com.ddcode.rocketmq.message;

import lombok.Builder;
import lombok.Data;

/**
 * 事务消息
 */
@Data
@Builder
public class OrderTranMessage {
    public static final String TOPIC = "Quick_Start_Trans";
    //消息id
    private Integer id;
    //消息内容
    private String msg;
}
