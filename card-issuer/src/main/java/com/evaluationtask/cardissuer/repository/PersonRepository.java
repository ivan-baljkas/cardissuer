package com.evaluationtask.cardissuer.repository;

import com.evaluationtask.cardissuer.model.Person;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

  Optional<Person> findByOib(String OIB);

  boolean existsByOib(String OIB);
}
