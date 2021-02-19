package com.interview.shippit.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This is an integration test as we are loading the test
 */
public class CLIApplicationTest {

    final PrintStream originalOut = System.out;
    final PrintStream originalErr = System.err;
    final ByteArrayOutputStream out = new ByteArrayOutputStream();
    final ByteArrayOutputStream err = new ByteArrayOutputStream();


    @BeforeEach
    public void setUpStreams() {
        out.reset();
        err.reset();
        System.setOut(new PrintStream(out));
        System.setErr(new PrintStream(err));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testSimpleTestCase1_PersonNotFound() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_1.txt"});
        assertEquals("PERSON_NOT_FOUND\n" + "PERSON_NOT_FOUND\n", out.toString());
    }

    @Test
    public void testSimpleTestCase2_ChildAdditionFailed() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_2.txt"});
        assertEquals("CHILD_ADDITION_FAILED\n" + "NONE\n", out.toString());
    }

    @Test
    public void testSimpleTestCase3_RelationshipDoesNotExist() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_3.txt"});
        assertEquals("NONE\n", out.toString());
    }

    @Test
    public void testSimpleTestCase4_AddChildAndGetRelationship() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_4.txt"});
        
        assertEquals("CHILD_ADDED\n" +
            "Dominique Minerva\n" +
            "Dominique Louis Victoire\n" +
            "Dominique Victoire Minerva\n", out.toString());
    }
}
