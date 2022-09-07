package com.ddcode.es.controller;

import com.ddcode.es.po.Blog;
import com.ddcode.es.po.Book;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * api实现文档的DSL查询搜索
 */
@RestController
@RequestMapping("/es/dsl")
@Slf4j
public class DocumentDSLQueryController {

    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;


    /**
     * 获取所有
     * @return
     */
    @RequestMapping("matchAll")
    public String matchAll(){
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        Query query = new NativeSearchQuery(matchAllQueryBuilder);
        SearchHits<Book> search = elasticsearchRestTemplate.search(query, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }

    /**
     * 展示指定字段
     * @return
     */
    @RequestMapping("displaySpecifiedField")
    public String displaySpecifiedField (){
        //使用的还是matchAll查询
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //使用fetch指定返回哪些字段(第一个参数), 指定不返回哪些参数(第二个参数)
        FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(new String[]{"name"}, new String[]{});

        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQueryBuilder)
                .withSourceFilter(fetchSourceFilter)
                .build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }


    /**
     * 分页搜索
     * @return
     */
    @RequestMapping("pageSearch")
    public String pageSearch(){
        //使用的还是matchAll查询
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //使用fetch指定返回哪些字段(第一个参数), 指定不返回哪些参数(第二个参数)
        FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(new String[]{"name", "price", "desc"}, new String[]{});
        //设置分页参数
        //参数1:第几页,  参数2:展示个数, 参数3: 按照price字段降序
        PageRequest pageRequest = PageRequest.of(0, 2, Sort.by("price").descending());

        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQueryBuilder)
                .withSourceFilter(fetchSourceFilter)
                .withPageable(pageRequest)
                .build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }

    /**
     * 根据ID搜索
     * @return
     */
    @RequestMapping("idsSearch")
    public String idsSearch(){
        //使用的还是matchAll查询
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        //使用fetch指定返回哪些字段(第一个参数), 指定不返回哪些参数(第二个参数)
        FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(new String[]{"name", "price", "desc"}, new String[]{});

        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchAllQueryBuilder)
                .withSourceFilter(fetchSourceFilter)
                .withIds("1", "2", "10000")
                .build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }


    /**
     * match匹配搜索
     * @return
     */
    @RequestMapping("matchSearch")
    public String matchSearch(){
        //使用的match查询
        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("desc", "java程序员");

        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery)
                .build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }


    /**
     * 多字段查询
     * @return
     */
    @RequestMapping("multiMatchSearch")
    public String multiMatchSearch(){
        //使用的multiMatch查询
        //第一个参数: 要匹配的值
        //后面的参数,都是可变参数,可以输入多个,都是字段名
        MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("java程序员", "name", "desc");
        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQueryBuilder)
                .build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
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
    public String boolSearch(){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery("name", "java"));
        boolQueryBuilder.should(QueryBuilders.rangeQuery("price").gte(30.0).lte(100.0));
        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        SearchHits<Book> search = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        Stream<SearchHit<Book>> searchHitStream = search.get();
        List<Book> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
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
    @RequestMapping("boolDslSearch")
    public String boolDslSearch(){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title","java"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "学习"));


        boolQueryBuilder.must(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("author","Iron"))
                        .should(QueryBuilders.matchQuery("author","Captain"))

        );

        boolQueryBuilder.must(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.termsQuery("serialNum", new int[]{1,2,3,4}))
                        .should(QueryBuilders.termsQuery("serialNum", new int[]{5,6,7,8}))

        );

        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        SearchHits<Blog> search = elasticsearchRestTemplate.search(nativeSearchQuery, Blog.class);
        Stream<SearchHit<Blog>> searchHitStream = search.get();
        List<Blog> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }


    /**
     * filter查询
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
     *         }
     *       ],
     *       "filter": {
     *         "bool": {
     *           "should": [
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
     *         }
     *       }
     *     }
     *   }
     * }
     * @return
     */
    @RequestMapping("filterSearch")
    public String filterSearch(){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title","java"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "学习"));


        boolQueryBuilder.must(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("author","Iron"))
                        .should(QueryBuilders.matchQuery("author","Captain"))

        );

        //更改成filter查询
        boolQueryBuilder.filter(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.termsQuery("serialNum", new int[]{1,2,3,4}))
                        .should(QueryBuilders.termsQuery("serialNum", new int[]{5,6,7,8}))

        );

        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .build();
        SearchHits<Blog> search = elasticsearchRestTemplate.search(nativeSearchQuery, Blog.class);
        Stream<SearchHit<Blog>> searchHitStream = search.get();
        List<Blog> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }


    /**
     * 排序查询
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
     *         }
     *       ],
     *       "filter": {
     *         "bool": {
     *           "should": [
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
     *         }
     *       }
     *     }
     *   },
     *   "sort": [
     *     {
     *       "serialNum": {
     *         "order": "desc"
     *       }
     *     }
     *   ]
     * }
     * @return
     */
    @RequestMapping("sortSearch")
    public String sortSearch(){
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("title","java"));
        boolQueryBuilder.must(QueryBuilders.matchQuery("title", "学习"));


        boolQueryBuilder.must(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.matchQuery("author","Iron"))
                        .should(QueryBuilders.matchQuery("author","Captain"))

        );

        //更改成filter查询
        boolQueryBuilder.filter(
                QueryBuilders.boolQuery()
                        .should(QueryBuilders.termsQuery("serialNum", new int[]{1,2,3,4}))
                        .should(QueryBuilders.termsQuery("serialNum", new int[]{5,6,7,8}))

        );
        //将上面两个参数设置到searchQuery中
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withSorts(SortBuilders.fieldSort("serialNum").order(SortOrder.DESC))
                .build();
        SearchHits<Blog> search = elasticsearchRestTemplate.search(nativeSearchQuery, Blog.class);
        Stream<SearchHit<Blog>> searchHitStream = search.get();
        List<Blog> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }



}
