package com.interview.shippit.family.usecase;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.exception.NameAlreadyExistsException;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.port.AddFamilyService;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;

import java.util.Optional;

/**
 * Interactors or Usecases implement business rules of the product
 *
 * AddFamilyMember creates the root parent (no parents), children and
 * partners to the children
 *
 */
public class AddFamilyMemberUseCase implements AddFamilyService {

    private final FamilyMemberRepository repository;

    public AddFamilyMemberUseCase(final FamilyMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public FamilyMember create(final String name, final String gender, final String motherName)
            throws PersonNotFoundException, NameAlreadyExistsException {

        Optional<FamilyMember> mother = repository.findByName(motherName);

        if (!mother.isPresent()) {
            throw new PersonNotFoundException();
        }

        if (!mother.get().isGenderFemale()) {
            throw new IllegalArgumentException("Mother needs to be identified as Female");
        }

        Optional<FamilyMember> optional = repository.findByName(name);

        if (optional.isPresent()) {
            throw new NameAlreadyExistsException();
        }

        return repository.createFamilyMember(
            name, Gender.getGenderbyName(gender), mother.get());
    }

    @Override
    public FamilyMember createNoParentFamilyMember(final String name, final String gender) {

        Optional<FamilyMember> optional = repository.findByName(name);

        if (optional.isPresent()) {
            throw new NameAlreadyExistsException();
        }

        return this.repository.createNoParentFamilyMember(name, Gender.getGenderbyName(gender));
    }

    @Override
    public FamilyMember createIndividualPartner(
            final String name,
            final String gender,
            final String partnerName) {

        Optional<FamilyMember> memberOptional = repository.findByName(partnerName);

        if (!memberOptional.isPresent()) {
            throw new PersonNotFoundException();
        }

        FamilyMember partner = memberOptional.get();

        Optional<FamilyMember> optional = repository.findByName(name);

        if (optional.isPresent()) {
            throw new NameAlreadyExistsException();
        }

        FamilyMember member = this.repository.createNoParentFamilyMember(
                name, Gender.getGenderbyName(gender));
        member.setPartner(partner);
        partner.setPartner(member);

        return member;
    }
}
