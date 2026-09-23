package com.example.demo.api;

import com.example.demo.exception.InvalidLetterException;
import com.example.demo.exception.PersonNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.service.PersonService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Person> addPerson(@Valid @NotNull
                          @RequestBody Person person) {
        Person savedPerson = personService.addPerson(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
    }

    // Gestisce la GET api/v1/person
    @GetMapping
    public List<Person> getAllPeople() {
        return personService.getAllPeople();
    }
    // Gestisce la GET api/v1/person/{id}
    @GetMapping(path="{id}")
    public Person getPersonById(@PathVariable("id") UUID id){   // Legge l'Id dal percorso e lo converte in UUID
        return personService.getPersonById(id);
    }
    // Gestisce la GET api/v1/person/{nome}
    @GetMapping(params = "name")
    public List<Person> findByName(@RequestParam("name") String name){
        return personService.findByName(name);
    }

    // Gestisce il GET api/v1/person per la Ricerca con un carattere
    // ==== ESERCIZIO 5 ====
    @GetMapping(params = "letter", produces ="text/plain")          // (params = "letter)   path = "/per-lettera"
    public String getNamesByChar(
            @RequestParam(value = "letter",  required = false)
            @Pattern(regexp = "[a-zA-Z]",
                    message = "Inserire una sola lettera")
            String letter){
        return personService.getNamesByChar(letter);
    }

/*
    // ====== ESERCIZIO 6 ======
    @GetMapping(path = "/test-exception", produces ="text/plain")
    public String getNamesByChar(
            @RequestParam(value = "letter",  required = false) String letter){
        //try {
            return personService.getNamesByChar(letter);
        /*}catch(ResponseStatusException e) {
            System.out.println("Eccezione catturata: " + e.getReason());
            throw e;
        }*/
        // Per il punto 4 si può togliere il blocco try/catch. Se voglio lasciare il blocco try/catch, posso scrivere:
        // catch(InvalidLetterException | NoNamesFoundException) {......}
 //   }


    // Gestisce il DELETE api/v1/person/{id}
    @DeleteMapping(path = "{id}")
    public ResponseEntity<Person> deletePersonById(@PathVariable("id") UUID id) {
        personService.deletePersonById(id);
        return ResponseEntity.noContent().build();
    }

    // Gestisce il PUT api/v1/person/{id}
    @PutMapping(path = "{id}")
    public ResponseEntity<Person> updatePersonById(@PathVariable("id") UUID id, @Valid @NotNull
                                 @RequestBody Person personToUpdate) {  // Legge il JSON con i dati da aggiornare
        return ResponseEntity.ok(personService.updatePersonById(id, personToUpdate));
    }

}
