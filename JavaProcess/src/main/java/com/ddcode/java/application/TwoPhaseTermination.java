package com.ddcode.java.application;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 实现两阶段终止模式
 */
@Slf4j(topic = "c.monitor")
public class TwoPhaseTermination {

    private Thread monitor;

    public void start(){
        monitor = new Thread(){
            @Override
            public void run() {
                while (true){
                    log.info("监控线程 : 执行监控任务...");

                    //获取当前正在执行的线程
                    Thread currentThread = Thread.currentThread();
                    if(currentThread.isInterrupted()){
                        log.info("监控线程 : 正在关闭...");
                        log.info("监控线程 : 处理后事...");
                        break;
                    }
                    try {
                        //模拟监控任务, 每2s执行一次
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        //被打断后, 状态被清除了, 需要手动设置状态
                        //这样下回在判断 currentThread.isInterrupted() 就能终止线程
                        currentThread.interrupt();
                        e.printStackTrace();
                    }
                }
                log.info("监控线程 : 结束...");
            }
        };

        monitor.start();
    }


    public void stop(){
        if(monitor != null){
            //终止线程
            monitor.interrupt();
        }
    }
}
