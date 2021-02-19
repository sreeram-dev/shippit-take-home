package com.interview.shippit.db;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Input port for the family member usecases
 * Uses hashmap as the storage engine to store data and LinkedHashSet to maintain order of insertion.
 * Implements the repository interface defined in the domain usecases (interactors)
 */
public class InMemoryFamilyMemberRepository implements FamilyMemberRepository {

    // Persist data in a hashmap
    private final Map<String, FamilyMember> map = new HashMap<>();

    // maintain the order of insertion into the hashmap for sorting purposes
    private final Set<String> order = new LinkedHashSet<>();

    @Override
    public FamilyMember createFamilyMember(String name, Gender gender, FamilyMember mother) {
        FamilyMember member = new FamilyMember(name, gender, mother);
        this.map.put(name, member);
        order.add(name);
        return member;
    }

    @Override
    public Optional<FamilyMember> findByName(String name) {
        return Optional.ofNullable(map.getOrDefault(name, null));
    }

    @Override
    public List<FamilyMember> getSibling(FamilyMember member) {

        if (member == null) {
            return new ArrayList<>();
        }

        FamilyMember mother = member.getMother();
        if (mother == null) {
            return new ArrayList<>();
        }

        List<FamilyMember> children = mother.getChildren();

        return children.stream()
                .filter(mem -> !mem.getName().equals(member.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getPaternalUncle(FamilyMember member) {
        if (!Optional.ofNullable(member.getFather()).isPresent()) {
            return new ArrayList<>();
        }

        FamilyMember father = member.getFather();

        return getSibling(father)
                .stream()
                .filter(FamilyMember::isGenderMale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getMaternalUncle(FamilyMember member) {
        if (!Optional.ofNullable(member.getMother()).isPresent()) {
            return new ArrayList<>();
        }

        FamilyMember mother = member.getMother();

        return getSibling(mother)
                .stream()
                .filter(FamilyMember::isGenderMale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getPaternalAunt(FamilyMember member) {

        if (!Optional.ofNullable(member.getMother()).isPresent()) {
            return new ArrayList<>();
        }

        FamilyMember father = member.getFather();

        return getSibling(father)
                .stream()
                .filter(FamilyMember::isGenderFemale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getMaternalAunt(FamilyMember member) {

        if (!Optional.ofNullable(member.getMother()).isPresent()) {
            return new ArrayList<>();
        }

        FamilyMember mother = member.getMother();

        return getSibling(mother)
                .stream()
                .filter(FamilyMember::isGenderFemale)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getSisterInLaw(FamilyMember member) {
        FamilyMember partner = member.getPartner();

        List<FamilyMember> partnerSiblings;

        if (partner == null) {
            partnerSiblings = getSibling(partner)
                .stream()
                .filter(FamilyMember::isGenderFemale)
                .collect(Collectors.toList());
        } else {
            partnerSiblings = new ArrayList<>();
        }

        List<FamilyMember> siblingPartners = getSibling(member)
                .stream()
                .filter(FamilyMember::isGenderMale)
                .filter(FamilyMember::hasPartner)
                .map(FamilyMember::getPartner)
                .collect(Collectors.toList());

        return Stream.of(partnerSiblings, siblingPartners)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public List<FamilyMember> getBrotherInLaw(FamilyMember member) {
        FamilyMember partner = member.getPartner();

        List<FamilyMember> partnerSiblings;

        if (partner == null) {
            partnerSiblings = new ArrayList<>();
        } else {
            partnerSiblings = getSibling(partner)
                .stream()
                .filter(FamilyMember::isGenderMale)
                .collect(Collectors.toList());
        }

        List<FamilyMember> siblingPartners = getSibling(member)
                .stream()
                .filter(FamilyMember::isGenderFemale)
                .filter(FamilyMember::hasPartner)
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
        order.add(name);
        return member;
    }

    // helper function to evaluate the solution
    public List<String> getAllMembersStoredInMemory() {
        return map.values().stream().map(FamilyMember::getName).collect(Collectors.toList());
    }

    public void clearData() {
        map.clear();
        order.clear();
    }

    @Override
    public List<FamilyMember> sortOnAddedTime(List<FamilyMember> members) {
        List<String> sortedOnTimeOrder = order.stream().collect(Collectors.toList());

        members.sort((o1, o2) -> {
            String name1 = o1.getName();
            String name2 = o2.getName();

            return sortedOnTimeOrder.indexOf(name1) - sortedOnTimeOrder.indexOf(name2);
        });

        return members;
    }
}
