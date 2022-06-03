package com.ddcode.java.asyn;

/**
 * 消息实体
 */
public class Message {

    // 消息id
    private int id;
    // 消息内容
    private Object msg;

    public Message(int id, Object msg) {
        this.id = id;
        this.msg = msg;
    }

    public int getId() {
        return id;
    }

    public Object getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", msg=" + msg +
                '}';
    }
}
