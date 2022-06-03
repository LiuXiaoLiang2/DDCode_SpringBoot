package com.ddcode.java.multitasking;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 测试整个流程
 */
@Slf4j(topic = "c.main")
public class TestMain {

    public static void main(String[] args) throws InterruptedException {
        //开启三个人等待收信
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        TimeUnit.SECONDS.sleep(1);
        //开始送信, 有几个邮箱就有几个送信人
        for (Integer id : MailBoxes.getIds()) {
            TimeUnit.MILLISECONDS.sleep(100);
            new PostMan(id, "内容" + id).start();
        }
    }
}
