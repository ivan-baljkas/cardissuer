package com.evaluationtask.cardissuer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApplicationFileException extends ResponseStatusException {

  public ApplicationFileException(HttpStatus httpStatus, String message) {
    super(httpStatus, message);
  }
}
