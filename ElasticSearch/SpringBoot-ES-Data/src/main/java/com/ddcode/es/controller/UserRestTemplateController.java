package com.ddcode.es.controller;

import com.ddcode.es.dao.UserDao;
import com.ddcode.es.po.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.Script;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class UserRestTemplateController {



    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 创建索引
     * @return
     */
    @RequestMapping("createIndex")
    public String createIndex(){
        boolean b = elasticsearchRestTemplate.indexOps(User.class).create();
        log.info("createIndex {}", b);
        return "ok";
    }

    /**
     * 获取索引, 判断索引是否存在
     * @return
     */
    @RequestMapping("getIndex")
    public String getIndex(){
        boolean b = elasticsearchRestTemplate.indexOps(User.class).exists();
        log.info("createIndex {}", b);
        return "ok";
    }


    /**
     * 删除索引
     * @return
     */
    @RequestMapping("deleteIndex")
    public String deleteIndex(){
        boolean b = elasticsearchRestTemplate.indexOps(User.class).delete();
        log.info("deleteIndex {}", b);
        return "ok";
    }



    /**
     * 添加用户
     * @return
     */
    @RequestMapping("addUser")
    public String addUser(){
        User user = User.builder().id("1").name("张三").sex("男").build();
        User save = elasticsearchRestTemplate.save(user);
        log.info("addUser {}", save);
        return "ok";
    }

    /**
     * 批量添加
     * @return
     */
    @RequestMapping("addUserBatch")
    public String addUserBatch(){
        User user1 = User.builder().id("1").name("张三1").sex("男").build();
        User user2 = User.builder().id("2").name("张三2").sex("女").build();
        User user3 = User.builder().id("3").name("张三3").sex("男").build();
        List<User> of = Arrays.asList(user1, user2, user3);
        Iterable<User> save = elasticsearchRestTemplate.save(of);
        log.info("addUserBatch {}", save);
        return "ok";
    }


    /**
     * 增加方式2, 使用ES自带的主键, 不设置id值即可
     * @return
     */
    @RequestMapping("addUser2")
    public String addUser2(){
        User user = User.builder().name("张三4").sex("男").build();
        User save = elasticsearchRestTemplate.save(user);
        log.info("addUser {}", save);
        return "ok";
    }

    /**
     * 增加方式3, 要返回ES的自带主键
     * @return
     */
    @RequestMapping("addUser3")
    public String addUser3(){
        User user = User.builder().name("张三5").sex("男").build();
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(user).withVersion(10L).build();
        //返回的主键id, 自带的主键
        String id = elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of("user-index"));
        log.info("addUser3 {}", id);
        return "ok";
    }


    /**
     * 增加方式4, 自带版本
     * @return
     */
    @RequestMapping("addUser4")
    public String addUser4(){
        User user = User.builder().id("4").name("张三4").sex("男").build();
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(user).withVersion(10L).build();
        //返回的主键id, 自带的主键
        String id = elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of("user-index"));
        log.info("addUser4 {}", id);
        return "ok";
    }


    @RequestMapping("addUser5")
    public String addUser5(){
        User user1 = User.builder().name("王五1").sex("男").build();
        IndexQuery indexQuery1 = new IndexQueryBuilder().withObject(user1).build();
        User user2 = User.builder().name("王五2").sex("男").build();
        IndexQuery indexQuery2 = new IndexQueryBuilder().withObject(user2).build();
        User user3 = User.builder().name("王五3").sex("男").build();
        IndexQuery indexQuery3 = new IndexQueryBuilder().withObject(user3).build();
        List<IndexQuery> indexQueries = Arrays.asList(indexQuery1, indexQuery2, indexQuery3);


        //返回的主键id, 自带的主键
        List<IndexedObjectInformation> indexedObjectInformations = elasticsearchRestTemplate.bulkIndex(indexQueries, User.class);
        for (IndexedObjectInformation indexedObjectInformation : indexedObjectInformations) {
            log.info("indexedObjectInformation {}", indexedObjectInformation);
            log.info("indexedObjectInformation {}", indexedObjectInformation.getVersion());
        }
        return "ok";
    }


    /**
     * 覆盖更新
     */
    @RequestMapping("/updateAll")
    public String updateAll(){
        User user = User.builder().id("4").name("张三4_update").sex("男_update").build();
        IndexQuery indexQuery = new IndexQueryBuilder().withObject(user).build();
        //返回的主键id, 自带的主键
        String id = elasticsearchRestTemplate.index(indexQuery, IndexCoordinates.of("user-index"));
        log.info("update1 {}", id);
        return "ok";
    }


    /**
     * 局部更新方式1: 根据id更新
     * @return
     */
    @RequestMapping("/partialUpdate1")
    public String partialUpdate1() {
        Document doc = Document.create();
        doc.putIfAbsent("name", "张三");
        doc.putIfAbsent("sex", "女");
        UpdateQuery updateQuery = UpdateQuery.builder("4").withDocument(doc).withRetryOnConflict(5).build();
        UpdateResponse update = elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("user-index"));
        log.info("partialUpdate1 {}", update);
        return "ok";
    }


    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 批量更新: 根据筛选条件批量更新
     * @return
     */
    @RequestMapping("/batchUpdate")
    public String batchUpdate() throws IOException {
        UpdateByQueryRequest updateByQueryRequest = new UpdateByQueryRequest("user-index");
        updateByQueryRequest.setConflicts("proceed");//设置版本冲突
        //设置查询条件
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
//        并且 and
        queryBuilder.must(QueryBuilders.termQuery("sex", "男"));
        updateByQueryRequest.setQuery(queryBuilder);
//        批次大小
        updateByQueryRequest.setBatchSize(1000);
//        并行
        updateByQueryRequest.setSlices(2);
//        使用滚动参数来控制“搜索上下文”存活的时间
        updateByQueryRequest.setScroll(TimeValue.timeValueMinutes(10));
//        刷新索引
        updateByQueryRequest.setRefresh(true);
//        更新的内容
        updateByQueryRequest.setScript(new Script("ctx._source['name']='wangwu'"));
        BulkByScrollResponse response = restHighLevelClient.updateByQuery(updateByQueryRequest, RequestOptions.DEFAULT);
        log.info(response.getStatus().getUpdated() + ""); // 返回1 表示成功
        return "ok";
    }


    /**
     * 根据id删除
     * @return
     */
    @RequestMapping("/deleteById")
    public String deleteById(){
        String delete = elasticsearchRestTemplate.delete("4", User.class);
        log.info("delete {}", delete);
        return "ok";
    }

    @RequestMapping("/deleteQuery")
    public String deleteQuery(){
        //        根据条件删除
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        queryBuilder.must(QueryBuilders.termQuery("sex", "男"));
        Query query = new NativeSearchQuery(queryBuilder);
        ByQueryResponse delete = elasticsearchRestTemplate.delete(query, User.class);
        log.info("delete {}", delete);
        return "ok";
    }

    /**
     * 根据id获取
     * @return
     */
    @RequestMapping("/getById")
    public String getById(){
        User user = elasticsearchRestTemplate.get("4", User.class);
        log.info("user {}", user);
        return "ok";
    }

    /**
     * search 查询
     * @return
     */
    @RequestMapping("/search")
    public String search(){
        QueryBuilder queryBuilder = QueryBuilders.termQuery("sex", "男");
        Query query = new NativeSearchQuery(queryBuilder);
        SearchHits<User> search = elasticsearchRestTemplate.search(query, User.class);
        Stream<SearchHit<User>> searchHitStream = search.get();
        List<User> list = searchHitStream.map(SearchHit::getContent).collect(Collectors.toList());
        log.info("list {}" ,list);
        return "ok";
    }

}
