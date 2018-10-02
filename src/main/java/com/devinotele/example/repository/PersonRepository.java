package com.devinotele.example.repository;

import com.devinotele.example.dto.Person;
import com.devinotele.example.repository.dbo.PersonData;
import org.springframework.lang.NonNull;

import java.util.List;

public interface PersonRepository {

    long addPerson(@NonNull Person person);

    PersonData getPersonById(long id);

    List<PersonData> getPersons();

    List<PersonData> getMinorPersons();
}
