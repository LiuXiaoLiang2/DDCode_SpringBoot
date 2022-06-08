package com.ddcode.java.volatile_;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 使用 volatile 实现两阶段终止
 */
@Slf4j(topic = "c.stop")
public class Demo_2_Stop {

    public static void main(String[] args) throws InterruptedException {
        TPTVolatile tptVolatile = new TPTVolatile();
        tptVolatile.start();
        tptVolatile.start();
        tptVolatile.start();
        TimeUnit.SECONDS.sleep(5);
        tptVolatile.stop();
    }

}

@Slf4j(topic = "c.TPTVolatile")
class TPTVolatile {

    private Thread thread;
    //定义终止标志
    private volatile boolean stop = false;

    // 用来表示是否已经有线程已经在执行启动了
    private volatile boolean starting = false;

    /**
     * 开启任务
     */
    public void start(){
        log.info("线程尝试启动");
        synchronized (this) {
            if(starting){
                log.info("线程已经启动了");
                return;
            }
            starting = true;
        }
        log.info("线程启动成功");
        thread = new Thread("t1"){
            @Override
            public void run() {
                while (true){
                    if(stop){
                        log.info("料理后事");
                        break;
                    }
                    try {
                        log.info("执行监控任务");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        log.info("监控线程被打断");
                        e.printStackTrace();
                    }
                }
            }
        };
        //启动线程
        thread.start();
    }


    public void stop(){
        stop = true;
        //立马终止线程
        thread.interrupt();
    }
}
