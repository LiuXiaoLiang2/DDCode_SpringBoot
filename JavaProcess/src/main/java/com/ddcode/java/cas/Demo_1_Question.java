package com.ddcode.java.cas;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * cas问题的引出
 */
public class Demo_1_Question {

    public static void main(String[] args) {
//        Account.demo(new AccountUnsafe(10000));
//        Account.demo(new AccountSafe(10000));
//        Account.demo(new AccountSafeNoLock(10000));
        AccountBigDecimal.demo(new AccountSafeNoLockBigDecimal(new BigDecimal(10000.0)));
    }
}

/**
 * 账户接口
 */
interface Account {
    // 获取余额
    Integer getBalance();

    // 取款
    void withdraw(Integer amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(Account account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(10);
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}

/**
 * 账户-线程不安全
 */
class AccountUnsafe implements Account {

    private Integer balance;

    /**
     * 构造方法将参数传递进来
     * @param balance
     */
    public AccountUnsafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    /**
     * 体现方法
     * @param amount
     */
    @Override
    public void withdraw(Integer amount) {
        balance -= amount;
    }
}


/**
 * 账户-线程安全, 使用锁的方式
 */
class AccountSafe implements Account {

    private Integer balance;

    /**
     * 构造方法将参数传递进来
     * @param balance
     */
    public AccountSafe(Integer balance) {
        this.balance = balance;
    }

    @Override
    public Integer getBalance() {
        return balance;
    }

    /**
     * 体现方法
     * @param amount
     */
    @Override
    public synchronized void withdraw(Integer amount) {
        balance -= amount;
    }
}


/**
 * 账户-线程安全, 使用锁的方式
 */
class AccountSafeNoLock implements Account {

    private AtomicInteger balance;

    /**
     * 构造方法将参数传递进来
     * @param balance
     */
    public AccountSafeNoLock(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.intValue();
    }

    /**
     * 体现方法
     * @param amount
     */
    @Override
    public synchronized void withdraw(Integer amount) {

        balance.updateAndGet(m -> m-amount);
    }

}

interface AccountBigDecimal {
    // 获取余额
    BigDecimal getBalance();

    // 取款
    void withdraw(BigDecimal amount);

    /**
     * 方法内会启动 1000 个线程，每个线程做 -10 元 的操作
     * 如果初始余额为 10000 那么正确的结果应当是 0
     */
    static void demo(AccountBigDecimal account) {
        List<Thread> ts = new ArrayList<>();
        long start = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            ts.add(new Thread(() -> {
                account.withdraw(new BigDecimal(10));
            }));
        }
        ts.forEach(Thread::start);
        ts.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        long end = System.nanoTime();
        System.out.println(account.getBalance()
                + " cost: " + (end-start)/1000_000 + " ms");
    }
}

/**
 * 账户-线程安全, 使用锁的方式
 */
class AccountSafeNoLockBigDecimal implements AccountBigDecimal {

    private AtomicReference<BigDecimal> balance;

    /**
     * 构造方法将参数传递进来
     * @param balance
     */
    public AccountSafeNoLockBigDecimal(BigDecimal balance) {
        this.balance = new AtomicReference(balance);
    }

    @Override
    public BigDecimal getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(BigDecimal amount) {
//        while (true) {
//            BigDecimal current = balance.get();
//            BigDecimal next = current.subtract(amount);
//            boolean compareAndSet = balance.compareAndSet(current, next);
//            if(compareAndSet){
//                break;
//            }
//        }
       BigDecimal bigDecimal = balance.updateAndGet(m -> m.subtract(amount));
    }

}