package com.evaluationtask.cardissuer.controller;

import static com.evaluationtask.cardissuer.infrastructure.Constants.API_PERSONS;
import static com.evaluationtask.cardissuer.infrastructure.Constants.API_PERSON_BY_OIB;
import static com.evaluationtask.cardissuer.infrastructure.Constants.API_APPLY_FOR_CARD_ISSUING;

import com.evaluationtask.cardissuer.dto.PersonDto;
import com.evaluationtask.cardissuer.mapper.PersonMapper;
import com.evaluationtask.cardissuer.model.Person;
import com.evaluationtask.cardissuer.service.PersonService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PersonController {

  private final PersonService personService;
  private final PersonMapper personMapper;

  @GetMapping(API_PERSON_BY_OIB)
  public ResponseEntity<PersonDto> getPersonByOib(@PathVariable String oib) {
    PersonDto personDto =
        personMapper.mapToDto(personService.getPersonByOib(oib));
    return ResponseEntity.ok(personDto);
  }

  @PostMapping(API_PERSONS)
  public ResponseEntity<PersonDto> savePerson(
      @RequestBody PersonDto personDto) {
    Person person = personService.savePerson(personMapper.mapFromDto(personDto));
    return ResponseEntity.ok(personMapper.mapToDto(person));
  }

  @DeleteMapping(API_PERSON_BY_OIB)
  public ResponseEntity<PersonDto> deletePersonByOib(@PathVariable String oib) throws IOException {
    PersonDto personDto =
        personMapper.mapToDto(personService.deletePersonByOib(oib));
    return ResponseEntity.ok(personDto);
  }

  @PostMapping(API_APPLY_FOR_CARD_ISSUING)
  public ResponseEntity<PersonDto> applyForCardIssuing(
      @PathVariable String oib) throws IOException {
    PersonDto personDto = personMapper.mapToDto(
        personService.applyForCardIssuing(oib)
    );
    return ResponseEntity.ok(personDto);
  }
}
