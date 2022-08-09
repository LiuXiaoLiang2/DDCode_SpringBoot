package com.ddcode.redisson.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Bean(destroyMethod="shutdown")
    Redisson redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useClusterServers()
                .addNodeAddress("redis://192.168.137.132:6380")
                .addNodeAddress("redis://192.168.137.132:6381")
                .addNodeAddress("redis://192.168.137.132:6382")
                .addNodeAddress("redis://192.168.137.132:7380")
                .addNodeAddress("redis://192.168.137.132:7381")
                .addNodeAddress("redis://192.168.137.132:7382");
        //创建redisson客户端对象
        return (Redisson) Redisson.create(config);
    }

}
