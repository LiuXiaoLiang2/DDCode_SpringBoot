package com.ddcode.sharding.algorithm.table;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * 实现 PreciseShardingAlgorithm 算法
 * 用在分表上，返回值就是具体用在哪个表
 */
@Slf4j
public class MyTableStandardPreciseAlgorithm implements PreciseShardingAlgorithm<Long> {


    /**
     * 参数1: 配置的所有表的集合, tb_device_$->{device_id % 2}, 这里就是 tb_device_0 和 tb_device_1
     * 参数2: 以获取到逻辑表名称, 分片键, 执行sql的时候分片键对应的值
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        log.info("进入分表算法");
        log.info("进入分表算法, 所有表的集合 {}", collection.toString());
        String logicTableName = preciseShardingValue.getLogicTableName();
        log.info("进入分表算法, 逻辑表的名称 {}", logicTableName);
        String columnName = preciseShardingValue.getColumnName();
        log.info("进入分表算法, 分片键的名称 {}", columnName);
        Long value = preciseShardingValue.getValue();
        log.info("进入分表算法, 执行当前sql的时候分片键对应的值 {}", value);
        String tableName = logicTableName + "_" + (value % 2);
        if(!collection.contains(tableName)){
            throw new UnsupportedOperationException("不存在该表");
        }
        log.info("进入分表算法, 最终返回的表名称 {}", tableName);
        return tableName;
    }
}
