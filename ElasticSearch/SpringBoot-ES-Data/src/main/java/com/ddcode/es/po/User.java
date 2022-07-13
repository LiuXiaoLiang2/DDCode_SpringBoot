package com.ddcode.es.po;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@Document(indexName = "user-index", createIndex = false)
public class User {

    @Id
    private String  id;
    @Field
    private String name;
    @Field
    private String sex;
}
