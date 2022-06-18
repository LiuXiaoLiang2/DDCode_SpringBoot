package com.ddcode.java.stampedLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * StampedLock 使用
 */
@Slf4j(topic = "c.StampedLock")
public class DataContainerStamped {

    private int data;

    private final StampedLock lock = new StampedLock();

    /**
     * 使用构造方法, 将数据传进来
     * @param data
     */
    public DataContainerStamped(int data) {
        this.data = data;
    }

    /**
     * 传入的参数,可以修改值
     * @param newData
     * @throws Exception
     */
    public void write(int newData) throws Exception {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            //写锁, 休眠2s
            TimeUnit.SECONDS.sleep(2);
            this.data = newData;
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }

    /**
     * 读锁
     * @param readTime
     * @return
     * @throws Exception
     */
    public int read(int readTime)  throws Exception{
        //首先使用乐观锁去判断
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking...{}", stamp);
        TimeUnit.SECONDS.sleep(readTime);
        if (lock.validate(stamp)) {
            //说明有效
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        }

        // 如果乐观锁,加锁失败,就需要升级锁
        // 锁升级 - 读锁
        log.debug("updating to read lock... {}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            TimeUnit.SECONDS.sleep(readTime);
            log.debug("read finish...{}, data:{}", stamp, data);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }
}
