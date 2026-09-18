package com.example.demo.repository;
import com.example.demo.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {
    List<Person> findByName(String name);
    List<Person> findByNameContainingIgnoreCase(String name);
    Optional<Person> findFirstByNameIgnoreCaseAndSurnameIgnoreCase(
            String name,
            String surname);
}
