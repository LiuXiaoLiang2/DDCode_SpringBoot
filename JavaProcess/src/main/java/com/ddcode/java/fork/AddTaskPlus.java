package com.ddcode.java.fork;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * 因为有返回值，所以使用 RecursiveTask
 */
@Slf4j(topic = "c.addTaskPlus")
@Data
public class AddTaskPlus extends RecursiveTask<Integer> {

    int begin;
    int end;

    public AddTaskPlus(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        // 5, 5
        //说明拆分到同一个数字了, 直接返回就可以
        if (begin == end) {
            log.debug("join() {}", begin);
            return begin;
        }

        //说明拆分到相邻的数据,那么就返回两者之和
        if (end - begin == 1) {
            log.debug("join() {} + {} = {}", begin, end, end + begin);
            return end + begin;
        }

        // 1 5
        int mid = (end + begin) / 2; // 3
        AddTaskPlus t1 = new AddTaskPlus(begin, mid); // 1,3
        t1.fork();
        AddTaskPlus t2 = new AddTaskPlus(mid + 1, end); // 4,5
        t2.fork();
        log.debug("fork() {} + {} = ?", t1, t2);
        int result = t1.join() + t2.join();
        log.debug("join() {} + {} = {}", t1, t2, result);
        return result;
    }
}
