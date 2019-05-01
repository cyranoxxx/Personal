package com.golhan.person.service;

import com.golhan.person.entity.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    void savePerson(Person person);
    void updatePerson(Person person);
    void deletePerson(Long id);
    Optional<Person> findPersonid(Long id);
    List<Person>findAllPerson();
}
