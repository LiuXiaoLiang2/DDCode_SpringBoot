package com.ddcode.rocketmq.message;

import lombok.Builder;
import lombok.Data;

/**
 * 发送的消息实体
 */
@Data
@Builder
public class OrderMessage {

    public static final String TOPIC = "Quick_Start";

    //消息id
    private Integer id;

    //消息内容
    private String msg;
}
