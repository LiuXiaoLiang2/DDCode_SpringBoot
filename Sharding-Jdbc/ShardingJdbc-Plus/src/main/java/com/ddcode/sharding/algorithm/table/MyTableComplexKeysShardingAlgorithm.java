package com.ddcode.sharding.algorithm.table;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * 实现 PreciseShardingAlgorithm 算法
 * 用在分表上，返回值就是具体用在哪个表
 */
@Slf4j
public class MyTableComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {


    @Override
    public Collection<String> doSharding(Collection<String> collection, ComplexKeysShardingValue<Long> complexKeysShardingValue) {
        //获取复合分片键传过来的内容
        Map<String, Collection<Long>> columnNameAndShardingValuesMap = complexKeysShardingValue.getColumnNameAndShardingValuesMap();
        log.info("进入分表, 复合算法, columnNameAndShardingValuesMap {}", columnNameAndShardingValuesMap);
        String logicTableName = complexKeysShardingValue.getLogicTableName();
        log.info("进入分表, 复合算法, logicTableName {}", logicTableName);
        log.info("进入分表, 复合算法, collection {}", collection);

        //这里的key就是配置文件中指定的分片键
        Collection<Long> device_types = columnNameAndShardingValuesMap.get("device_type");
        //存放指定的库
        Collection<String> tables = new ArrayList<>();
        for (Long device_type : device_types) {
            //根据deviceType的奇偶选择哪个数据库
            String table = logicTableName + "_" + (device_type % 2);
            tables.add(table);
        }
        return tables;
    }
}
