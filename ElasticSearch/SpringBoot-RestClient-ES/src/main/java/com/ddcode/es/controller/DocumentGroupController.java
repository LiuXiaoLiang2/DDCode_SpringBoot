package com.ddcode.es.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.histogram.*;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * 使用 RestHighLevelClient 方式完成 ES 的聚合
 */

@RestController
@RequestMapping("/es/group")
@Slf4j
public class DocumentGroupController {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 基本使用
     * 按照颜色分组，计算每个颜色卖出的个数
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
    public String test1() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("tvs");
        //请求体
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //因为我们不需要返回值,所以设置size=0
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //组织分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_color").field("color");
        searchSourceBuilder.aggregation(termsAggregationBuilder);

        //请求体放入请求头
        searchRequest.source(searchSourceBuilder);

        //2 执行
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        
        Aggregations aggregations = searchResponse.getAggregations();
        Terms group_by_color = aggregations.get("group_by_color");
        List<? extends Terms.Bucket> buckets = group_by_color.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);
            log.info("=================================");
        }
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
    public String test2() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("tvs");
        //请求体
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //因为我们不需要返回值,所以设置size=0
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //组织分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_color").field("color");

        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("avg_price").field("price"));

        searchSourceBuilder.aggregation(termsAggregationBuilder);

        //请求体放入请求头
        searchRequest.source(searchSourceBuilder);

        //2 执行
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Terms group_by_color = aggregations.get("group_by_color");
        List<? extends Terms.Bucket> buckets = group_by_color.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);


            Aggregations aggregations1 = bucket.getAggregations();
            ParsedAvg avgPrice = aggregations1.get("avg_price");
            double value = avgPrice.getValue();
            log.info("value:"+value);

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
    public String test3() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("tvs");
        //请求体
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //因为我们不需要返回值,所以设置size=0
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //组织分组
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("group_by_color").field("color");

        termsAggregationBuilder.subAggregation(AggregationBuilders.avg("avg_price").field("price"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_price").field("price"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.max("max_price").field("price"));
        termsAggregationBuilder.subAggregation(AggregationBuilders.min("min_price").field("price"));

        searchSourceBuilder.aggregation(termsAggregationBuilder);

        //请求体放入请求头
        searchRequest.source(searchSourceBuilder);

        //2 执行
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        Terms group_by_color = aggregations.get("group_by_color");
        List<? extends Terms.Bucket> buckets = group_by_color.getBuckets();
        for (Terms.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);


            Aggregations aggregations1 = bucket.getAggregations();
            ParsedAvg avgPrice = aggregations1.get("avg_price");
            double avgPriceValue = avgPrice.getValue();
            log.info("avgPriceValue:"+avgPriceValue);

            ParsedSum sumPrice = aggregations1.get("sum_price");
            double sumPriceValue = sumPrice.getValue();
            log.info("sumPriceValue:"+sumPriceValue);

            ParsedMin minPrice = aggregations1.get("min_price");
            double minPriceValue = minPrice.getValue();
            log.info("minPriceValue:"+minPriceValue);

            ParsedMax maxPrice = aggregations1.get("max_price");
            double maxPriceValue = maxPrice.getValue();
            log.info("maxPriceValue:"+maxPriceValue);


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
    public String test4() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("tvs");
        //请求体
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //因为我们不需要返回值,所以设置size=0
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //组织分组
        HistogramAggregationBuilder histogramAggregationBuilder = AggregationBuilders.histogram("histogram_by_price").field("price").interval(2000);
        histogramAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_price").field("price"));
        searchSourceBuilder.aggregation(histogramAggregationBuilder);

        //请求体放入请求头
        searchRequest.source(searchSourceBuilder);

        //2 执行
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        ParsedHistogram group_by_color = aggregations.get("histogram_by_price");
        List<? extends Histogram.Bucket> buckets = group_by_color.getBuckets();
        for (Histogram.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);


            Aggregations aggregations1 = bucket.getAggregations();
            ParsedSum sumPrice = aggregations1.get("sum_price");
            double value = sumPrice.getValue();
            log.info("value:"+value);

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
    public String test5() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("tvs");
        //请求体
        SearchSourceBuilder searchSourceBuilder=new SearchSourceBuilder();
        //因为我们不需要返回值,所以设置size=0
        searchSourceBuilder.size(0);
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //组织分组

        DateHistogramAggregationBuilder dateHistogramAggregationBuilder =
                    AggregationBuilders.dateHistogram("histogram_by_date").field("sold_date").calendarInterval(DateHistogramInterval.QUARTER).format("yyyy-MM-dd").minDocCount(0).extendedBounds(new ExtendedBounds("2019-01-01", "2020-12-31"));
        dateHistogramAggregationBuilder.subAggregation(AggregationBuilders.sum("sum_price").field("price"));

        searchSourceBuilder.aggregation(dateHistogramAggregationBuilder);

        //请求体放入请求头
        searchRequest.source(searchSourceBuilder);

        //2 执行
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        Aggregations aggregations = searchResponse.getAggregations();
        ParsedDateHistogram group_by_color = aggregations.get("histogram_by_date");
        List<? extends Histogram.Bucket> buckets = group_by_color.getBuckets();
        for (Histogram.Bucket bucket : buckets) {
            String key = bucket.getKeyAsString();
            log.info("key:"+key);

            long docCount = bucket.getDocCount();
            log.info("docCount:"+docCount);


            Aggregations aggregations1 = bucket.getAggregations();
            ParsedSum sumPrice = aggregations1.get("sum_price");
            double value = sumPrice.getValue();
            log.info("value:"+value);

            log.info("=================================");
        }
        return "ok";
    }

}
