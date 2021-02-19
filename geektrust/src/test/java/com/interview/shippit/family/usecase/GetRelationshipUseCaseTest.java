package com.interview.shippit.family.usecase;


import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.exception.FamilyRelationNotFoundException;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;
import com.interview.shippit.family.usecase.port.GetRelationshipService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetRelationshipUseCaseTest {

    @Test
    public void testGetRelationshipToFamilyMember_PersonNotFoundException(
        @Mock FamilyMemberRepository repository) {

        GetRelationshipService service = new GetRelationshipUseCase(repository);
        assertThrows(PersonNotFoundException.class,
            () -> service.getRelationshipToFamilyMember(
                "Ted", "Maternal-Aunt"));
    }

    @Test
    public void testGetRelationshipToFamilyMember_FamilyRelationNotFoundException(
        @Mock FamilyMemberRepository repository) {

        FamilyMember alice = new FamilyMember("Alice", Gender.FEMALE);
        when(repository.findByName("Alice")).thenReturn(Optional.of(alice));

        GetRelationshipService service = new GetRelationshipUseCase(repository);

        assertThrows(FamilyRelationNotFoundException.class,
            () -> service.getRelationshipToFamilyMember(
                "Alice", "Non-Existent-Relation"));
    }

    @Test
    public void testGetRelationshipToFamilyMember_getSonEmptyList(
        @Mock FamilyMemberRepository repository) {

        FamilyMember alice = new FamilyMember("Alice", Gender.FEMALE);

        when(repository.findByName("Alice")).thenReturn(Optional.of(alice));

        GetRelationshipService service = new GetRelationshipUseCase(repository);

        List<FamilyMember> sons = service.getRelationshipToFamilyMember(
                "Alice", "Son");

        assertTrue(sons.isEmpty());
    }

    @Test
    public void testGetRelationshipToFamilyMember_getSonSuccess(
        @Mock FamilyMemberRepository repository) {

        FamilyMember alice = new FamilyMember("Alice", Gender.FEMALE);
        FamilyMember ted = new FamilyMember("Ted", Gender.MALE, alice);
        FamilyMember ronald = new FamilyMember("Ronald", Gender.MALE, alice);
        FamilyMember molly = new FamilyMember("Molly", Gender.FEMALE, alice);

        when(repository.findByName("Alice")).thenReturn(Optional.of(alice));

        // test it properly in repository mock tests
        when(repository.getSon(alice)).thenReturn(alice.getChildren()
            .stream().filter(FamilyMember::isGenderMale).collect(Collectors.toList()));

        GetRelationshipService service = new GetRelationshipUseCase(repository);

        List<FamilyMember> sons = service.getRelationshipToFamilyMember(
            "Alice", "Son");


        assertTrue(!sons.isEmpty());
        assertEquals(2, sons.size());
        FamilyMember son = sons.get(0);
        assertEquals("Ted", son.getName());
        assertEquals(alice, son.getMother());
        FamilyMember otherSon = sons.get(1);
        assertEquals("Ronald", otherSon.getName());
        assertEquals(alice, otherSon.getMother());
    }
}
