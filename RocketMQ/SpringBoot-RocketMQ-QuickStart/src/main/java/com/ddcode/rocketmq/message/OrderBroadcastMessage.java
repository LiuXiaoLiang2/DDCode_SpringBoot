package com.ddcode.rocketmq.message;

import lombok.Builder;
import lombok.Data;

/**
 * 发送的消息实体【广播】
 */
@Data
@Builder
public class OrderBroadcastMessage {

    public static final String TOPIC = "Quick_Start_Broadcast";

    //消息id
    private Integer id;

    //消息内容
    private String msg;
}
