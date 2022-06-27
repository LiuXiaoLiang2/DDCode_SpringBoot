package com.ddcode.sharding.algorithm.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * 实现 PreciseShardingAlgorithm 算法
 * 用在分库上，返回值就是具体用在哪个库
 */
@Slf4j
public class MyDatabaseStandardPreciseAlgorithm implements PreciseShardingAlgorithm<Long> {


    /**
     * 参数1: 就是所有配置的数据库的集合, 就是下面配置的 ds$->{0..1}, 其实就是 ds0 和 ds1 的集合
     *  spring.shardingsphere.sharding.tables.tb_device.actual-data-nodes=ds$->{0..1}.tb_device_$->{0..1}
     * 参数2: 可以获取到逻辑表名称, 分片键, 执行sql的时候分片键对应的值
     * @param collection
     * @param preciseShardingValue
     * @return
     */
    @Override
    public String doSharding(Collection<String> collection, PreciseShardingValue<Long> preciseShardingValue) {
        log.info("进入分库算法");
        log.info("进入分库算法, 数据库的集合 {}", collection.toString());
        String logicTableName = preciseShardingValue.getLogicTableName();
        log.info("进入分库算法, 逻辑表的名称 {}", logicTableName);
        String columnName = preciseShardingValue.getColumnName();
        log.info("进入分库算法, 分片键的名称 {}", columnName);
        Long value = preciseShardingValue.getValue();
        log.info("进入分库算法, 执行当前sql的时候分片键对应的值 {}", value);
        String dsName = "";
        dsName = "ds" + (value % 2);
        if(!collection.contains(dsName)){
            throw new UnsupportedOperationException("没有该数据源");
        }
        log.info("进入分库算法, 最终返回数据库的名称 {}", dsName);
        return dsName;
    }
}
