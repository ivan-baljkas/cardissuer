package com.evaluationtask.cardissuer.validator.impl;

import static com.evaluationtask.cardissuer.infrastructure.Constants.OIB_NOT_HAVING_CORRECT_NUMBER_OF_DIGITS;
import static com.evaluationtask.cardissuer.infrastructure.Constants.PERSON_MISSING_FIRST_NAME;
import static com.evaluationtask.cardissuer.infrastructure.Constants.PERSON_MISSING_LAST_NAME;
import static com.evaluationtask.cardissuer.infrastructure.Constants.PERSON_MISSING_OIB;

import com.evaluationtask.cardissuer.exceptions.PersonException;
import com.evaluationtask.cardissuer.model.Person;
import com.evaluationtask.cardissuer.validator.PersonValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class PersonValidatorImpl implements PersonValidator {

  private final int NUMBER_OF_OIB_DIGITS = 11;

  @Override
  public void validatePerson(Person person) {
    try {
      Assert.hasText(person.getFirstName(), PERSON_MISSING_FIRST_NAME);
      Assert.hasText(person.getLastName(), PERSON_MISSING_LAST_NAME);
      Assert.hasText(person.getOib(), PERSON_MISSING_OIB);
      Assert.isTrue(
          person.getOib().length() == NUMBER_OF_OIB_DIGITS,
          OIB_NOT_HAVING_CORRECT_NUMBER_OF_DIGITS + " - " + NUMBER_OF_OIB_DIGITS);
    } catch (IllegalArgumentException e) {
      throw new PersonException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
    }

  }
}
