package com.ddcode.sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 自定义策略
 */
@Slf4j
public class BirthdayTableAlgorithm implements PreciseShardingAlgorithm<String> {


    /**
     * 参数1：
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<String> preciseShardingValue) {
        log.info("进入自定义分片策略");
        String columnName = preciseShardingValue.getColumnName();
        log.info("进入自定义分片策略, columnName {}", columnName);
        String logicTableName = preciseShardingValue.getLogicTableName();
        log.info("进入自定义分片策略, logicTableName {}", logicTableName);
        String value = preciseShardingValue.getValue();
        log.info("进入自定义分片策略, value {}", value);//每次执行的sql的配置的分片键的值

        //
        collection.forEach((str)->{
            log.info("collection str {}", str); // 这个是配置的每个库
        });

        String result = logicTableName + "_" + value.split("-")[0];
        log.info("最终返回的 result {}", result);
        return result;
    }
}
