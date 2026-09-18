package com.example.demo.service;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*import com.example.demo.dao.PersonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;*/

@Service
public class PersonService {
    private final PersonRepository personRepository;                    //private final PersonDao personDao;

    public PersonService(PersonRepository personRepository) {           //@Autowired - nel costruttore -> @Qualifier("postgres") PersonDao personDao
        this.personRepository = personRepository;
    }

    public int addPerson(Person person) {
        Person personToSave = new Person(UUID.randomUUID(), person.getName(), person.getSurname());
        personRepository.save(personToSave);
        return 1;
    }

    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }
    public List<Person> findByName(String name){
        return personRepository.findByName(name);
    }

    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

    public int deletePersonById(UUID id) {
        if(!personRepository.existsById(id)){
            return 0;
        }
        personRepository.deleteById(id);
        return 1;
    }

    public int updatePersonById(UUID id, Person newPerson) {
        if(!personRepository.existsById(id)){
            return 0;
        }
        Person updatedPerson = new Person(id, newPerson.getName(), newPerson.getSurname());
        updatedPerson.setProfession(newPerson.getProfession());
        personRepository.save(updatedPerson);
        return 1;
    }
}
