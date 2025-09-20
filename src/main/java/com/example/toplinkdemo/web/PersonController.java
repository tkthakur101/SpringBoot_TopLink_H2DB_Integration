package com.example.toplinkdemo.web;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.example.toplinkdemo.repo.PersonRepository;
import com.example.toplinkdemo.entity.Person;

@RestController
@RequestMapping("/api/persons")
public class PersonController {
    private final PersonRepository repo;
    public PersonController(PersonRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Person> all() { return repo.findAll(); }

    @PostMapping
    public Person create(@RequestBody Person p) { return repo.save(p); }
}
