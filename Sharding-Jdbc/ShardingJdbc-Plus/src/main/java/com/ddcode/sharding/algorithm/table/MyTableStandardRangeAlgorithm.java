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
public class MyTableStandardRangeAlgorithm implements RangeShardingAlgorithm<Long> {


    /**
     * 将所有的执行的表,都返回给shardingJDBC,去执行
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        log.info("进入 range 分表, collection {}", collection);
        return collection;
    }
}
