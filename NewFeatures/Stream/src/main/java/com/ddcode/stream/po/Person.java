package com.ddcode.stream.po;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Person {
    private Integer age;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(age, person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(age);
    }
}
