package com.ddcode.es.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "tvs", shards = 2, replicas = 1, refreshInterval = "30s", createIndex = true)
public class Television {

    @Id
    private String id;

    @Field(type = FieldType.Long)
    private Long price;

    @Field(type = FieldType.Keyword)
    private String color;

    @Field(type = FieldType.Keyword)
    private String brand;

    @Field(type = FieldType.Date)
    private String sold_date;
}
