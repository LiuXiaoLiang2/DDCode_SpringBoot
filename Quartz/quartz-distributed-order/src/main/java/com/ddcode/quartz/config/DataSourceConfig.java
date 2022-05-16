package com.ddcode.quartz.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Bean(name = "quartzDataSource")
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource.quartz")
    public HikariDataSource quartzDataSource() {
        return new HikariDataSource();
    }
}
