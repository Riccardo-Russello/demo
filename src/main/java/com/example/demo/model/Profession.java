package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity                         // entità persistente
@Table(name = "profession")     // taablla presente nel DB
public class Profession {
    @Id
    @JdbcTypeCode(Types.CHAR)
    @Column(length = 36, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "job_name", nullable = false, length = 255, unique = true)   // deve essere univoca
    private String jobName;

    protected Profession() {}
    public Profession(@JsonProperty("id") UUID id,
                      @JsonProperty("jobName") String jobName) {
        this.id = id;
        this.jobName = jobName;
    }

    // === GETTER ===
    public UUID getId() {
            return id;
    }
    public String getJobName() {
        return jobName;
    }

    // === SETTER ===
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
