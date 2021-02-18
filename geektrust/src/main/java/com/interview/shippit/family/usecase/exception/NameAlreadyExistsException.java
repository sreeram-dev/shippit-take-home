package com.interview.shippit.family.usecase.exception;

public class NameAlreadyExistsException extends RuntimeException {
    public NameAlreadyExistsException() {
        super("NAME_ALREADY_EXISTS");
    }
}
