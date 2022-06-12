package com.ddcode.java.threadpool;


import lombok.extern.slf4j.Slf4j;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 */
@Slf4j(topic = "c.main")
public class Demo_1_MyThreadPool {

    public static void main(String[] args) {

        ThreadPool threadPool = new ThreadPool(2, 1, TimeUnit.SECONDS, 10);
        for (int i=0; i<5; i++){
            //提交5个任务
            int j = i+1;
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    log.info("执行业务代码 {}", j);
                }
            }, "任务"+(i+1));
        }

        ThreadPool threadPoolPlus = new ThreadPool(2, 1, TimeUnit.SECONDS, 10, (queue, task)->{
            //自定义出来的方法
            // 1. 死等
            // queue.put(task);
            // 2) 带超时等待
            // queue.put(task, 1500, TimeUnit.MILLISECONDS);
            // 3) 让调用者放弃任务执行
            // log.debug("放弃{}", task);
            // 4) 让调用者抛出异常
            // throw new RuntimeException("任务执行失败 " + task);
            // 5) 让调用者自己执行任务
            task.run();
        });
        for (int i=0; i<5; i++){
            //提交5个任务
            int j = i+1;
            threadPool.executePlus(new Runnable() {
                @Override
                public void run() {
                    log.info("执行业务代码 {}", j);
                }
            }, "任务"+(i+1));
        }

    }

}


/**
 * 创建线程池
 */
@Slf4j(topic = "c.ThreadPool")
class ThreadPool {

    // 任务队列
    private BlockingQueue<Runnable> blockingQueue;

    //线程集合
    private Set<Worker> workers = new HashSet<>();

    //核心线程数, 也就是线程池中最多存放线程的数量
    private int coresSize;

    //获取任务的超时时间
    private long timeout;

    private TimeUnit timeUnit;

    private RejectPolicy rejectPolicy;

    /**
     * 构造
     * @param coresSize
     * @param timeout
     * @param timeUnit
     * @param capacity
     */
    public ThreadPool(int coresSize, long timeout, TimeUnit timeUnit, int capacity) {
        this.coresSize = coresSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = new BlockingQueue<>(capacity);
    }

    /**
     * 使用构造方法, 让生产者将拒绝策略传进来
     * @param coresSize
     * @param timeout
     * @param timeUnit
     * @param capacity
     * @param rejectPolicy
     */
    public ThreadPool(int coresSize, long timeout, TimeUnit timeUnit, int capacity, RejectPolicy<Runnable> rejectPolicy) {
        this.coresSize = coresSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.blockingQueue = new BlockingQueue<>(capacity);
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 提交任务的方法
     * @param task
     */
    public void execute(Runnable task, String name){
        //看线程池中有没有多余的线程执行, 如果有就立马执行, 没有就放到任务队列中
        synchronized (workers) {
            if(workers.size() < coresSize){
                log.info("执行任务 {} , 线程池还没有满, 可以执行执行", name);
                //说明线程池中的数量还没超出coreSize, 可以立马新建一个, 去执行
                //将task传递给worker对象
                Worker worker = new Worker(task, name);
                workers.add(worker);
                //立马执行
                worker.start();
            } else {
                log.info("执行任务 {} , 线程池已经满了, 暂时放在任务队列中", name);
                //说明线程池中的数量已经够多了, 暂时放在任务队列中
                blockingQueue.put(task);
            }
        }
    }

    /**
     * 提交任务的方法升级版
     * @param task
     */
    public void executePlus(Runnable task, String name){
        //看线程池中有没有多余的线程执行, 如果有就立马执行, 没有就放到任务队列中
        synchronized (workers) {
            if(workers.size() < coresSize){
                log.info("执行任务 {} , 线程池还没有满, 可以执行执行", name);
                //说明线程池中的数量还没超出coreSize, 可以立马新建一个, 去执行
                //将task传递给worker对象
                Worker worker = new Worker(task, name);
                workers.add(worker);
                //立马执行
                worker.start();
            } else {
                log.info("执行任务 {} , 线程池已经满了, 暂时放在任务队列中", name);
                //使用策略模式
                blockingQueue.tryPut(rejectPolicy, task);

            }
        }
    }


    /**
     * 创建线程对象, 最终要执行的代码, 内部类
     */
    class Worker extends Thread{
        private Runnable task;
        private String name;
        public Worker(Runnable task, String name) {
            this.task = task;
            this.name = name;
        }
        @Override
        public void run() {
            //最终执行的方法, 不仅用执行刚传进来的, 还要执行队列中的任务
            while (task != null || (task = blockingQueue.take()) != null){
                //说明有任务
                try {
                    log.info("name {} 真正执行业务", name);
                    task.run();
                } catch (Exception e) {

                } finally {
                    //执行完任务, 将task置为null
                    task = null;
                }
            }
            //执行完以后,从线程池对象中移除
            synchronized (workers){
                workers.remove(this);
            }
        }
    }

}



/**
 * 自定义任务队列
 * @param <T>
 */
@Slf4j(topic = "c.BlockingQueue")
class BlockingQueue<T> {

