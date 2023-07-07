package com.evaluationtask.cardissuer.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationFile {

  private String firstName;
  private String lastName;
  private String OIB;
  private Status status;
  private Instant timestamp;
}
