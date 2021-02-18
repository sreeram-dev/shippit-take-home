package com.interview.shippit.family.usecase.port;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * DAO for accessing family member and their relations
 */
public interface FamilyMemberRepository {

    public FamilyMember createFamilyMember(String name, Gender gender, FamilyMember mother);

    public FamilyMember createNoParentFamilyMember(String name, Gender gender);

    public Optional<FamilyMember> findByName(String name);

    public List<FamilyMember> getSibling(FamilyMember member) throws PersonNotFoundException;

    public List<FamilyMember> getPaternalUncle(FamilyMember member);

    public List<FamilyMember> getPaternalAunt(FamilyMember member);


    public List<FamilyMember> getMaternalUncle(FamilyMember member);

    public List<FamilyMember> getMaternalAunt(FamilyMember member);

    public List<FamilyMember> getSisterInLaw(FamilyMember member);


    public List<FamilyMember> getBrotherInLaw(FamilyMember member);

    public List<FamilyMember> getSon(FamilyMember member);

    public List<FamilyMember> getDaughter(FamilyMember member);
}
