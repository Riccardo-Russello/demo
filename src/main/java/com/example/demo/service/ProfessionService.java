package com.example.demo.service;

import com.example.demo.api.ProfessionByPersonResponse;
import com.example.demo.api.ProfessionRequest;
import com.example.demo.exception.PersonNotFoundException;
import com.example.demo.exception.ProfessionAlreadyExistsException;
import com.example.demo.exception.ProfessionNotFoundException;
import com.example.demo.model.Person;
import com.example.demo.model.Profession;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ProfessionRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service            // Spring crea e gestisce questo service
public class ProfessionService {
    private final PersonRepository personRepository;
    private final ProfessionRepository professionRepository;

    public List<Profession> getAllProfessions() {
        return professionRepository.findAll();
    }

    public ProfessionService(PersonRepository personRepository, ProfessionRepository professionRepository) {
        this.personRepository = personRepository;
        this.professionRepository = professionRepository;
    }

    // GET di una profession da nome e cognome di una persona
    public ProfessionByPersonResponse getProfessionByPerson(String name,
                                                            String surname){
        Person person = personRepository
                .findFirstByNameIgnoreCaseAndSurnameIgnoreCase(name, surname)
                .orElseThrow(()-> new PersonNotFoundException("Persona non trovata"));  // lancia l'eccezione personalizzata
        if(person.getProfession() == null){
            throw new ProfessionNotFoundException("Nessuna professione associata alla persona selezionata");    // lancia l'eccezione personalizzata
        }
        return new ProfessionByPersonResponse(
                person.getProfession().getJobName()         // Ritorna il nome della professione
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

    // Metodo CREATE per profession
    public Profession createProfession(ProfessionRequest request){
        if(professionRepository.existsByJobNameIgnoreCase(request.jobName())){
            throw new ProfessionAlreadyExistsException("Professione già esistente");
        }
        Profession profession = new Profession(UUID.randomUUID(), request.jobName());
        return professionRepository.save(profession);
    }

    // Metodo DELETE per profession
    @Transactional          // Esegue il metodo all'interno di una transazione
    public void deleteProfession(UUID id){
        Profession profession = professionRepository.findById(id)
                .orElseThrow(() -> new ProfessionNotFoundException("Professione non trovata"));
        if(personRepository.existsByProfessionId(id)){  // Controlla se una persona ha questa professione
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Impossibile eliminare la professione : è associata a una o più persone");
        }
        professionRepository.delete(profession);
    }

    // Metodo UPDATE per profession
    @Transactional
    public Profession updateProfessionName(UUID id, ProfessionRequest request){
        Profession profession = professionRepository.findById(id)
                .orElseThrow(() -> new ProfessionNotFoundException("Professione non trovata"));
        if(!profession.getJobName().equalsIgnoreCase(request.jobName())     // Controlla che il nuovo nome sia diverso dal precedente
        && professionRepository.existsByJobNameIgnoreCase(request.jobName())){  // .. e che non sia gia presente
            throw new ProfessionAlreadyExistsException("Professione con lo stesso nome già esistente");
        }
        profession.setJobName(request.jobName());       // modifica il nome dell'entità gestita da Hibernate
        return profession;
    }
}
