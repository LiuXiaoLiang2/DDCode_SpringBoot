package com.ddcode.es.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class EsUpdateService {

    @Resource
    private RestHighLevelClient restHighLevelClient;


    public void update() {
        try {
            //构建请求
            UpdateRequest request = new UpdateRequest("test_user", "1");

            Map<String, Object> jsonMap = new HashMap<>();
            jsonMap.put("name", "张三_update");
            //doc方法传入的参数可以传map, 也可以传 json, 这个doc就是类似于执行update语法更新的时候 dox
            request.doc(jsonMap);

            request.timeout("1s");//超时时间

            //重试次数
            request.retryOnConflict(3);

            //设置在继续更新之前，必须激活的分片数, 我们目前的副本分片不能用, 所以注释掉
            //request.waitForActiveShards(2);
            //所有分片都是active状态，才更新
            //request.waitForActiveShards(ActiveShardCount.ALL);

            UpdateResponse updateResponse = restHighLevelClient.update(request, RequestOptions.DEFAULT);
            log.info("updateResponse {}", updateResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void delete(){
        try {
            //1构建请求
            DeleteRequest request =new DeleteRequest("test_user","4");
            //2执行
            DeleteResponse deleteResponse = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
            log.info("deleteResponse {}", deleteResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void bulk(){
        try {
            //1创建请求
            BulkRequest request = new BulkRequest();
            request.add(new IndexRequest("test_user").id("1").source("id", 1L, "name", "男1", "sex", "男"));
            request.add(new IndexRequest("test_user").id("2").source("id", 2L, "name", "男2", "sex", "男"));
            request.add(new UpdateRequest("test_user", "1").doc("name", "男1_update"));

            //2执行
            BulkResponse bulkResponse = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
            //返回的是集合对象,可以根据返回的类型判断是增加还是更新
            for (BulkItemResponse itemResponse : bulkResponse) {
                DocWriteResponse itemResponseResponse = itemResponse.getResponse();
                switch (itemResponse.getOpType()) {
                    case INDEX:
                    case CREATE:
                        IndexResponse indexResponse = (IndexResponse) itemResponseResponse;
                        indexResponse.getId();
                        log.info("indexResponse {}", indexResponse);
                        break;
                    case UPDATE:
                        UpdateResponse updateResponse = (UpdateResponse) itemResponseResponse;
                        updateResponse.getIndex();
                        log.info("updateResponse {}", updateResponse);
                        break;
                    case DELETE:
                        DeleteResponse deleteResponse = (DeleteResponse) itemResponseResponse;
                        log.info("deleteResponse {}", deleteResponse);
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
