package com.ddcode.java.safe;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j(topic = "c.account")
public class Demo_4_Account {

    public static void main(String[] args) {

        Account account1 = new Account(1000);
        Account account2 = new Account(1000);


        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for(int i=0; i<1000; i++){
                    account1.transfer(account2, randomAmount());
                }
            }
        };
        Thread thread2= new Thread() {
            @Override
            public void run() {
                for(int i=0; i<1000; i++){
                    account2.transfer(account1, randomAmount());
                }
            }
        };

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();

            log.info("account1 剩余 {}" , account1.getMoney());
            log.info("account2 剩余 {}" , account2.getMoney());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    // Random 为线程安全
    static Random random = new Random();
    // 随机 1~5
    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }
}
