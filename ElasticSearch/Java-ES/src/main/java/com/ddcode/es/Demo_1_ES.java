package com.ddcode.es;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

@Slf4j(topic = "c.es")
public class Demo_1_ES {

    public static void main(String[] args) throws IOException {
        //链接客户端
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        //构建请求
        //参数1：索引名称
        //参数2：文档id
        GetRequest request = new GetRequest("book", "1");
        // 执行
        GetResponse getResponse = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        log.info("getResponse {}", getResponse);
        log.info("isExists {}", getResponse.isExists());
        //获取结果
        if(getResponse.isExists()){
            long version = getResponse.getVersion();
            log.info("version {}", version);
            String sourceAsString = getResponse.getSourceAsString();//检索文档(String形式)
            log.info("返回结果 {}" ,sourceAsString);
        }
    }
}
