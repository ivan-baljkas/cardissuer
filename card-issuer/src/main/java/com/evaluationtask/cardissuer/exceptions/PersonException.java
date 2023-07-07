package com.evaluationtask.cardissuer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class PersonException extends ResponseStatusException {

  public PersonException(HttpStatus httpStatus, String reason, Exception e) {
    super(httpStatus, reason, e);
  }

  public PersonException(HttpStatus httpStatus, String message) {
    super(httpStatus, message);
  }
}
