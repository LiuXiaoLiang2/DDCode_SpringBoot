package com.ddcode.java.thread;

import com.ddcode.java.application.TwoPhaseTermination;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * interrupt方法的使用后
 */
@Slf4j(topic = "c.interrupt")
public class Demo_8_interrupt {
    public static void main(String[] args) throws InterruptedException {
//        interrupt_quickStart();
//        interrupt_normal();
        TwoPhaseTermination twoPhaseTermination = new TwoPhaseTermination();
        twoPhaseTermination.start();
        //让监控执行10s
        TimeUnit.SECONDS.sleep(10);
        twoPhaseTermination.stop();

    }
    
    public static void interrupt_quickStart() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {
                log.debug("执行 t1 子线程");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    log.error("t1 子线程被打断");
                    e.printStackTrace();
                }
            }
        };

        t1.start();
        //为了能让子线程先跑起来, 而不是子线程还没执行, 我们就执行打断方法
        TimeUnit.SECONDS.sleep(1);
        //打断t1子线程
        t1.interrupt();
        //查看现在t1线程的打断状态
        log.info("t1 线程的状态 {}", t1.isInterrupted());
    }

    public static void interrupt_normal() throws InterruptedException {
        Thread t1 = new Thread() {
            @Override
            public void run() {

                while (true){
                    log.info("执行t1线程");
                    Thread thread = Thread.currentThread();
                    if(thread.isInterrupted()){
                        log.info("t1线程被打断");
                        //使用break, 终止子线程
                        break;
                    }
                }
            }
        };

        t1.start();
        //为了能让子线程先跑起来, 而不是子线程还没执行, 我们就执行打断方法
        TimeUnit.MILLISECONDS.sleep(20);
        //打断t1子线程
        t1.interrupt();
        //查看现在t1线程的打断状态
        log.info("t1 线程的状态 {}", t1.isInterrupted());
    }


}
