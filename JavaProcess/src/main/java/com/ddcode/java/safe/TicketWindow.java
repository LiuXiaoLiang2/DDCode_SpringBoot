package com.ddcode.java.safe;

/**
 * 定义卖票窗口
 */
public class TicketWindow {
    //卖票的数量
    private int count;

    //使用构造方法初始化


    public TicketWindow(int count) {
        this.count = count;
    }

    /**
     * 定义卖票方法
     * @param amount 卖票的数量
     * @return 返回卖票的数量
     */
    public synchronized int sell(int amount){
        if(this.count >= amount){
            this.count = this.count - amount;
            return amount;
        } else {
            return 0;
        }
    }

    public int getCount(){
        return count;
    }
}
