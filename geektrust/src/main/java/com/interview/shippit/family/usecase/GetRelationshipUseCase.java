package com.interview.shippit.family.usecase;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.exception.FamilyRelationNotFoundException;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;
import com.interview.shippit.family.usecase.port.GetRelationshipService;

import java.util.List;
import java.util.Optional;

public class GetRelationshipUseCase implements GetRelationshipService {

    private final FamilyMemberRepository repository;

    public GetRelationshipUseCase(final FamilyMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<FamilyMember> getRelationshipToFamilyMember(String name, String relationship)
            throws PersonNotFoundException, FamilyRelationNotFoundException {
        Optional<FamilyMember> optional = this.repository.findByName(name);

        if (!optional.isPresent()) {
            throw new PersonNotFoundException();
        }

        FamilyMember member = optional.get();

        switch (relationship) {
            case "Paternal-Uncle": return this.repository.getPaternalUncle(member);
            case "Maternal-Uncle": return this.repository.getMaternalUncle(member);
            case "Paternal-Aunt": return this.repository.getPaternalAunt(member);
            case "Maternal-Aunt": return this.repository.getMaternalAunt(member);
            case "Sister-in-Law": return this.repository.getSisterInLaw(member);
            case "Brother-in-Law": return this.repository.getBrotherInLaw(member);
            case "Siblings": return this.repository.getSibling(member);
            case "Son": return this.repository.getSon(member);
            case "Daughter": return this.repository.getDaughter(member);
            default: throw new FamilyRelationNotFoundException();
        }

    }
}
