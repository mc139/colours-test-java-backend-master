package com.answerdigital.colourstest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.answerdigital.colourstest.dto.PersonUpdateDTO;
import com.answerdigital.colourstest.model.Person;
import com.answerdigital.colourstest.repository.PeopleRepository;

@RestController
@RequestMapping("/People")
public class PeopleController {

    @Autowired
    private PeopleRepository peopleRespository;

    @GetMapping
    public ResponseEntity<List<Person>> getPeople() {
        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok();
        if (peopleRespository.findAll().size() != 0) {
            return responseBuilder.body(peopleRespository.findAll());
        } else {
            return responseBuilder.body(Collections.emptyList());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable("id") long id) {
        if (peopleRespository.existsById(id)) {
            return new ResponseEntity<>(peopleRespository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody PersonUpdateDTO personUpdate) {
        if (peopleRespository.existsById(id)) {
            Person existingPerson = peopleRespository.getOne(id);
            existingPerson.setAuthorised(personUpdate.isAuthorised());
            existingPerson.setColours(personUpdate.getColours());
            existingPerson.setEnabled(personUpdate.isEnabled());
            peopleRespository.save(existingPerson);
            return new ResponseEntity<>(peopleRespository.getOne(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping("/create")
    public Person createPerson(@RequestBody Person person) {
        return peopleRespository.save(person);
    }

}
