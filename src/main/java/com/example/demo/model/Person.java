package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;
import java.sql.Types;
import java.util.UUID;

@Entity                         // Hiberante gestisce la classe come entità persistente
@Table(name="person")           // Tabella corrispondente nel DB
public class Person {
    @Id                                     // il campo id è la primary key
    @JdbcTypeCode(Types.CHAR)               // memorizza come sql testuale char
    @Column(length=36, nullable = false)    // colonna non nulla, lunghezza 36 caratteri
    private UUID id;

    @NotBlank                                   //deve contenere almeno un carattere
    @Column(nullable = false, length = 255)
    private String name;

    @NotBlank
    @Column(nullable = false, length = 255)
    private String surname;

    @ManyToOne                              // Più persone possono riferirsi alla stessa persona
    @JoinColumn(name = "profession_id")     // contiene la colonna di collegamento profession_id
    private Profession profession;

    protected Person() {}       // costruttore richiesto da JPA

    public Person(@JsonProperty("id") UUID id,                  // dati associati alle proprietà json
                  @JsonProperty("name") String name,
                  @JsonProperty("surname") String surname){
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    // === GETTER ===
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public Profession getProfession() {
        return profession;
    }

    // === SETTER ===
    public void setProfession(Profession profession) {
        this.profession = profession;
    }
}
