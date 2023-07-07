package com.evaluationtask.cardissuer.mapper.impl;

import com.evaluationtask.cardissuer.dto.PersonDto;
import com.evaluationtask.cardissuer.mapper.PersonMapper;
import com.evaluationtask.cardissuer.model.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper {

  @Override
  public PersonDto mapToDto(Person person) {
    PersonDto personDto = new PersonDto();
    personDto.setFirstName(person.getFirstName());
    personDto.setLastName(person.getLastName());
    personDto.setOib(person.getOib());
    personDto.setStatus(person.getStatus());
    return personDto;
  }

  @Override
  public Person mapFromDto(PersonDto personDto) {
    Person person = new Person();
    person.setFirstName(personDto.getFirstName());
    person.setLastName(personDto.getLastName());
    person.setOib(personDto.getOib());
    person.setStatus(personDto.getStatus());
    return person;
  }
}
