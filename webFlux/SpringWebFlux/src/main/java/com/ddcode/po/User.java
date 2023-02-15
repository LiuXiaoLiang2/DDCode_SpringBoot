package com.ddcode.po;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 映射mongo中的表
 */
@Document(collection = "user")
@Data
public class User {

    @Id
    private String id;

    private String name;

    private Integer age;
}
