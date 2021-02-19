package com.interview.shippit.db.fixtures;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;

public final class InitialFamilyFixture {

    public static void applyFixture(FamilyMemberRepository repo) {
        FamilyMember king = repo.createNoParentFamilyMember("Arthur", Gender.MALE);
        FamilyMember queen = repo.createNoParentFamilyMember("Margret", Gender.FEMALE);
        king.setPartner(queen);
        queen.setPartner(king);

        FamilyMember percy = repo.createFamilyMember("Percy", Gender.MALE, queen);
        FamilyMember charlie = repo.createFamilyMember("Charlie", Gender.MALE, queen);
        FamilyMember ronald = repo.createFamilyMember("Ronald", Gender.MALE, queen);
        FamilyMember bill = repo.createFamilyMember("Bill", Gender.MALE, queen);
        FamilyMember ginerva = repo.createFamilyMember("Ginerva", Gender.FEMALE, queen);

        FamilyMember flora = repo.createNoParentFamilyMember("Flora", Gender.FEMALE);
        flora.setPartner(bill);
        bill.setPartner(flora);

        FamilyMember audrey = repo.createNoParentFamilyMember("Audrey", Gender.FEMALE);
        percy.setPartner(audrey);
        audrey.setPartner(percy);

        FamilyMember helen = repo.createNoParentFamilyMember("Helen", Gender.FEMALE);
        helen.setPartner(ronald);
        ronald.setPartner(helen);

        FamilyMember harry = repo.createNoParentFamilyMember("Harry", Gender.MALE);
        harry.setPartner(ginerva);
        ginerva.setPartner(harry);


        FamilyMember dominique = repo.createFamilyMember("Dominique", Gender.FEMALE, flora);
        FamilyMember louis = repo.createFamilyMember("Louis", Gender.MALE, flora);
        FamilyMember victoire = repo.createFamilyMember("Victoire", Gender.FEMALE, flora);
        FamilyMember ted = repo.createNoParentFamilyMember("Ted", Gender.MALE);
        ted.setPartner(victoire);
        victoire.setPartner(ted);
        FamilyMember remus = repo.createFamilyMember("Remus", Gender.MALE, victoire);

        FamilyMember molly = repo.createFamilyMember("Molly", Gender.FEMALE, audrey);
        FamilyMember lucy = repo.createFamilyMember("Lucy", Gender.FEMALE, audrey);

        FamilyMember rose = repo.createFamilyMember("Rose", Gender.FEMALE, helen);
        FamilyMember hugo = repo.createFamilyMember("Hugo", Gender.MALE, helen);
        FamilyMember malfoy = repo.createNoParentFamilyMember("Malfoy", Gender.MALE);
        malfoy.setPartner(rose);
        rose.setPartner(malfoy);
        FamilyMember draco = repo.createFamilyMember("Draco", Gender.MALE, rose);
        FamilyMember aster = repo.createFamilyMember("Aster", Gender.FEMALE, rose);

        FamilyMember james = repo.createFamilyMember("James", Gender.MALE, ginerva);
        FamilyMember albus = repo.createFamilyMember("Albus", Gender.MALE, ginerva);
        FamilyMember lily = repo.createFamilyMember("Lily", Gender.FEMALE, ginerva);
        FamilyMember darcy = repo.createNoParentFamilyMember("Darcy", Gender.FEMALE);
        darcy.setPartner(james);
        james.setPartner(darcy);
        FamilyMember alice = repo.createNoParentFamilyMember("Alice", Gender.FEMALE);
        alice.setPartner(albus);
        albus.setPartner(alice);


        FamilyMember william = repo.createFamilyMember("William", Gender.MALE, darcy);
        FamilyMember ron = repo.createFamilyMember("Ron", Gender.MALE, alice);
        FamilyMember ginny = repo.createFamilyMember("Ginny", Gender.FEMALE, alice);
    }
}
