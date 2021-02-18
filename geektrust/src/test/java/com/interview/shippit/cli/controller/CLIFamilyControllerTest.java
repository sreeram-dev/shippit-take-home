package com.interview.shippit.cli.controller;

import com.interview.shippit.adapter.family.FamilyAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * CLIFamilyControllerTests to test the correct output is passed to the cli
 */
@ExtendWith(MockitoExtension.class)
public class CLIFamilyControllerTest {

    @Test
    public void testAddFamilyMember_childAdditionFail(@Mock FamilyAdapter adapter) {

        when(adapter.addFamilyMember("Bella", "Female", "Ted"))
            .thenReturn("CHILD_ADDITION_FAILED");
        //doReturn("CHILD_ADDITION_FAILED").when(controller.addFamilyMember(
        //    "Bella", "Female", "Ted"));

        CLIFamilyController cli = new CLIFamilyController(adapter);
        String response = cli.addFamilyMember("Ted", "Bella", "Female");
        assertEquals("CHILD_ADDITION_FAILED", response);
    }

    @Test
    public void testAddFamilyMember_personNotFound(@Mock FamilyAdapter adapter) {

        when(adapter.addFamilyMember("Lola", "Female", "Luna"))
            .thenReturn("PERSON_NOT_FOUND");

        CLIFamilyController cli = new CLIFamilyController(adapter);
        String response = cli.addFamilyMember("Luna", "Lola", "Female");
        assertEquals("PERSON_NOT_FOUND", response);
    }

    @Test
    public void testAddFamilyMember_childAddedSuccess(@Mock FamilyAdapter adapter) {
        when(adapter.addFamilyMember("Minerva", "Female", "Flora"))
            .thenReturn("CHILD_ADDED");

        CLIFamilyController cli = new CLIFamilyController(adapter);
        String response = cli.addFamilyMember("Flora", "Minerva", "Female");
        assertEquals("CHILD_ADDED", response);
    }

    @Test
    public void testGetRelationship_foundRelationship(@Mock FamilyAdapter adapter) {
        when(adapter.getRelationship("Remus", "Maternal-Aunt"))
            .thenReturn("Dominique Minerva");

        CLIFamilyController cli = new CLIFamilyController(adapter);
        String response = cli.getRelationship("Remus", "Maternal-Aunt");
        assertEquals("Dominique Minerva", response);
    }

    @Test
    public void testGetRelationship_NoRelationship(@Mock FamilyAdapter adapter) {

        when(adapter.getRelationship("Remus", "Siblings"))
            .thenReturn("NONE");

        CLIFamilyController cli = new CLIFamilyController(adapter);
        String response = cli.getRelationship("Resmus", "Siblings");
        assertEquals("NONE", response);
    }

    @Test
    public void testGetRelationship_PersonNotFoundException(@Mock FamilyAdapter adapter) {
        when(adapter.getRelationship("Luna", "Maternal-Aunt"))
            .thenReturn("PERSON_NOT_FOUND");

        CLIFamilyController cli = new CLIFamilyController(adapter);
        String response = cli.getRelationship("Luna", "Maternal-Aunt");
        assertEquals("PERSON_NOT_FOUND", response);
    }
}
