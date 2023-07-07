package com.evaluationtask.cardissuer.infrastructure;

public class Constants {

  public final static String API = "/api";
  public final static String API_PERSONS = API + "/persons";
  public final static String API_PERSON_BY_OIB = API_PERSONS + "/{oib}";
  public final static String API_APPLY_FOR_CARD_ISSUING = API_PERSON_BY_OIB + "/issueCard";
  public final static String PERSON_NOT_FOUND = "Person could not be found.";
  public final static String PERSON_NOT_SAVED = "Person could not be saved.";
  public final static String PERSON_NOT_DELETED = "Person could not be deleted.";
  public final static String EXISTING_OIB_NOT_SAVED = "There is already a person with the same OIB in the database.";
  public final static String ERROR_ON_WRITING_TO_FILE = "Could not write to a file.";
  public final static String PERSON_MISSING_FIRST_NAME = "Person must have first name.";
  public final static String PERSON_MISSING_LAST_NAME = "Person must have last name.";
  public final static String PERSON_MISSING_OIB = "Person must have OIB.";
  public final static String OIB_NOT_HAVING_CORRECT_NUMBER_OF_DIGITS = "Person's OIB does not have the correct number of digits.";
}
