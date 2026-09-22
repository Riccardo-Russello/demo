package com.example.demo.repository;

import com.example.demo.model.Profession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

// Definisce le operazioni sulla professione
public interface ProfessionRepository extends JpaRepository<Profession, UUID> {
    boolean existsByJobNameIgnoreCase(String jobName);

}
