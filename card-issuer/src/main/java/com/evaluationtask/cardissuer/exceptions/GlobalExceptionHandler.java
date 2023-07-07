package com.evaluationtask.cardissuer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({PersonException.class, ApplicationFileException.class})
  public ResponseEntity<ExceptionResponse> handlePersonException(PersonException e) {
    return ResponseEntity.status(e.getStatusCode()).body(
        new ExceptionResponse(e.getStatusCode(), e.getClass().getSimpleName(), e.getReason()));
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static class ExceptionResponse {

    private HttpStatusCode statusCode;
    private String error;
    private String message;

  }
}
