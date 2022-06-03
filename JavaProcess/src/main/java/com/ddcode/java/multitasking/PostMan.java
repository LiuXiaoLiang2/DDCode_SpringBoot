package com.ddcode.java.multitasking;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建快递员
 */
@Slf4j(topic = "c.postman")
public class PostMan extends Thread{

    private int id;
    private String mail;

    public PostMan(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @Override
    public void run() {
        GuardedObject guardedObject = MailBoxes.getGuardedObject(id);
        log.debug("送信 id:{}, 内容:{}", id, mail);
        guardedObject.complete(mail);
    }
}
