package com.interview.shippit.family.usecase.port;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * DAO for accessing family member and their relations, any persistence layer has to implement the repository
 * to persist data and provide support functions for business rules (get_relationship and add_child)
 *
 * Each getRelation Query has to be implemented by persistence layer on how to fetch the
 */
public interface FamilyMemberRepository {

    FamilyMember createFamilyMember(String name, Gender gender, FamilyMember mother);

    FamilyMember createNoParentFamilyMember(String name, Gender gender);

    Optional<FamilyMember> findByName(String name);

    List<FamilyMember> getSibling(FamilyMember member) throws PersonNotFoundException;

    List<FamilyMember> getPaternalUncle(FamilyMember member);

    List<FamilyMember> getPaternalAunt(FamilyMember member);

    List<FamilyMember> getMaternalUncle(FamilyMember member);

    List<FamilyMember> getMaternalAunt(FamilyMember member);

    List<FamilyMember> getSisterInLaw(FamilyMember member);

    List<FamilyMember> getBrotherInLaw(FamilyMember member);

    List<FamilyMember> getSon(FamilyMember member);

    List<FamilyMember> getDaughter(FamilyMember member);

    /**
     * Sort the list in the order people were added to database
     * @param members
     * @return Sorted list of members based on the order they were added to database
     */
    List<FamilyMember> sortOnAddedTime(List<FamilyMember> members);
}
