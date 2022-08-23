package com.ddcode.es.controller;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.awt.print.Book;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * api实现文档的DSL查询搜索
 */
@RestController
@RequestMapping("/es/dsl")
@Slf4j
public class DocumentDSLQueryController {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    /**
     * 获取所有
     * @return
     */
    @RequestMapping("matchAll")
    public String matchAll() throws IOException {

        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }

    /**
     * 展示指定字段
     * @return
     */
    @RequestMapping("displaySpecifiedField")
    public String displaySpecifiedField () throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //只展示name字段
        searchSourceBuilder.fetchSource(new String[]{"name"}, new String[]{});
        searchRequest.source(searchSourceBuilder);

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }

    /**
     * 分页搜索
     * @return
     */
    @RequestMapping("pageSearch")
    public String pageSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //第几页
        int page=1;
        //每页几个
        int size=2;
        //下标计算
        int from=(page-1)*size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);

        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }


    /**
     * 根据ID搜索
     * @return
     */
    @RequestMapping("idsSearch")
    public String idsSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //增加id搜索
        searchSourceBuilder.query(QueryBuilders.idsQuery().addIds("1", "2" , "100"));
        searchRequest.source(searchSourceBuilder);
        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }



    /**
     * match匹配搜索
     * @return
     */
    @RequestMapping("matchSearch")
    public String matchSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //增加id搜索
        searchSourceBuilder.query(QueryBuilders.matchQuery("desc", "java程序员"));
        searchRequest.source(searchSourceBuilder);
        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }


    /**
     * 多字段查询
     * @return
     */
    @RequestMapping("multiMatchSearch")
    public String multiMatchSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //增加id搜索
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery("java程序员", "name", "desc"));
        searchRequest.source(searchSourceBuilder);
        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }


    /**
     * 布尔查询
     * GET /book/_search
     * {
     *   "query": {
     *     "bool": {
     *       "should": [
     *         {
     *           "match": {
     *             "name": "java"
     *           }
     *         }
     *         ,
     *         {
     *           "range": {
     *             "price": {
     *               "gte": 30,
     *               "lte": 100
     *             }
     *           }
     *         }
     *       ]
     *
     *     }
     *   }
     * }
     * @return
     */
    @RequestMapping("boolSearch")
    public String boolSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("book");

        //创建搜索器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //构建布尔搜索
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", "java"));
        boolQueryBuilder.should(QueryBuilders.rangeQuery("price").gte(30.0).lte(100.0));

        //将布尔搜索设置到搜索器中
        searchSourceBuilder.query(boolQueryBuilder);
        //将搜索器设置到请求中
        searchRequest.source(searchSourceBuilder);

        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }


    /**
     * 布尔的嵌套查询
     * GET /blog/_search
     * {
     *   "query": {
     *     "bool": {
     *       "must": [
     *         {
     *           "match": {
     *              "title": "java"
     *           }
     *         },
     *         {
     *           "match": {
     *             "title": "学习"
     *           }
     *         },
     *
     *         {
     *           "bool": {
     *
     *             "should": [
     *               {
     *                  "match": {
     *                    "author": "Iron"
     *                  }
     *                },
     *                {
     *                  "match": {
     *                    "author": "Captain"
     *                  }
     *                }
     *             ]
     *           }
     *         },
     *         {
     *           "bool": {
     *
     *             "should": [
     *               {
     *                 "terms": {
     *                   "serialNum": [1,2,3,4]
     *                 }
     *               },
     *               {
     *                 "terms": {
     *                   "serialNum": [5,6,7,8]
     *                 }
     *               }
     *             ]
     *           }
     *         }
     *
     *       ]
     *     }
     *   }
     * }
     * @return
     */
    //布尔嵌套查询
    @RequestMapping("boolDslSearch")
    public String boolDslSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("blog");

        //创建搜索器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //构建布尔搜索
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "java"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "学习"));


        BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
        boolQueryBuilder1.should(QueryBuilders.matchQuery("author", "Iron"));
        boolQueryBuilder1.should(QueryBuilders.matchQuery("author", "Captain"));
        boolQueryBuilder.must(boolQueryBuilder1);

        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        boolQueryBuilder2.should(QueryBuilders.termsQuery("serialNum", new int[]{1,2,3,4}));
        boolQueryBuilder2.should(QueryBuilders.termsQuery("serialNum", new int[]{5,6,7,8}));
        boolQueryBuilder.must(boolQueryBuilder2);

        //将布尔搜索设置到搜索器中
        searchSourceBuilder.query(boolQueryBuilder);
        //将搜索器设置到请求中
        searchRequest.source(searchSourceBuilder);

        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.info("searchResponse {}", searchResponse);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }


    /**
     * filter查询
     * @return
     * @throws IOException
     */
    @RequestMapping("filterSearch")
    public String filterSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("blog");

        //创建搜索器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //构建布尔搜索
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "java"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "学习"));


        BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
        boolQueryBuilder1.should(QueryBuilders.matchQuery("author", "Iron"));
        boolQueryBuilder1.should(QueryBuilders.matchQuery("author", "Captain"));
        boolQueryBuilder.must(boolQueryBuilder1);

        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        boolQueryBuilder2.should(QueryBuilders.termsQuery("serialNum", new int[]{1,2,3,4}));
        boolQueryBuilder2.should(QueryBuilders.termsQuery("serialNum", new int[]{5,6,7,8}));
        boolQueryBuilder.filter(boolQueryBuilder2);

        //将布尔搜索设置到搜索器中
        searchSourceBuilder.query(boolQueryBuilder);
        //将搜索器设置到请求中
        searchRequest.source(searchSourceBuilder);

        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.info("searchResponse {}", searchResponse);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }


    @RequestMapping("sortSearch")
    public String sortSearch() throws IOException {
        //1.构建请求
        SearchRequest searchRequest = new SearchRequest("blog");

        //创建搜索器
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //构建布尔搜索
        BoolQueryBuilder boolQueryBuilder=QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "java"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "学习"));


        BoolQueryBuilder boolQueryBuilder1 = QueryBuilders.boolQuery();
        boolQueryBuilder1.should(QueryBuilders.matchQuery("author", "Iron"));
        boolQueryBuilder1.should(QueryBuilders.matchQuery("author", "Captain"));
        boolQueryBuilder.must(boolQueryBuilder1);

        BoolQueryBuilder boolQueryBuilder2 = QueryBuilders.boolQuery();
        boolQueryBuilder2.should(QueryBuilders.termsQuery("serialNum", new int[]{1,2,3,4}));
        boolQueryBuilder2.should(QueryBuilders.termsQuery("serialNum", new int[]{5,6,7,8}));
        boolQueryBuilder.filter(boolQueryBuilder2);

        //排序字段
        searchSourceBuilder.sort("serialNum", SortOrder.DESC);

        //将布尔搜索设置到搜索器中
        searchSourceBuilder.query(boolQueryBuilder);
        //将搜索器设置到请求中
        searchRequest.source(searchSourceBuilder);

        //2.执行搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        log.info("searchResponse {}", searchResponse);
        //3.打印
        printBookEs(searchResponse);
        return "ok";
    }



    /**
     * 打印结果
     * @param searchResponse
     */
    public void printBookEs(SearchResponse searchResponse){
        //3.获取结果
        SearchHits searchHits = searchResponse.getHits();
        //4.打印结果
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit : hits) {
            String id = hit.getId();
            float score = hit.getScore();
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info(id + ": score: " + score + ": desc" + sourceAsMap.toString());
        }
    }

}
