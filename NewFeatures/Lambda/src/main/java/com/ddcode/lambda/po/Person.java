package com.ddcode.lambda.po;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class Person {
    public String name;
    public Integer age;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return name.equals(person.name) &&
                age.equals(person.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
