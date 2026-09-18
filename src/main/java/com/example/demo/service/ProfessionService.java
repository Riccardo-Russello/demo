package com.example.demo.service;

import com.example.demo.api.ProfessionByPersonResponse;
import com.example.demo.exception.PersonNotFoundException;
import com.example.demo.exception.ProfessionNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;

import org.springframework.stereotype.Service;

@Service
public class ProfessionService {
    private final PersonRepository personRepository;

    public ProfessionService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public ProfessionByPersonResponse getProfessionByPerson(String name,
                                                            String surname){
        Person person = personRepository
                .findFirstByNameIgnoreCaseAndSurnameIgnoreCase(name, surname)
                .orElseThrow(()-> new PersonNotFoundException("Persona non trovata"));
        if(person.getProfession() == null){
            throw new ProfessionNotFoundException("Nessuna professione associata alla persona selezionata");
        }
        return new ProfessionByPersonResponse(
                person.getProfession().getJobName()
        );
    }

    /*  METODO CON CATENA NULL SAFE
    public ProfessionByPersonResponse v1(String name, String surname){
        ProfessionByPersonResponse person = personRepository
                .findFirstByNameIgnoreCaseAndSurnameIgnoreCase(name, surname)
                .map(Person::getProfession)
                .map(Profession::getJobName)
                .map(jobName -> new ProfessionByPersonResponse(jobName))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Persona non trovata"
                ));
        return person;
    }
    */
}
