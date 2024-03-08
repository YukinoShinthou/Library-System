package com.example.demo.project2.Repositories;


import com.example.demo.project2.Models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PeopleRepositories extends JpaRepository<Person,Integer> {

    List<Person> findByName(String name);
}
