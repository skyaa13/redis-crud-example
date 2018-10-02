package com.devinotele.example.repository.dbo;

import com.devinotele.example.dto.Person;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PersonData {
    public final long id;
    public final String name;
    public final int age;
    public final String sex;

    public PersonData(
            @JsonProperty("id") long id,
            @JsonProperty("name") String name,
            @JsonProperty("age") int age,
            @JsonProperty("sex") String sex) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public static PersonData toPersonData(long id, Person person) {
        return new PersonData(
                id,
                person.name,
                person.age,
                person.sex);
    }
}
