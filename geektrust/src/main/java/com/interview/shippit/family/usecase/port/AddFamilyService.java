package com.interview.shippit.family.usecase.port;

import com.interview.shippit.family.entity.FamilyMember;

/**
 * Interface/API that outerlayers (adapters, rest, cli) have to know to use business rules
 * This particular usecase contains the business rules to support 'ADD_CHILD'
 *  command
 */
public interface AddFamilyService {
    FamilyMember create(final String name, final String gender, final String motherName);

    FamilyMember createNoParentFamilyMember(final String name, final String gender);

    FamilyMember createIndividualPartner(
        final String name,
        final String gender,
        final String partnerName);
}
