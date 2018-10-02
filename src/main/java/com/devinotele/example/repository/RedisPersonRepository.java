package com.devinotele.example.repository;

import com.devinotele.example.dto.Person;
import com.devinotele.example.repository.dbo.PersonData;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

import static com.devinotele.example.repository.dbo.PersonData.toPersonData;

@Repository
public class RedisPersonRepository implements PersonRepository {

    private final String PERSON_MAP = "ex_person:1:person:map";
    private final String MINOR_PERSON_MAP = "ex_person:1:minor:map";

    private final RedisTemplate<String, PersonData> personRedisTemplate;
    private final RedisScript<List<PersonData>> personRedisScript;

    public RedisPersonRepository(RedisTemplate<String, PersonData> personRedisTemplate,
                                 RedisScript<List<PersonData>> personRedisScript) {
        this.personRedisTemplate = personRedisTemplate;
        this.personRedisScript = personRedisScript;
    }

    @Override
    public List<PersonData> getMinorPersons() {
        personRedisTemplate.opsForSet().members(MINOR_PERSON_MAP)
                .addAll(getAllPersons()
                        .stream()
                        .filter(person -> person.age < 18)
                        .collect(Collectors.toList()));
        return new ArrayList<>(personRedisTemplate.opsForSet().members(MINOR_PERSON_MAP));
    }

    @Override
    public long addPerson(Person person) {
        long id = Math.abs(new Random().nextLong());
        personRedisTemplate.opsForSet().add(PERSON_MAP, toPersonData(id, person));
        return id;
    }

    @Override
    public PersonData getPersonById(long id) {
        return personRedisTemplate.opsForSet().members(PERSON_MAP)
                .stream()
                .filter(p -> p.id == id)
                .findAny()
                .get();
    }

    @Override
    public List<PersonData> getPersons() {
        return getAllPersons();
    }

    private List<PersonData> getAllPersons() {
        return new ArrayList<>(personRedisTemplate.opsForSet().members(PERSON_MAP));
    }

    @Deprecated
    private void swap() {
        personRedisTemplate.execute(
                personRedisScript,
                Arrays.asList(PERSON_MAP, MINOR_PERSON_MAP));
    }
}