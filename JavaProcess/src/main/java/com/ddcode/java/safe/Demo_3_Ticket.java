package com.ddcode.java.safe;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "c.ticket")
public class Demo_3_Ticket {

    public static void main(String[] args) {
        //有1000张票
        TicketWindow ticketWindow = new TicketWindow(1000);

        //存储所以的线程
        List<Thread> threads = new ArrayList<>();
        //存储所有卖的票数
        List<Integer> sells = new Vector<>();


        for(int i=0; i<2000; i++){

            //开启2000个线程抢票
            Thread thread = new Thread() {
                @Override
                public void run() {
                    //每个线程买随机的票
                    try {
                        //休眠10ms
                        TimeUnit.MILLISECONDS.sleep(10);
                        int sell = ticketWindow.sell(randomAmount());
                        sells.add(sell);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            threads.add(thread);
        }

        log.info("开始卖票");

        //主线程阻塞等待每一个卖票子线程执行完毕
        threads.forEach((t1)->{
            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        //卖票的数量求和
        int sum = sells.stream().mapToInt(t -> t).sum();

        //还剩下的票的数量
        int last = ticketWindow.getCount();
        log.info("卖出去的票 {}" , sum);
        log.info("剩下的票 {}", last);

    }


    // Random 为线程安全
    static Random random = new Random();
    // 随机 1~5
    public static int randomAmount() {
        return random.nextInt(5) + 1;
    }
}
