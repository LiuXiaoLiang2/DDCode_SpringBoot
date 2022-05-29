package com.ddcode.java.safe;

public class Account {

    //账户钱的数量
    private int money;

    //使用构造方法初始化
    public Account(int money) {
        this.money = money;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * 转账操作
     * @param target 要转给的目标账户
     * @param money
     */
    public void transfer(Account target , int money){
        synchronized (Account.class){
            if(this.money >= money){
                this.setMoney(this.money - money);
                target.setMoney(target.getMoney() + money);
            }
        }
    }
}
