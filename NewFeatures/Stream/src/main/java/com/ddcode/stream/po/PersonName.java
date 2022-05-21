package com.ddcode.stream.po;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class PersonName {
    private String name;

    public PersonName(String name) {
        this.name = name;
    }
}
