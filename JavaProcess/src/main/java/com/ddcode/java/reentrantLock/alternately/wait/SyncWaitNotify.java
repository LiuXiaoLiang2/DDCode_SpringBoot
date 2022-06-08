package com.ddcode.java.reentrantLock.alternately.wait;

/**
 * 定义
 */
public class SyncWaitNotify {

    // 当前执行的标志
    private int flag;
    // 循环的次数
    private int loopNumber;

    /**
     * 使用构造方法传递参数
     * @param flag
     * @param loopNumber
     */
    public SyncWaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /**
     * 第一次循环可以打印, 第二次循环被挂起
     * @param waitFlag
     * @param nextFlag
     * @param str
     */
    public void print(int waitFlag, int nextFlag, String str) {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (this.flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}
