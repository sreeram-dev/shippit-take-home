package com.interview.shippit.db;


import com.interview.shippit.db.fixtures.InitialFamilyFixture;
import com.interview.shippit.family.entity.FamilyMember;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


public class InMemoryFamilyMemberRepositoryTest {

    private final InMemoryFamilyMemberRepository repository = new InMemoryFamilyMemberRepository();

    @BeforeEach
    public void setUp() {
        // load the repo with fixtures
        InitialFamilyFixture.applyFixture(repository);
    }

    @AfterEach
    public void tearDown() {
        repository.clearData();
    }

    @Test
    public void testFindByName() {
        Optional<FamilyMember> member = repository.findByName("Lola");

        assertFalse(member.isPresent());

        member = repository.findByName("Percy");
        assertTrue(member.isPresent());
        assertEquals("Percy", member.get().getName());
    }

    @Test
    public void testGetDaughter_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Percy");
        assertTrue(member.isPresent());
        FamilyMember percy = member.get();

        List<FamilyMember> daughters = repository.getDaughter(percy);
        assertFalse(daughters.isEmpty());
        List<String> names = daughters.stream().map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("Molly", "Lucy"));
    }

    @Test
    public void testGetSon_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Harry");
        assertTrue(member.isPresent());
        FamilyMember harry = member.get();

        List<FamilyMember> sons = repository.getSon(harry);
        assertFalse(sons.isEmpty());
        List<String> names = sons.stream().map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("James", "Albus"));
    }

    @Test
    public void testGetSiblings_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Percy");
        assertTrue(member.isPresent());
        FamilyMember percy = member.get();

        List<FamilyMember> siblings = repository.getSibling(percy);
        assertFalse(siblings.isEmpty());

        List<String> actualNames = siblings.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        List<String> expectedNames = Arrays.asList("Bill", "Charlie", "Ronald", "Ginerva");

        assertThat(actualNames,
            Matchers.containsInAnyOrder(expectedNames.toArray()));
    }

    @Test
    public void testGetSisterInLaw_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Ginerva");
        assertTrue(member.isPresent());
        FamilyMember ginerva = member.get();

        List<FamilyMember> sisterInLaws = repository.getSisterInLaw(ginerva);
        assertFalse(sisterInLaws.isEmpty());

        List<String> names = sisterInLaws.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        // hamcrest assertThat is <actual, expected>
        assertThat(names, Matchers.containsInAnyOrder("Helen", "Audrey", "Flora"));
    }

    @Test
    public void testGetBrotherInLaw_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Harry");
        assertTrue(member.isPresent());
        FamilyMember harry = member.get();

        List<FamilyMember> brotherInLaws = repository.getBrotherInLaw(harry);
        assertFalse(brotherInLaws.isEmpty());

        List<String> names = brotherInLaws.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("Bill", "Charlie", "Percy", "Ronald"));
    }

    @Test
    public void getPaternalUncle_EmptyList() {
        Optional<FamilyMember> member = repository.findByName("Malfoy");
        assertTrue(member.isPresent());
        FamilyMember malfoy = member.get();

        List<FamilyMember> uncles = repository.getPaternalUncle(malfoy);
        assertTrue(uncles.isEmpty());
    }

    @Test
    public void getPaternalUncle_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Rose");
        assertTrue(member.isPresent());
        FamilyMember rose = member.get();

        List<FamilyMember> uncles = repository.getPaternalUncle(rose);
        assertFalse(uncles.isEmpty());

        List<String> names = uncles.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("Bill", "Charlie", "Percy"));
    }

    @Test
    public void getMaternalUncle_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("James");
        assertTrue(member.isPresent());
        FamilyMember james = member.get();

        List<FamilyMember> uncles = repository.getMaternalUncle(james);
        assertFalse(uncles.isEmpty());

        List<String> names = uncles.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("Bill", "Charlie", "Percy", "Ronald"));
    }

    @Test
    public void getPaternalAunt_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Ron");
        assertTrue(member.isPresent());
        FamilyMember ron = member.get();

        List<FamilyMember> aunts = repository.getPaternalAunt(ron);
        assertFalse(aunts.isEmpty());

        List<String> names = aunts.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("Lily"));
    }

    @Test
    public void getMaternalAunt_NonEmptyList() {
        Optional<FamilyMember> member = repository.findByName("Remus");
        assertTrue(member.isPresent());
        FamilyMember remus = member.get();

        List<FamilyMember> aunts = repository.getMaternalAunt(remus);
        assertFalse(aunts.isEmpty());

        List<String> names = aunts.stream()
            .map(FamilyMember::getName)
            .collect(Collectors.toList());

        assertThat(names, Matchers.containsInAnyOrder("Dominique"));
    }
}
