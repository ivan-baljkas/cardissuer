package com.evaluationtask.cardissuer.service.impl;

import static com.evaluationtask.cardissuer.infrastructure.Constants.EXISTING_OIB_NOT_SAVED;
import static com.evaluationtask.cardissuer.infrastructure.Constants.PERSON_NOT_DELETED;
import static com.evaluationtask.cardissuer.infrastructure.Constants.PERSON_NOT_FOUND;
import static com.evaluationtask.cardissuer.infrastructure.Constants.PERSON_NOT_SAVED;

import com.evaluationtask.cardissuer.exceptions.PersonException;
import com.evaluationtask.cardissuer.model.ApplicationFile;
import com.evaluationtask.cardissuer.model.Person;
import com.evaluationtask.cardissuer.model.Status;
import com.evaluationtask.cardissuer.repository.ApplicationFileRepository;
import com.evaluationtask.cardissuer.repository.PersonRepository;
import com.evaluationtask.cardissuer.service.PersonService;
import com.evaluationtask.cardissuer.validator.PersonValidator;
import java.io.IOException;
import java.time.Instant;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

  private final PersonValidator personValidator;
  private final PersonRepository personRepository;
  private final ApplicationFileRepository applicationFileRepository;

  @Override
  public Person getPersonByOib(String OIB) {
    Optional<Person> person = personRepository.findByOib(OIB);

    if (person.isPresent()) {
      return person.get();
    } else {
      throw new PersonException(HttpStatus.BAD_REQUEST, PERSON_NOT_FOUND);
    }
  }

  @Override
  public Person savePerson(Person person) {
    personValidator.validatePerson(person);

    if (personRepository.existsByOib(person.getOib())) {
      throw new PersonException(HttpStatus.BAD_REQUEST, EXISTING_OIB_NOT_SAVED);
    }

    person.setStatus(Status.INACTIVE);
    try {
      return personRepository.save(person);
    } catch (IllegalArgumentException | OptimisticLockingFailureException ex) {
      throw new PersonException(HttpStatus.INTERNAL_SERVER_ERROR, PERSON_NOT_SAVED, ex);
    }
  }

  @Override
  public Person applyForCardIssuing(String OIB) throws IOException {
    Person person = getPersonByOib(OIB);
    applicationFileRepository.createApplicationFile(buildApplicationFile(person), person.getStatus());

    if (person.getStatus() == Status.INACTIVE) {
      person.setStatus(Status.ACTIVE);
      personRepository.save(person);
    }
    return person;
  }

  @Override
  public Person deletePersonByOib(String oib) throws IOException {
    Person person = getPersonByOib(oib);
    try {
      applicationFileRepository.deactivateTheLastApplicationFile(oib, person.getStatus());
      personRepository.delete(person);
    } catch (RuntimeException ex) {
      throw new PersonException(HttpStatus.INTERNAL_SERVER_ERROR, PERSON_NOT_DELETED, ex);
    }
    return person;
  }

  private ApplicationFile buildApplicationFile(Person person) {

    ApplicationFile applicationFile = new ApplicationFile();
    applicationFile.setFirstName(person.getFirstName());
    applicationFile.setLastName(person.getLastName());
    applicationFile.setOIB(person.getOib());
    applicationFile.setStatus(Status.ACTIVE);
    applicationFile.setTimestamp(Instant.now());

    return applicationFile;
  }
}
