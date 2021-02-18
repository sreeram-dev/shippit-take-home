package com.interview.shippit.cli.controller;

import com.interview.shippit.adapter.family.FamilyAdapter;


public class CLIFamilyController {

    private final FamilyAdapter adapter;

    public CLIFamilyController(final FamilyAdapter adapter) {
        this.adapter = adapter;
    }

    public String addFamilyMember(final String motherName, final String name, final String gender) {
        return this.adapter.addFamilyMember(name, gender, motherName);
    }

    public String getRelationship(final String name, final String relationship) {
        return this.adapter.getRelationship(name, relationship);
    }

    public void addIndividualPartner(final String name, final String gender, final String partnerName) {
        this.adapter.addIndividualPartner(name, gender, partnerName);
    }

    public void createNoParentFamilyMember(final String name, final String gender) {
        this.adapter.createNoParentFamilyMember(name, gender);
    }
}
