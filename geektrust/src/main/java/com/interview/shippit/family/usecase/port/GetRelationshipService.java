package com.interview.shippit.family.usecase.port;

import com.interview.shippit.family.entity.FamilyMember;

import java.util.List;



/**
 * Interface/API that outerlayers (adapters, rest, cli) have to know to use business rules
 * This one supports the different query parameters -
 */
public interface GetRelationshipService {
    List<FamilyMember> getRelationshipToFamilyMember(String name, String relationship);
}
