package com.evaluationtask.cardissuer.dto;

import com.evaluationtask.cardissuer.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

  private String firstName;
  private String lastName;
  private String oib;
  private Status status;
}
