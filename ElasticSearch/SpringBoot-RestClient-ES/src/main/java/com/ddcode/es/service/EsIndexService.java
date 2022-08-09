package com.ddcode.es.service;

import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.close.CloseIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ES操作索引index
 */
@Service
@Slf4j
public class EsIndexService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引, 方式1
     */
    public void createIndex1() throws IOException {

        //创建请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("yinlan-user");
        //设置主片和副本的数量
        //setting参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas", "0"));
        //指定映射
        createIndexRequest.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"name\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"birthday\": {\n" +
                "      \"type\": \"text\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);

        //执行请求
        //操作索引的客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行创建索引库
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);

        //得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        //得到响应 指示是否在超时前为索引中的每个分片启动了所需数量的碎片副本
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        log.info("createIndexResponse {}", createIndexResponse);
        log.info("acknowledged {}", acknowledged);
        log.info("shardsAcknowledged {}", shardsAcknowledged);
    }


    /**
     * 创建索引,方式2
     * @throws IOException
     */
    public void createIndex2() throws IOException {

        //创建请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("yinlan-user");
        //设置主片和副本的数量
        //setting参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas", "0"));
        //指定映射
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        //name字段
        Map<String, Object> nameMapping = new HashMap<>();
        nameMapping.put("type", "text");
        properties.put("name", nameMapping);

        //birthday字段
        Map<String, Object> birthdayMapping = new HashMap<>();
        birthdayMapping.put("type", "text");
        properties.put("birthday", birthdayMapping);

        mapping.put("properties", properties);
        createIndexRequest.mapping(mapping);

        //执行请求
        //操作索引的客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行创建索引库
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);

        //得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        //得到响应 指示是否在超时前为索引中的每个分片启动了所需数量的碎片副本
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        log.info("createIndexResponse {}", createIndexResponse);
        log.info("acknowledged {}", acknowledged);
        log.info("shardsAcknowledged {}", shardsAcknowledged);
    }



    /**
     * 创建索引,设置其他参数
     * @throws IOException
     */
    public void createIndex3() throws IOException {

        //创建请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("yinlan-user");
        //设置主片和副本的数量
        //setting参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas", "0"));
        //指定映射
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        //name字段
        Map<String, Object> nameMapping = new HashMap<>();
        nameMapping.put("type", "text");
        properties.put("name", nameMapping);

        //birthday字段
        Map<String, Object> birthdayMapping = new HashMap<>();
        birthdayMapping.put("type", "text");
        properties.put("birthday", birthdayMapping);

        mapping.put("properties", properties);
        createIndexRequest.mapping(mapping);

        //设置别名
        createIndexRequest.alias(new Alias("yinlan-user-prod"));

        // 额外参数
        //设置超时时间
        createIndexRequest.setTimeout(TimeValue.timeValueMinutes(2));
        //设置主节点超时时间
        createIndexRequest.setMasterTimeout(TimeValue.timeValueMinutes(1));
        //在创建索引API返回响应之前等待的活动分片副本的数量，以int形式表示
        createIndexRequest.waitForActiveShards(ActiveShardCount.from(2));
        createIndexRequest.waitForActiveShards(ActiveShardCount.DEFAULT);

        //执行请求
        //操作索引的客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行创建索引库
        CreateIndexResponse createIndexResponse = indices.create(createIndexRequest, RequestOptions.DEFAULT);

        //得到响应（全部）
        boolean acknowledged = createIndexResponse.isAcknowledged();
        //得到响应 指示是否在超时前为索引中的每个分片启动了所需数量的碎片副本
        boolean shardsAcknowledged = createIndexResponse.isShardsAcknowledged();

        log.info("createIndexResponse {}", createIndexResponse);
        log.info("acknowledged {}", acknowledged);
        log.info("shardsAcknowledged {}", shardsAcknowledged);
    }


    /**
     * 创建索引,异步创建
     * @throws IOException
     */
    public void createIndexAsync() throws IOException {

        //创建请求
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("yinlan-user");
        //设置主片和副本的数量
        //setting参数
        createIndexRequest.settings(Settings.builder().put("number_of_shards","1").put("number_of_replicas", "0"));
        //指定映射
        Map<String, Object> mapping = new HashMap<>();
        Map<String, Object> properties = new HashMap<>();

        //name字段
        Map<String, Object> nameMapping = new HashMap<>();
        nameMapping.put("type", "text");
        properties.put("name", nameMapping);

        //birthday字段
        Map<String, Object> birthdayMapping = new HashMap<>();
        birthdayMapping.put("type", "text");
        properties.put("birthday", birthdayMapping);

        mapping.put("properties", properties);
        createIndexRequest.mapping(mapping);


        //监听方法
        ActionListener<CreateIndexResponse> listener =
                new ActionListener<CreateIndexResponse>() {

                    @Override
                    public void onResponse(CreateIndexResponse createIndexResponse) {
                        log.info("异步创建索引成功");
                        log.info(createIndexResponse.toString());
                    }

                    @Override
                    public void onFailure(Exception e) {
                        log.info("异步创建索引失败");
                        e.printStackTrace();
                    }
                };


        //执行请求
        //操作索引的客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行创建索引库
        indices.createAsync(createIndexRequest, RequestOptions.DEFAULT, listener);
    }


    /**
     * 删除索引对象
     * @throws IOException
     */
    public void deleteIndex() throws IOException {
        //删除索引对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("yinlan-user");
        //操作索引的客户端
        IndicesClient indices = restHighLevelClient.indices();
        //执行删除索引
        AcknowledgedResponse delete = indices.delete(deleteIndexRequest, RequestOptions.DEFAULT);
        //得到响应
        boolean acknowledged = delete.isAcknowledged();
        System.out.println(acknowledged);
    }


    /**
     * 判断索引是否存在
     */
    public void existIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("yinlan-user");
        request.local(false);//从主节点返回本地信息或检索状态
        request.humanReadable(true);//以适合人类的格式返回结果
        request.includeDefaults(false);//是否返回每个索引的所有默认设置
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }


    /**
     * 关闭索引
     * @throws IOException
     */
    public void closeIndex() throws IOException {
        CloseIndexRequest request = new CloseIndexRequest("yinlan-user");
        AcknowledgedResponse closeIndexResponse = restHighLevelClient.indices().close(request, RequestOptions.DEFAULT);
        boolean acknowledged = closeIndexResponse.isAcknowledged();
        System.out.println("!!!!!!!!!"+acknowledged);

    }


    public void openIndex() throws IOException {
        OpenIndexRequest request = new OpenIndexRequest("yinlan-user");
        OpenIndexResponse openIndexResponse = restHighLevelClient.indices().open(request, RequestOptions.DEFAULT);
        boolean acknowledged = openIndexResponse.isAcknowledged();
        System.out.println("!!!!!!!!!"+acknowledged);
    }
}
