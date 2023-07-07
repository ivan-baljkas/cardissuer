package com.evaluationtask.cardissuer.service;

import com.evaluationtask.cardissuer.model.Person;
import java.io.IOException;

public interface PersonService {
  Person getPersonByOib(String OIB);

  Person savePerson(Person person);

  Person applyForCardIssuing(String OIB) throws IOException;

  Person deletePersonByOib(String oib) throws IOException;
}
