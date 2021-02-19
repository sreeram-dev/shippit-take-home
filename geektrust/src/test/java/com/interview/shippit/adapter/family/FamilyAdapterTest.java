package com.interview.shippit.adapter.family;


import com.interview.shippit.db.InMemoryFamilyMemberRepository;
import com.interview.shippit.db.fixtures.InitialFamilyFixture;
import com.interview.shippit.family.usecase.AddFamilyMemberUseCase;
import com.interview.shippit.family.usecase.GetRelationshipUseCase;
import com.interview.shippit.family.usecase.port.AddFamilyService;
import com.interview.shippit.family.usecase.port.GetRelationshipService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * This is part of integration testing as we are not mocking the repository
 */
@ExtendWith(MockitoExtension.class)
public class FamilyAdapterTest {

    private final InMemoryFamilyMemberRepository repository = new InMemoryFamilyMemberRepository();
    private final AddFamilyService familyService = new AddFamilyMemberUseCase(repository);
    private final GetRelationshipService relationshipService = new GetRelationshipUseCase(repository);

    @BeforeEach
    public void setUp() {
        InitialFamilyFixture.applyFixture(repository);
    }

    /**
     * Clear the data in the inmemory hashmap after each test
     */
    @AfterEach
    public void tearDown() {
        repository.clearData();
    }

    @Test
    public void testAddFamilyMember_ChildAdded() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);
        String result = adapter.addFamilyMember("Lola", "Female", "Victoire");
        assertEquals("CHILD_ADDED", result);

        // are relations accessible
        String relations = adapter.getRelationship("Lola", "Siblings");
        assertEquals("Remus", relations);
    }

    @Test
    public void testAddFamilyMember_ChildAdditionFailed() {

        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        // add child to a father node
        String result = adapter.addFamilyMember("Robert",  "Male", "Ted");

        assertEquals("CHILD_ADDITION_FAILED", result);
    }

    @Test
    public void testAddFamilyMember_NameAlreadyExistsException() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        String result = adapter.addFamilyMember("Percy", "Male", "Margret");

        assertEquals("CHILD_ADDITION_FAILED", result);
    }

    @Test
    public void testAddFamilyMember_PersonNotFoundException() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        String result = adapter.addFamilyMember(
            "Hillary", "Female", "Lola");

        assertEquals("PERSON_NOT_FOUND", result);
    }


    @Test
    public void testGetRelationship_PersonNotFoundException() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        String result = adapter.getRelationship("Lola", "Siblings");

        assertEquals("PERSON_NOT_FOUND", result);
    }

    @Test
    public void testGetRelationship_FamilyRelationNotFound() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        String result = adapter.getRelationship("Percy", "SonInLaw");

        assertEquals("NONE", result);
    }

    @Test
    public void testGetRelationship_NoRelationExists() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        String result = adapter.getRelationship("Remus", "Siblings");

        assertEquals("NONE", result);
    }

    @Test
    public void testGetRelationship_RelationshipExists() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);

        String result = adapter.getRelationship("Draco", "Siblings");

        // Assert that Aster is present as a result
        assertEquals("Aster", result);
    }

    @Test
    public void testGetRelationship_InOrder() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);


        String result = adapter.getRelationship("Percy", "Siblings");

        // Percy, charlie, Ronald, Bill, Ginerva were added in order in fixtures
        assertEquals("Charlie Ronald Bill Ginerva", result);

        // add a new aunt for remus, dominique is already present in dataset
        adapter.addFamilyMember("Minerva", "Female", "Flora");
        result = adapter.getRelationship("Remus", "Maternal-Aunt");
        // assert dominique is shown before minerva
        assertEquals("Dominique Minerva", result);
    }
}
