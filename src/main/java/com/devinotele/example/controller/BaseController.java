package com.devinotele.example.controller;

import com.devinotele.example.dto.Person;
import com.devinotele.example.dto.Response;
import com.devinotele.example.repository.RedisPersonRepository;
import com.devinotele.example.repository.dbo.PersonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.devinotele.example.utils.MapperUtils.toJsonString;

@RequestMapping("/person")
@RestController
public class BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    private RedisPersonRepository personRepository;

    public BaseController(RedisPersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping(path = "/{id}")
    public PersonData getPersonById(@PathVariable long id) {
        LOGGER.info("Get person by Id: {}", id);
        return personRepository.getPersonById(id);
    }

    @GetMapping(path = "/all")
    public List<PersonData> getPersons() {
        LOGGER.info("Get all persons");
        return personRepository.getPersons();
    }

    @GetMapping(path = "/minors")
    public List<PersonData> getMinorPersons() {
        LOGGER.info("Get minor persons");
        return personRepository.getMinorPersons();
    }

    @PostMapping
    public Response addPerson(@RequestBody Person person) {
        LOGGER.info("PersonData added: {}", toJsonString(person));
        Long id = personRepository.addPerson(person);
        LOGGER.info("Person Id: {}", id);
        return new Response(id, "success");
    }
}