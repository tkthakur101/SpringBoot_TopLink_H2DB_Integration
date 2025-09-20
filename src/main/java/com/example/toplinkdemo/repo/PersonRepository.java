package com.example.toplinkdemo.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.toplinkdemo.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {}
