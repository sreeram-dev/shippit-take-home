package com.interview.shippit.family.usecase.port;

import com.interview.shippit.family.entity.FamilyMember;

import java.util.List;

public interface GetRelationshipService {
    List<FamilyMember> getRelationshipToFamilyMember(String name, String relationship);
}
