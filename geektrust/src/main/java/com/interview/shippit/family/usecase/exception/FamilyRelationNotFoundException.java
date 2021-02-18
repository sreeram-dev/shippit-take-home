package com.interview.shippit.family.usecase.exception;

public class FamilyRelationNotFoundException extends RuntimeException {

    public FamilyRelationNotFoundException() {
        super("RELATION_NOT_FOUND");
    }
}
