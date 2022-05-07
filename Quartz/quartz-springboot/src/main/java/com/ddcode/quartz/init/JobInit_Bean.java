package com.ddcode.quartz.init;

import com.ddcode.quartz.job.SpringJob1;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * 创建方式2：使用Bean方式创建触发器和JobDetail
 * 该方式：将创建好的JobDetail和Trigger都交给Spring管理, 最后执行也是Spring给执行的
 */
@Component
@Slf4j
public class JobInit_Bean {

    /**
     * 创建好的JobDetail交给Spring管理
     * @return
     */
    @Bean
    public JobDetail createJobDetail(){
        return JobBuilder.newJob(SpringJob1.class)
                .withIdentity("createJobDetail")
                .storeDurably()
                .build();
    }

    /**
     * 创建好的Trigger交给Spring管理
     * @return
     */
    @Bean
    public Trigger createTrigger(){
        return TriggerBuilder.newTrigger()
                .forJob("createJobDetail")
                .startNow()
                .build();
    }
}
