package com.interview.shippit.controller;


import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.usecase.AddFamilyMember;
import com.interview.shippit.family.usecase.GetRelationship;
import com.interview.shippit.family.usecase.exception.NameAlreadyExistsException;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;

import java.util.List;

public class FamilyController {
    private final AddFamilyMember addFamilyMember;
    private final GetRelationship getRelationship;

    public FamilyController(final AddFamilyMember addFamilyMember, final GetRelationship getRelationship) {
        this.addFamilyMember = addFamilyMember;
        this.getRelationship = getRelationship;
    }

    public String addFamilyMember(final String name, final String gender, final String motherName) {
        try {
            this.addFamilyMember.create(name, gender, motherName);
        } catch (PersonNotFoundException ex) {
            return "PERSON_NOT_FOUND";
        } catch (NameAlreadyExistsException | IllegalArgumentException ex) {
            return "CHILD_ADDITION_FAILED";
        }

        return "CHILD_ADDED";
    }

    public String getRelationship(final String name, final String relationship) {
        try {
            List<FamilyMember> members = this.getRelationship.getRelationshipToFamilyMember(name, relationship);
            if (members.isEmpty()) {
                return "NONE";
            }

            return members.stream()
                    .map(FamilyMember::getName)
                    .reduce(" ", String::concat);

        } catch (PersonNotFoundException ex) {
            return ex.getMessage();
        }
    }

    public void createNoParentFamilyMember(final String name, final String gender) {
        this.addFamilyMember.createNoParentFamilyMember(name, gender);
    }

    public void addIndividualPartner(final String name, final String gender, final String partnerName) {
        this.addFamilyMember.createIndividualPartner(name, gender, partnerName);
    }
}