package com.ddcode.es.controller;

import com.ddcode.es.po.Television;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.*;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Avg;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Min;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.clients.elasticsearch7.ElasticsearchAggregations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 *  api实现ES聚合
 */
@RestController
@RequestMapping("/es/group")
@Slf4j
public class DocumentGroupController {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 基本使用
     * qi
     * GET tvs/_search
     * {
     *   "size": 0,
     *   "aggs": {
     *     "group_by_color": {
     *       "terms": {
     *         "field": "color"
     *       }
     *     }
     *   }
     * }
     * @return
     */
    @RequestMapping("/test1")
    public String test1(){

        // 创建总的query
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //创建分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_color").field("color");
        //将创建分组的请求
        nativeSearchQueryBuilder.withAggregations(termsAggregationBuilder);

        // 数据分页。需求是聚合，数据本身我并不需要，所以只返回一条数据就行
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<Television> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Television.class);
        log.info("search {}", searchHits);

        //解析获取分组后的数据
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        Terms group_by_color = aggregations.aggregations().get("group_by_color");
        List<? extends Terms.Bucket> buckets = group_by_color.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            System.out.println("key:"+key);

            long docCount = bucket.getDocCount();
            System.out.println("docCount:"+docCount);

            System.out.println("=================================");
        }


        //遍历的另外一种方法
        Map<String, Aggregation> stringAggregationMap = aggregations.aggregations().asMap();
        log.info("stringAggregationMap {}" , stringAggregationMap);


        return "ok";
    }


    /**
     * 聚合子查询
     * #按照颜色分组，计算每个颜色卖出的个数，每个颜色卖出的平均价格
     *
     * GET tvs/_search
     * {
     *   "size": 0,
     *   "aggs": {
     *     "group_by_color": {
     *       "terms": {
     *         "field": "color"
     *       },
     *       "aggs": {
     *         "avg_price": {
     *           "avg": {
     *             "field": "price"
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     * @return
     */
    @RequestMapping("/test2")
    public String test2(){

        // 创建总的query
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //创建分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_color").field("color");

        //构建子查询
        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("avg_price").field("price"));

        //将创建分组的请求
        nativeSearchQueryBuilder.withAggregations(termsAggregationBuilder);


        // 数据分页。需求是聚合，数据本身我并不需要，所以只返回一条数据就行
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<Television> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Television.class);
        log.info("search {}", searchHits);

        //解析获取分组后的数据
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        Terms group_by_color = aggregations.aggregations().get("group_by_color");
        List<? extends Terms.Bucket> buckets = group_by_color.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);

            Aggregations aggregations1 = bucket.getAggregations();
            Avg avgPrice = aggregations1.get("avg_price");
            double value = avgPrice.getValue();
            log.info("avgPrice {}" , value);

            log.info("=================================");
        }
        return "ok";
    }


    /**
     * 多个分组子查询
     * # 需求三：按照颜色分组，计算每个颜色卖出的个数，以及每个颜色卖出的平均值、最大值、最小值、总和。
     * GET tvs/_search
     * {
     *   "size": 0,
     *   "aggs": {
     *     "group_by_color": {
     *       "terms": {
     *         "field": "color"
     *       },
     *       "aggs": {
     *         "avg_price": {
     *           "avg": {
     *             "field": "price"
     *           }
     *         },
     *         "sum_price":{
     *           "sum": {
     *             "field": "price"
     *           }
     *         },
     *         "max_price":{
     *           "max": {
     *             "field": "price"
     *           }
     *         },
     *         "min_price":{
     *           "min": {
     *             "field": "price"
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     * @return
     */
    @RequestMapping("/test3")
    public String test3(){

        // 创建总的query
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //创建分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_color").field("color");

        //构建多个子查询
        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("avg_price").field("price"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_price").field("price"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.max("max_price").field("price"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.min("min_price").field("price"));

        //将创建分组的请求
        nativeSearchQueryBuilder.withAggregations(termsAggregationBuilder);


        // 数据分页。需求是聚合，数据本身我并不需要，所以只返回一条数据就行
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<Television> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Television.class);
        log.info("search {}", searchHits);

        //解析获取分组后的数据
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        Terms group_by_color = aggregations.aggregations().get("group_by_color");
        List<? extends Terms.Bucket> buckets = group_by_color.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);

            Aggregations aggregations1 = bucket.getAggregations();
            Avg avgPrice = aggregations1.get("avg_price");
            double avgPriceValue = avgPrice.getValue();
            log.info("avgPriceValue {}" , avgPriceValue);


            Sum sumPrice = aggregations1.get("sum_price");
            double sumPriceValue = sumPrice.getValue();
            log.info("sumPriceValue {}" , sumPriceValue);


            Min minPrice = aggregations1.get("min_price");
            double minPriceValue = minPrice.getValue();
            log.info("minPriceValue {}" , minPriceValue);


            Max maxPrice = aggregations1.get("max_price");
            double maxPriceValue = maxPrice.getValue();
            log.info("maxPriceValue {}" , maxPriceValue);

            log.info("=================================");
        }
        return "ok";
    }

    /**
     * #需求四：按照售价每2000价格划分范围，算出每个区间的销售总额 histogram
     *
     * GET tvs/_search
     * {
     *   "size": 0,
     *   "aggs": {
     *     "histogram_by_price": {
     *       "histogram": {
     *         "field": "price",
     *         "interval": 2000
     *       },
     *       "aggs": {
     *         "sum_price": {
     *           "sum": {
     *             "field": "price"
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     */
    @RequestMapping("/test4")
    public String test4(){

        // 创建总的query
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        //创建分组
        HistogramAggregationBuilder histogramAggregationBuilder = AggregationBuilders.histogram("histogram_by_price").field("price").interval(2000);

        //构建子查询
        histogramAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_price").field("price"));

        //将创建分组的请求
        nativeSearchQueryBuilder.withAggregations(histogramAggregationBuilder);


        // 数据分页。需求是聚合，数据本身我并不需要，所以只返回一条数据就行
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<Television> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Television.class);
        log.info("search {}", searchHits);

        //解析获取分组后的数据
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        Histogram group_by_color = aggregations.aggregations().get("histogram_by_price");
        List<? extends Histogram.Bucket> buckets = group_by_color.getBuckets();
        for (Histogram.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);

            Aggregations aggregations1 = bucket.getAggregations();
            Sum sumPrice = aggregations1.get("sum_price");
            if(sumPrice != null){
                double value = sumPrice.getValue();
                log.info("value:"+value);
            }

            log.info("=================================");
        }
        return "ok";
    }


    /**
     * # 需求五：计算每个季度的销售总额
     * GET tvs/_search
     * {
     *   "size": 0,
     *   "aggs": {
     *     "histogram_by_date": {
     *       "date_histogram": {
     *         "field": "sold_date",
     *         "interval": "quarter",
     *         "format": "yyyy-MM-dd",
     *         "min_doc_count": 0,
     *         "extended_bounds": {
     *           "min": "2019-01-01",
     *           "max": "2020-12-31"
     *         }
     *       },
     *       "aggs": {
     *         "sum_price": {
     *           "sum": {
     *             "field": "price"
     *           }
     *         }
     *       }
     *     }
     *   }
     * }
     * @return
     */
    @RequestMapping("/test5")
    public String test5(){
        // 创建总的query
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //创建分组
        DateHistogramAggregationBuilder dateHistogramAggregationBuilder =
                AggregationBuilders.dateHistogram("histogram_by_date").field("sold_date").calendarInterval(DateHistogramInterval.QUARTER).format("yyyy-MM-dd").minDocCount(0).extendedBounds(new LongBounds("2019-01-01", "2020-12-31"));


        dateHistogramAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_price").field("price"));
        //将创建分组的请求
        nativeSearchQueryBuilder.withAggregations(dateHistogramAggregationBuilder);


        // 数据分页。需求是聚合，数据本身我并不需要，所以只返回一条数据就行
        Pageable pageable = PageRequest.of(0, 1);
        nativeSearchQueryBuilder.withPageable(pageable);

        SearchHits<Television> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(), Television.class);
        log.info("search {}", searchHits);

        //解析获取分组后的数据
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) searchHits.getAggregations();
        ParsedDateHistogram parsedDateHistogram = aggregations.aggregations().get("histogram_by_date");
        List<? extends Histogram.Bucket> buckets = parsedDateHistogram.getBuckets();
        for (Histogram.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);

            Aggregations aggregations1 = bucket.getAggregations();
            Sum sumPrice = aggregations1.get("sum_price");
            if(sumPrice != null){
                double value = sumPrice.getValue();
                log.info("value:"+value);
            }

            log.info("=================================");
        }


        return "ok";
    }
}
