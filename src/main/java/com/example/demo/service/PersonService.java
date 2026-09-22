package com.example.demo.service;
import com.example.demo.model.Person;
import com.example.demo.repository.PersonRepository;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service            // Spring crea e gestisce un'istanza di questa classe
public class PersonService {
    private final PersonRepository personRepository;                    //private final PersonDao personDao;

    // Costruttore
    public PersonService(PersonRepository personRepository) {           //@Autowired - nel costruttore -> @Qualifier("postgres") PersonDao personDao
        this.personRepository = personRepository;
    }

    // Riceve la persona da creare e restituisce un indice numerico dopo averla AGGIUNTA al  DB
    public int addPerson(Person person) {
        Person personToSave = new Person(UUID.randomUUID(), person.getName(), person.getSurname());
        personRepository.save(personToSave);
        return 1;
    }
    //Restituisce tutte le persone
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    // Restituisce la persona cercata per nome
    public List<Person> findByName(String name){
        return personRepository.findByName(name);
    }

    // Restituisce una persona Opstional cercata per id
    public Optional<Person> getPersonById(UUID id) {
        return personRepository.findById(id);
    }

    // ELIMINA una persona per Id
    public int deletePersonById(UUID id) {
        if(!personRepository.existsById(id)){       // se NON esiste la persona con Id, ritorna
            return 0;
        }
        personRepository.deleteById(id);       // altrimenti ELIMINA la persona
        return 1;                              // e ritorna 1
    }

    // AGGIORNA il nome di una persona cercando per Id
    public int updatePersonById(UUID id, Person newPerson) {
        if(!personRepository.existsById(id)){   // Se NON esiste, ritorna 0
            return 0;
        }
        Person updatedPerson = new Person(id, newPerson.getName(), newPerson.getSurname()); //crea il nuovo stato mantenendo lo steso id
        updatedPerson.setProfession(newPerson.getProfession());                             // copia la professione
        personRepository.save(updatedPerson);                                               // salva lo stato aggiornato
        return 1;
    }

    // Metodo per la validazione della lettera
    private boolean isValidLetter(String letter){
        return letter != null && letter.matches("[a-zA-Z]");
    }
    public String getNamesByChar(String letter){
        if(!isValidLetter(letter)){
            return "Invalid Input";     // se l'input non è valido ritornerà la stringa "Invalid Input
        }
        List<Person> people = personRepository.findByNameStartingWithIgnoreCase(letter);    // Crea una lista di persone che iniziano con la lettera, case insensitive
        if(people.isEmpty()){
            return "Resource Not Found";    // se la lista di persone è vuota, ritornerà la stringa "Resource Not Found"
        }
        return people.stream()                                   // Trasforma la lista in uno stream
                .map(Person::getName)                            // Prende il nome
               // .filter(p -> p.length() > 8)                    Esercizio: applico un filtro al nome. deve essere maggiore di un certo valore
                .collect(Collectors.joining(", "));     // Unisce i nomi separati da ", "

    }
}
