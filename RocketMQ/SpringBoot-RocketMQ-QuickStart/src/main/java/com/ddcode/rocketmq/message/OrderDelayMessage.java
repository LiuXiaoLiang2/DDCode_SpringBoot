package com.ddcode.rocketmq.message;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 批量发送的消息实体
 */
@Data
@Builder
public class OrderDelayMessage implements Serializable {

    public static final String TOPIC = "Quick_Start_Delay";

    //消息id
    private Integer id;

    //消息内容
    private String msg;
}
