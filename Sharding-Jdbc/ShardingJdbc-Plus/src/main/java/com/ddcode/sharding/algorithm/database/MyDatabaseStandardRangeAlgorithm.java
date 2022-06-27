package com.ddcode.sharding.algorithm.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;

/**
 * 实现 RangeShardingAlgorithm 算法
 */
@Slf4j
public class MyDatabaseStandardRangeAlgorithm implements RangeShardingAlgorithm<Long> {


    /**
     * 可以看法 RangeShardingAlgorithm 的抽象方法的返回值
     * 该方法支持 between...and.. sql语句，由于我们不知道是去哪个库中查询，所以我们将所以的库都返回，所以返回的集合
     * @param collection
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Long> rangeShardingValue) {
        log.info("进入 range 分库算法 collection {}", collection);
        return collection;
    }
}
