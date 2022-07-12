package com.ddcode.es.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsConfig {

    @Value("${es.hostList}")
    private String esHostList;

    @Bean
    public RestHighLevelClient create(){
        //构建多个HttpHost
        String[] esHostListSplit = esHostList.split(",");
        HttpHost[] httpHostArray = new HttpHost[esHostListSplit.length];
        int i = 0;
        //切割配置文件中的值, 注入到httpHostArray
        for (String esHost : esHostListSplit) {
            String[] esHostSplit = esHost.split(":");
            String ip = esHostSplit[0];
            String port = esHostSplit[1];
            httpHostArray[i++] = new HttpHost(ip, Integer.parseInt(port), "http");
        }
        //直接返回RestHighLevelClient对象
        return new RestHighLevelClient(RestClient.builder(httpHostArray));
    }

}
