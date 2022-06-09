package com.ddcode.rocketmq.message;

import lombok.Builder;
import lombok.Data;

/**
 * 事务消息
 */
@Data
@Builder
public class OrderTagMessage {
    public static final String TOPIC = "Quick_Start_Tag";
    public static final String TAG1 = "TAG_1";
    public static final String TAG2 = "TAG_2";
    //消息id
    private Integer id;
    //消息内容
    private String msg;
}
