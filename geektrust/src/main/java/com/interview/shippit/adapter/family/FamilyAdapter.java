package com.interview.shippit.adapter.family;


import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.usecase.exception.FamilyRelationNotFoundException;
import com.interview.shippit.family.usecase.exception.NameAlreadyExistsException;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.port.AddFamilyService;
import com.interview.shippit.family.usecase.port.GetRelationshipService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Output port for the familymember usecases
 * Data Transfer Objects can be defined here so that persistence, network layers
 * need not know the domain entity structure
 *
 * This layer is mostly concerned with application rules.
 */
public class FamilyAdapter {

    private final AddFamilyService familyService;
    private final GetRelationshipService relationshipService;

    public FamilyAdapter(final AddFamilyService familyService, final GetRelationshipService relationshipService) {
        this.familyService = familyService;
        this.relationshipService = relationshipService;
    }

    public String addFamilyMember(final String name, final String gender, final String motherName) {
        try {
            this.familyService.create(name, gender, motherName);
        } catch (PersonNotFoundException ex) {
            return "PERSON_NOT_FOUND";
        } catch (NameAlreadyExistsException | IllegalArgumentException ex) {
            return "CHILD_ADDITION_FAILED";
        }

        return "CHILD_ADDED";
    }

    public String getRelationship(final String name, final String relationship) {
        try {
            List<FamilyMember> members = this.relationshipService
                .getRelationshipToFamilyMember(name, relationship);

            if (members.isEmpty()) {
                return "NONE";
            }

            return members.stream()
                    .map(FamilyMember::getName)
                    .collect(Collectors.joining(" "));

        } catch (PersonNotFoundException ex) {
            return ex.getMessage();
        } catch (FamilyRelationNotFoundException ex) {
            return "NONE";
        }
    }

    public void createNoParentFamilyMember(final String name, final String gender) {
        this.familyService.createNoParentFamilyMember(name, gender);
    }

    public void addIndividualPartner(final String name, final String gender, final String partnerName) {
        this.familyService.createIndividualPartner(name, gender, partnerName);
    }
}
