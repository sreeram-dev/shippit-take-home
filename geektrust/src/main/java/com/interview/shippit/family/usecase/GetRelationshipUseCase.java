package com.interview.shippit.family.usecase;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.exception.FamilyRelationNotFoundException;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;
import com.interview.shippit.family.usecase.port.GetRelationshipService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<FamilyMember> result;

        switch (relationship) {
            case "Paternal-Uncle":
                result = this.repository.getPaternalUncle(member);
                break;
            case "Maternal-Uncle":
                result = this.repository.getMaternalUncle(member);
                break;
            case "Paternal-Aunt":
                result = this.repository.getPaternalAunt(member);
                break;
            case "Maternal-Aunt":
                result = this.repository.getMaternalAunt(member);
                break;
            case "Sister-In-Law":
                result = this.repository.getSisterInLaw(member);
                break;
            case "Brother-In-Law":
                result = this.repository.getBrotherInLaw(member);
                break;
            case "Siblings":
                result = this.repository.getSibling(member);
                break;
            case "Son":
                result = this.repository.getSon(member);
                break;
            case "Daughter":
                result = this.repository.getDaughter(member);
                break;
            default:
                throw new FamilyRelationNotFoundException();
        }

        // As per the requirements, return relations
        // in order they were added to family tree
        return result.stream()
            .sorted(Comparator.comparing(FamilyMember::getAddedAt))
            .collect(Collectors.toList());
    }
}
