package com.interview.shippit.family.usecase.exception;


/**
 * PersonNotFoundException is thrown when the relationship is not found
 *
 */
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super("PERSON_NOT_FOUND");
    }
}