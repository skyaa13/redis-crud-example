package com.devinotele.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Person {
    public final String name;
    public final int age;
    public final String sex;

    public Person(
            @JsonProperty("name") String name,
            @JsonProperty("age") int age,
            @JsonProperty("sex") String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }
}
