package com.interview.shippit.family.usecase;

import java.util.Optional;

import com.interview.shippit.family.entity.FamilyMember;
import com.interview.shippit.family.entity.enums.Gender;
import com.interview.shippit.family.usecase.exception.NameAlreadyExistsException;
import com.interview.shippit.family.usecase.exception.PersonNotFoundException;
import com.interview.shippit.family.usecase.port.AddFamilyService;
import com.interview.shippit.family.usecase.port.FamilyMemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddFamilyMemberUseCaseTest {

    @Test
    public void testCreateFamilyMember_personNotFoundException(
        @Mock FamilyMemberRepository repository) {

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        assertThrows(PersonNotFoundException.class,
            () -> service.create("Lola", "Female", "Luna"));
    }

    @Test
    public void testCreateFamilyMember_childAdditionFailed(
        @Mock FamilyMemberRepository repository) {

        FamilyMember ted = new FamilyMember("Ted", Gender.MALE);

        when(repository.findByName("Ted"))
            .thenReturn(Optional.of(ted));

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        assertThrows(IllegalArgumentException.class,
            () -> service.create("Audrey", "Female", "Ted"));

    }

    @Test
    public void testCreateFamilyMember_childAddedSuccess(
        @Mock FamilyMemberRepository repository) {

        FamilyMember margaret = new FamilyMember("Margaret", Gender.FEMALE);

        when(repository.findByName("Margaret"))
            .thenReturn(Optional.of(margaret));

        when(repository.createFamilyMember("Luna", Gender.FEMALE, margaret))
            .thenReturn(new FamilyMember("Luna", Gender.FEMALE, margaret));

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        FamilyMember luna = service.create("Luna", "Female", "Margaret");

        assertNotNull(luna);
        assertEquals("Luna", luna.getName());
        assertEquals(margaret.getName(), luna.getMother().getName());
    }

    @Test
    public void testCreateIndividualPartner_addPartnerSuccess(
        @Mock FamilyMemberRepository repository) {

        FamilyMember alice = new FamilyMember("Alice", Gender.FEMALE);

        when(repository.findByName("Alice")).thenReturn(Optional.of(alice));

        when(repository.createNoParentFamilyMember("Ted", Gender.MALE))
            .thenReturn(new FamilyMember("Ted", Gender.MALE));

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        FamilyMember ted =service.createIndividualPartner("Ted", "Male", "Alice");

        assertNotNull(ted);
        assertEquals("Ted", ted.getName());
        assertTrue(ted.isGenderMale());
    }

    @Test
    public void testCreateIndividualPartner_PersonNotFoundException(
        @Mock FamilyMemberRepository repository) {

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        assertThrows(PersonNotFoundException.class,
            () -> service.createIndividualPartner("Ted", "Male", "Alice"));
    }


    @Test
    public void testCreateIndividualPartner_NameAlreadyExistsException(
        @Mock FamilyMemberRepository repository) {

        FamilyMember ted = new FamilyMember("Ted", Gender.MALE);
        FamilyMember alice = new FamilyMember("Alice", Gender.FEMALE);

        when(repository.findByName("Ted")).thenReturn(Optional.of(ted));
        when(repository.findByName("Alice")).thenReturn(Optional.of(alice));

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        assertThrows(NameAlreadyExistsException.class,
            () -> service.createIndividualPartner("Ted", "Male", "Alice"));
    }

    @Test
    public void testCreateNoParentFamilyMember_NameAlreadyExistsException(
        @Mock FamilyMemberRepository repository) {

        FamilyMember ted = new FamilyMember("Ted", Gender.MALE);

        when(repository.findByName("Ted")).thenReturn(Optional.of(ted));

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        assertThrows(NameAlreadyExistsException.class,
            () -> service.createNoParentFamilyMember("Ted", "Male"));
    }

    @Test
    public void testCreateNoParentFamilyMember_Success(
        @Mock FamilyMemberRepository repository) {

        when(repository.createNoParentFamilyMember("Ted", Gender.MALE))
            .thenReturn(new FamilyMember("Ted", Gender.MALE));

        AddFamilyService service = new AddFamilyMemberUseCase(repository);
        FamilyMember ted = service.createNoParentFamilyMember("Ted", "Male");

        // assert that ted object is created and it has no parents
        assertNotNull(ted);
        assertEquals("Ted", ted.getName());
        assertTrue(ted.isGenderMale());
        assertNull(ted.getMother());
        assertNull(ted.getFather());
    }

}
