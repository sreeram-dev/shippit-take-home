package com.interview.shippit.family.usecase.port;

import com.interview.shippit.family.entity.FamilyMember;

public interface AddFamilyService {
    FamilyMember create(final String name, final String gender, final String motherName);

    FamilyMember createNoParentFamilyMember(final String name, final String gender);

    FamilyMember createIndividualPartner(
        final String name,
        final String gender,
        final String partnerName);


}
