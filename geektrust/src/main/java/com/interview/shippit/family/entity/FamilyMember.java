package com.interview.shippit.family.entity;

import com.interview.shippit.family.entity.enums.Gender;

import java.util.ArrayList;
import java.util.List;

public class FamilyMember {

    private final Gender gender;
    private final String name;

    private final List<FamilyMember> children = new ArrayList<>();
    private FamilyMember father;
    private FamilyMember mother;
    private FamilyMember partner;

    public FamilyMember(final String name,
                        final Gender gender,
                        final FamilyMember mother) {
        this.name = name;
        this.gender = gender;
        this.father = mother.getPartner();
        this.mother = mother;
    }

    public FamilyMember(final String name, final Gender gender) {
        this.name = name;
        this.gender = gender;
        this.father = null;
        this.mother = null;
    }

    public static FamilyMemberBuilder builder() {
        return new FamilyMemberBuilder();
    }

    public List<FamilyMember> getChildren() {
        return this.children;
    }

    public String getName() {
        return this.name;
    }


    public FamilyMember getPartner() {
        return this.partner;
    }

    public void setPartner(final FamilyMember partner) {
        this.partner = partner;
    }

    public void setMother(final FamilyMember parent) {
        this.mother = parent;
    }

    public void setFather(final FamilyMember parent) {
        this.father = parent;
    }

    public void addChild(FamilyMember child) {
        this.children.add(child);
    }

    public Boolean isGenderMale() {
        return this.gender.equals(Gender.MALE);
    }

    public Boolean isGenderFemale() {
        return this.gender.equals(Gender.FEMALE);
    }

    public FamilyMember getMother() {
        return this.mother;
    }

    public FamilyMember getFather() {
        return this.father;
    }

    public static class FamilyMemberBuilder {
        private List<FamilyMember> children = new ArrayList<>();
        FamilyMemberBuilder() {
        }
    }
}
