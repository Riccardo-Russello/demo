package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.JdbcTypeCode;

import java.sql.Types;
import java.util.UUID;

@Entity
@Table(name = "profession")
public class Profession {
    @Id
    @JdbcTypeCode(Types.CHAR)
    @Column(length = 36, nullable = false)
    private UUID id;

    @Column(name = "job_name", nullable = false, length = 255, unique = true)
    private String jobName;

    protected Profession() {}
    public Profession(@JsonProperty("id") UUID id,
                      @JsonProperty("jobName") String jobName) {
        this.id = id;
        this.jobName = jobName;
    }

    public UUID getId() {
            return id;
    }
    public String getJobName() {
        return jobName;
    }
}
