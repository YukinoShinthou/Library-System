package com.example.demo.project2.Services;


import com.example.demo.project2.Models.Person;
import com.example.demo.project2.Repositories.PeopleRepositories;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
public class PeopleService {

    PeopleRepositories peopleRepositories;
    @Autowired
    public PeopleService(PeopleRepositories peopleRepositories) {
        this.peopleRepositories = peopleRepositories;
    }

    public List<Person> findAll(){
        return peopleRepositories.findAll();
    }

    public Person findOnePerson(int id){
        Optional<Person> foundPerson = peopleRepositories.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Person person){
        person.setCreatedAt(new Date());

        peopleRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepositories.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepositories.deleteById(id);
    }

    @Transactional
    public List<Person> findByName(String name){
        return peopleRepositories.findByName(name);
    }


}
