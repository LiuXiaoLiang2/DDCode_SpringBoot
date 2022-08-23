package com.ddcode.es.po;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

@Data
@Builder
@Document(indexName = "book", createIndex = false)
public class Book {

    @Id
    private Integer id;
    @Field
    private String name;
    @Field
    private String desc;
    @Field
    private Double price;
}
