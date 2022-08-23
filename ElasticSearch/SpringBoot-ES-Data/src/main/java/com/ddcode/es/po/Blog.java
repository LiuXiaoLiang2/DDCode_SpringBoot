package com.ddcode.es.po;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Builder
@Document(indexName = "blog", createIndex = false)
public class Blog {

    @Id
    private Integer id;

    private String title;

    private String content;

    private String author;

    private String category;

    private String createTime;

    private String updateTime;

    private Integer status;

    private String serialNum;
}
