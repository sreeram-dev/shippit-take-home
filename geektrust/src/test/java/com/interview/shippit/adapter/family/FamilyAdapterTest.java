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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FamilyAdapterTest {


    private final InMemoryFamilyMemberRepository repository = new InMemoryFamilyMemberRepository();
    private final AddFamilyService familyService = new AddFamilyMemberUseCase(repository);
    private final GetRelationshipService relationshipService = new GetRelationshipUseCase(repository);
    @BeforeEach
    public void setUp() {
        InitialFamilyFixture.applyFixture(repository);
    }

    @AfterEach
    public void tearDown() {
        repository.clearData();
    }

    @Test
    public void testAddFamilyMember_Success() {
        FamilyAdapter adapter = new FamilyAdapter(familyService, relationshipService);
        String result = adapter.addFamilyMember("Lola", "Female", "Victoire");
        assertEquals("CHILD_ADDED", result);

        // are relations accessible
        String relations = adapter.getRelationship("Lola", "Siblings");
        assertEquals("Remus", relations);
    }
}
