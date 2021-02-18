package com.interview.shippit.db;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.repository.FamilyMemberRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryFamilyMemberRepository implements FamilyMemberRepository {

    private final Map<String, FamilyMember> map = new HashMap<>();

    @Override
    public FamilyMember createFamilyMember(String name, Gender gender, FamilyMember mother) {
        FamilyMember member = new FamilyMember(name, gender, mother);
        this.map.put(name, member);
        return member;
    }

    @Override
    public Optional<FamilyMember> findByName(String name) {
        return Optional.ofNullable(map.getOrDefault(name, null));
    }

    @Override
    public List<FamilyMember> getSibling(FamilyMember member) throws PersonNotFoundException {
        FamilyMember mother = member.getMother();
        if (mother == null) {
            throw new PersonNotFoundException();
        }

        List<FamilyMember> children = mother.getChildren();

        return children.stream()
                .filter(mem -> !mem.getName().equals(member.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getPaternalUncle(FamilyMember member) {
        FamilyMember father = member.getFather();
        FamilyMember grandFather = father.getFather();

        return grandFather.getChildren()
                .stream()
                .filter(mem -> mem.isGenderMale() && !mem.getName().equals(father.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getMaternalUncle(FamilyMember member) {
        FamilyMember mother = member.getMother();
        FamilyMember grandFather = mother.getFather();

        return grandFather.getChildren()
                .stream()
                .filter(FamilyMember::isGenderMale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getPaternalAunt(FamilyMember member) {
        FamilyMember mother = member.getMother();
        FamilyMember grandFather = mother.getFather();

        return grandFather.getChildren()
                .stream()
                .filter(FamilyMember::isGenderMale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getMaternalAunt(FamilyMember member) {
        FamilyMember mother = member.getMother();
        FamilyMember grandFather = mother.getFather();

        return grandFather.getChildren()
                .stream()
                .filter(mem -> mem.isGenderFemale() && !mem.getName().equals(member.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getSisterInLaw(FamilyMember member) {
        FamilyMember partner = member.getPartner();

        List<FamilyMember> partnerSiblings = getSibling(partner)
                .stream()
                .filter(mem -> mem.isGenderFemale() && !mem.getName().equals(partner.getName()))
                .collect(Collectors.toList());

        List<FamilyMember> siblingPartners = getSibling(member)
                .stream()
                .filter(mem -> mem.isGenderMale() && !mem.getName().equals(partner.getName()))
                .map(FamilyMember::getPartner)
                .collect(Collectors.toList());

        return Stream.of(partnerSiblings, siblingPartners)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getBrotherInLaw(FamilyMember member) {
        FamilyMember partner = member.getPartner();

        List<FamilyMember> partnerSiblings = getSibling(partner)
                .stream()
                .filter(mem -> mem.isGenderMale() && !mem.getName().equals(partner.getName()))
                .collect(Collectors.toList());

        List<FamilyMember> siblingPartners = getSibling(member)
                .stream()
                .filter(mem -> mem.isGenderFemale() && !mem.getName().equals(partner.getName()))
                .map(FamilyMember::getPartner)
                .collect(Collectors.toList());

        return Stream.of(partnerSiblings, siblingPartners)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getSon(FamilyMember member) {
        return member.getChildren()
                .stream()
                .filter(FamilyMember::isGenderMale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getDaughter(FamilyMember member) {
        return member.getChildren()
                .stream()
                .filter(FamilyMember::isGenderFemale)
                .collect(Collectors.toList());
    }

    @Override
    public FamilyMember createNoParentFamilyMember(String name, Gender gender) {
        FamilyMember member =  new FamilyMember(name, gender);
        map.put(name, member);
        return member;
    }

    // helper function to evaluate the solution
    public List<String> getAllMembersStoredInMemory() {
        return map.values().stream().map(FamilyMember::getName).collect(Collectors.toList());
    }
}