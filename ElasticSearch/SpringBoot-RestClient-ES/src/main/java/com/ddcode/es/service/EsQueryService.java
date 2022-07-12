package com.ddcode.es.service;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
@Slf4j
public class EsQueryService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    /**
     * 普通查询
     */
    public void get(){
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 定制查询: 将指定字段返回
     */
    public void customQuery(){
        try {
            //构建请求
            //参数1：索引名称
            //参数2：文档id
            GetRequest request = new GetRequest("book", "1");

            //方式1：返回指定的参数
            //可选参数
            String[] includes = new String[]{"name", "price"};
            FetchSourceContext fetchSourceContext = new FetchSourceContext(true, includes, null);
            //request.fetchSourceContext(fetchSourceContext);


            //方式2：除了不指定返回的其他都返回
            String[] excludes = new String[]{"description", "tags"};
            FetchSourceContext fetchSourceContextExcludes = new FetchSourceContext(true, null, excludes);
            request.fetchSourceContext(fetchSourceContextExcludes);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 异步查询
     */
    public void AsyncGet(){
        try {
            //构建请求
            //参数1：索引名称
            //参数2：文档id
            GetRequest request = new GetRequest("book", "1");

            //创建监听器
            ActionListener<GetResponse> actionListener = new ActionListener<GetResponse>() {

                //查询成功会执行的方法
                @Override
                public void onResponse(GetResponse getResponse) {
                    log.info("异步成功返回 {}", getResponse);
                }

                //查询失败会执行的方法
                @Override
                public void onFailure(Exception e) {
                    log.error("异步失败返回 ,", e);
                }
            };

            restHighLevelClient.getAsync(request, RequestOptions.DEFAULT, actionListener);
            log.info("主线程执行完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
