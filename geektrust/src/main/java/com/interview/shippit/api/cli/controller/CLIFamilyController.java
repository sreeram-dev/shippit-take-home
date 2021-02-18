package com.interview.shippit.api.cli.controller;

import com.interview.shippit.controller.FamilyController;


public class CLIFamilyController {

    private final FamilyController controller;

    public  CLIFamilyController(final FamilyController controller) {
        this.controller = controller;
    }

    public String addFamilyMember(final String motherName, final String name, final String gender) {
        return this.controller.addFamilyMember(name, gender, motherName);
    }

    public String getRelationship(final String name, final String relationship) {
        return this.controller.getRelationship(name, relationship);
    }

    public void addIndividualPartner(final String name, final String gender, final String partnerName) {
        this.controller.addIndividualPartner(name, gender, partnerName);
    }

    public void createNoParentFamilyMember(final String name, final String gender) {
        this.controller.createNoParentFamilyMember(name, gender);
    }
}