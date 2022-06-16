package com.ddcode.java.fork;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RecursiveTask;

/**
 * 因为有返回值，所以使用 RecursiveTask
 */
@Slf4j(topic = "c.addTask")
@Data
public class AddTask extends RecursiveTask<Integer> {

    private int n;

    //使用构造函数, 将变量传进来
    public AddTask(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {

        if(n == 1){
            //说明到了最后一步了, 可以返回结果了
            log.info("到了最后一步返回结果 1");
            return 1;
        }

        //否则还可以进行拆分任务
        //再下一级拆分任务
        AddTask addTask = new AddTask(n - 1);
        log.info("n: {}, 可以拆分任务",  n);
        //执行了fork才会拆分, 这个时候就会新开一下线程, 重新又执行 compute() 方法
        addTask.fork();
        //合并结果, 执行了join会阻塞等待
        log.info("n: {}, 下一级返回,阻塞等待",  n);
        Integer result = n + addTask.join();
        log.info("n: {}, 下一级返回,结果 {}",  n, result);

        return result;
    }
}
