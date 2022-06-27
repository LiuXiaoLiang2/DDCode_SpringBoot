package com.ddcode.sharding.algorithm.table;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 实现 PreciseShardingAlgorithm 算法, 升级版
 * 用在分表上，返回值就是具体用在哪个表
 */
@Slf4j
public class MyTableStandardRangeAlgorithmPlus implements RangeShardingAlgorithm<Integer> {


    /**
     * 将所有的执行的表,都返回给shardingJDBC,去执行
     * @param tables
     * @param rangeShardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> tables, RangeShardingValue<Integer> rangeShardingValue) {
        log.info("进入 range 分表算法 collection {}", tables);
        Integer lowerEndpoint = rangeShardingValue.getValueRange().lowerEndpoint();
        Integer upperEndpoint = rangeShardingValue.getValueRange().upperEndpoint();
        log.info("进入 range 分表算法 between的最小值 {}", lowerEndpoint);
        log.info("进入 range 分表算法 between的最大值 {}", upperEndpoint);

        Set<String> result = new LinkedHashSet<>();
        // 循环范围计算分库逻辑
        for (Integer i = lowerEndpoint; i <= upperEndpoint; i++) {
            for (String table : tables) {
                // between...and... 中可以精确到数据库名，因为我们存储的时候就是按照求余存储的, 所以这里判断也要求余判断
                if (table.endsWith(i % tables.size() + "")) {
                    result.add(table);
                }
            }
        }
        log.info("进入 range 分表算法 result {}", result);
        return result;
    }
}
