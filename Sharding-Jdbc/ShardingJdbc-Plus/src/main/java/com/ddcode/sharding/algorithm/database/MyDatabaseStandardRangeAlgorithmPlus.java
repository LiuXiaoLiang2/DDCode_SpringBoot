package com.ddcode.sharding.algorithm.database;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 实现 RangeShardingAlgorithm 算法, 升级版
 */
@Slf4j
public class MyDatabaseStandardRangeAlgorithmPlus implements RangeShardingAlgorithm<Integer> {


    /**
     * 可以看法 RangeShardingAlgorithm 的抽象方法的返回值
     * 该方法支持 between...and.. sql语句，升级版可以更精确的算出 between...and... 需要的数据库
     * @param databaseNames
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> databaseNames, RangeShardingValue<Integer> rangeShardingValue) {
        log.info("进入 range 分库算法 collection {}", databaseNames);
        Integer lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        Integer upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
        log.info("进入 range 分库算法 between的最小值 {}", lowerEndpoint);
        log.info("进入 range 分库算法 between的最大值 {}", upperEndpoint);

        Set<String> result = new LinkedHashSet<>();
        // 循环范围计算分库逻辑
        for (Integer i = lowerEndpoint; i <= upperEndpoint; i++) {
            for (String databaseName : databaseNames) {
                // between...and... 中可以精确到数据库名，因为我们存储的时候就是按照求余存储的, 所以这里判断也要求余判断
                if (databaseName.endsWith(i % databaseNames.size() + "")) {
                    result.add(databaseName);
                }
            }
        }
        log.info("进入 range 分库算法 result {}", result);
        return result;
    }
}
