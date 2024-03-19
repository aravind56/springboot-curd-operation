package com.curdexample.springbootcurdexample.controller;


import org.springframework.web.bind.annotation.*;

import com.curdexample.springbootcurdexample.model.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final List<Person> persons = new ArrayList<>();
    private long nextId = 1;

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        person.setId(nextId++);
        persons.add(person);
        return person;
    }

    @GetMapping("/{id}")
    public Person getPersonById(@PathVariable long id) {
        Optional<Person> optionalPerson = persons.stream()
                                                .filter(p -> p.getId() == id)
                                                .findFirst();
        return optionalPerson.orElse(null);
    }

    @GetMapping
    public List<Person> getAllPersons() {
        return persons;
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable long id, @RequestBody Person updatedPerson) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getId() == id) {
                updatedPerson.setId(id);
                persons.set(i, updatedPerson);
                return updatedPerson;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable long id) {
        for (int i = 0; i < persons.size(); i++) {
            Person person = persons.get(i);
            if (person.getId() == id) {
                persons.remove(i);
                return "Person with ID " + id + " deleted successfully.";
            }
        }
        return "Person with ID " + id + " not found.";
    }
}
