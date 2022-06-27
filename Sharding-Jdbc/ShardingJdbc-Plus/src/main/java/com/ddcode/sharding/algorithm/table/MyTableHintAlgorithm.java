package com.ddcode.sharding.algorithm.table;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * 实现 RangeShardingAlgorithm 算法
 */
@Slf4j
public class MyTableHintAlgorithm implements HintShardingAlgorithm<Long> {


    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Long> hintShardingValue) {
        log.info("collection {}", collection);
        log.info("hintShardingValue {}", hintShardingValue.getValues());
        Collection<String> result = new ArrayList<>();
        for (String each : collection) {
            for (Long value : hintShardingValue.getValues()) {
                if (each.endsWith(String.valueOf(value % 2))) {
                    result.add(each);
                }
            }
        }
        log.info("result {}", result);
        return result;
    }
}