    //任务队列集合
    Deque<T> deque = new ArrayDeque();

    //锁
    ReentrantLock lock = new ReentrantLock();

    //创建生产者门,也就是向队列中放元素需要一个一个放
    Condition fullWaitSet = lock.newCondition();

    //创建消费者门,也就是向队列中放取元素需要一个一个取
    Condition emptyWaitSet = lock.newCondition();

    //队列容量
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * 获取队列中的对象
     * @return
     */
    public T take(){
        //加锁
        lock.lock();
        try {
            // 如果队列是空的, 就一直等待
            while (deque.isEmpty()){
                try {
                    log.info("任务队列是空,获取操作被挂起");
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //从队列中取出第一个元素返回
            log.info("获取操作被唤醒");
            T t = deque.removeFirst();
            //唤醒添加元素的线程
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 向队列中放元素
     * @param t
     */
    public void put(T t){
        //加锁
        lock.lock();
        try {
            //如果队列已经满了, 就一直等待
            while (deque.size() == capacity){
                try {
                    log.info("任务队列已满,添加操作被挂起");
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //向尾部添加元素
            log.info("执行添加操作");
            deque.addLast(t);
            //唤醒获取队列元素的线程
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取队列元素个数
     * @return
     */
    public int size(){
        lock.lock();
        try {
            return deque.size();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取队列中的对象【超时版】
     * @param timeout 超时的数值
     * @param unit 超时的单位
     * @return
     */
    public T take(long timeout, TimeUnit unit){
        //加锁
        lock.lock();
        try {
            //将超时转化成纳秒
            long nanos = unit.toNanos(timeout);
            // 如果队列是空的, 就一直等待
            while (deque.isEmpty()){
                try {
                    if(nanos <= 0){
                        log.info("任务队列是空,获取操作被挂起,超时了,不再等待");
                        break;
                    }
                    log.info("任务队列是空,获取操作被挂起");
                    // awaitNanos 方法传入的参数是: 要等待的纳秒
                    // 返回值 : 还升序多少纳秒要等待, 因为等待过程中有可能被中断, 还没等够时间
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //从队列中取出第一个元素返回
            log.info("获取操作被唤醒");
            T t = deque.removeFirst();
            //唤醒添加元素的线程
            fullWaitSet.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 向队列中放元素
     * @param t
     */
    public void put(T t, long timeout, TimeUnit unit){
        //加锁
        lock.lock();
        try {
            //如果队列已经满了, 就一直等待
            long nanos = unit.toNanos(timeout);
            while (deque.size() == capacity){
                try {
                    if(nanos <= 0){
                        break;
                    }
                    log.info("任务队列已满,添加操作被挂起");
                    nanos = fullWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //向尾部添加元素
            log.info("执行添加操作");
            deque.addLast(t);
            //唤醒获取队列元素的线程
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 定义拒绝策略模式方法, 可扩展性
     * @param rejectPolicy
     * @param task
     */
    public void tryPut(RejectPolicy<T> rejectPolicy, T task) {
        lock.lock();
        try {
        // 判断队列是否满
            if(deque.size() == capacity) {
                rejectPolicy.reject(this, task);
            } else { // 有空闲
                log.debug("加入任务队列 {}", task);
                deque.addLast(task);
                emptyWaitSet.signal();
            }
        } finally {
            lock.unlock();
        }
    }

}

/**
 * 定义函数式接口
 * @param <T>
 */
@FunctionalInterface // 拒绝策略
interface RejectPolicy<T> {
    void reject(BlockingQueue<T> queue, T task);
}