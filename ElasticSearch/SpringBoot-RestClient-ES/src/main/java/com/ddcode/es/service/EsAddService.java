package com.ddcode.es.service;

import com.alibaba.fastjson.JSON;
import com.ddcode.es.po.User;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.VersionType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EsAddService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 构建文档方式1
     */
    public void add1(Long id){
        try {
            IndexRequest request=new IndexRequest("test_user");
            //手动设置id
            request.id(id.toString());

            //构建文档
            User user = User.builder().id(1L).name("张三").sex("男").build();
            request.source(JSON.toJSONString(user), XContentType.JSON);

            //发送请求
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("indexResponse {}", indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建方式2
     */
    public void add2(Long id){
        try {
            IndexRequest request=new IndexRequest("test_user");
            //手动设置id
            request.id(id.toString());

            //构建文档
            Map<String,Object> jsonMap=new HashMap<>();
            jsonMap.put("id", 2L);
            jsonMap.put("name", "王五");
            jsonMap.put("sex", "男");
            request.source(jsonMap);

            //发送请求
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("indexResponse {}", indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建方式3
     */
    public void add3(Long id){
        try {
            IndexRequest request=new IndexRequest("test_user");
            //手动设置id
            request.id(id.toString());

            //构建文档
            request.source("id", 3L, "name", "李六", "sex", "男");

            //发送请求
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("indexResponse {}", indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 构建方式3
     */
    public void addVersion(Long id){
        try {
            IndexRequest request=new IndexRequest("test_user");
            //手动设置id
            request.id(id.toString());

            //构建文档
            request.source("id", 4L, "name", "路七", "sex", "男");
            request.version(10);
            request.versionType(VersionType.EXTERNAL);

            //发送请求
            IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("indexResponse {}", indexResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
