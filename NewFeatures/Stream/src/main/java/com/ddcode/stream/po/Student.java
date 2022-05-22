package com.ddcode.stream.po;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Student {

    private String name;
    private Integer age;
    private Integer score;

    public Student(String name, Integer age, Integer score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }
}
