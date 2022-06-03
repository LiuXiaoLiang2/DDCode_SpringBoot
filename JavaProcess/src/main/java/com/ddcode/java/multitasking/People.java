package com.ddcode.java.multitasking;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建收件人
 */
@Slf4j(topic = "c.people")
public class People extends Thread{

    @Override
    public void run() {
        // 收信
        GuardedObject guardedObject = MailBoxes.createGuardedObject();
        log.debug("开始收信 id:{}", guardedObject.getId());
        Object mail = guardedObject.get(5000L);
        log.debug("收到信 id:{}, 内容:{}", guardedObject.getId(), mail);
    }
}
