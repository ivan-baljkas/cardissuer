package com.evaluationtask.cardissuer.mapper;

import com.evaluationtask.cardissuer.dto.PersonDto;
import com.evaluationtask.cardissuer.model.Person;

public interface PersonMapper {

  PersonDto mapToDto(Person person);

  Person mapFromDto(PersonDto personDto);
}
