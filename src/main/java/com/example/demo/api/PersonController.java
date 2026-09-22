package com.example.demo.api;

import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("api/v1/person")        // Percorso comune alle operazioni di questo controller
@RestController                         // Registra il controller e scrive i risultati nel corpo della risposta http
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    // Gestisce la POST
    @PostMapping
    public void addPerson(@Valid @NotNull
                          @RequestBody Person person) {
        personService.addPerson(person);
    }

    // Gestisce la GET api/v1/person
    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }
    // Gestisce la GET api/v1/person/{id}
    @GetMapping(path="{id}")
    public Person getPersonById(@PathVariable("id") UUID id){   // Legge l'Id dal percorso e lo converte in UUID
        return personService.getPersonById(id)
                .orElse(null);
    }
    // Gestisce la GET api/v1/person/{nome}
    @GetMapping(params = "name")
    public List<Person> findByName(@RequestParam("name") String name){
        return personService.findByName(name);
    }

    // Gestisce il GET api/v1/person
    @GetMapping(params = "letter", produces ="text/plain")          // (params = "letter)   path = "/per-lettera"
    public String getNamesByChar(
            @RequestParam(value = "letter",  required = false) String letter){
        return personService.getNamesByChar(letter);
    }

    // Gestisce il DELETE api/v1/person/{id}
    @DeleteMapping(path = "{id}")
    public void deletePersonById(@PathVariable("id") UUID id) {
        personService.deletePersonById(id);
    }

    // Gestisce il PUT api/v1/person/{id}
    @PutMapping(path = "{id}")
    public void updatePersonById(@PathVariable("id") UUID id, @Valid @NotNull
                                 @RequestBody Person personToUpdate) {  // Legge il JSON con i dati da aggiornare
        personService.updatePersonById(id, personToUpdate);
    }

}
