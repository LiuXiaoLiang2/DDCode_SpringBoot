package com.ddcode.sharding;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ddcode.sharding.mapper")
public class ShardingJdbcPlusApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingJdbcPlusApplication.class, args);
    }
}
