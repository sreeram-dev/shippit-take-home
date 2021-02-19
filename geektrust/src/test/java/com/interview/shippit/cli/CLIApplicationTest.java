package com.interview.shippit.cli;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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
    public void testSimpleTestCase_PersonNotFound() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_1.txt"});
        assertEquals("PERSON_NOT_FOUND\n" + "PERSON_NOT_FOUND\n", out.toString());
    }

    @Test
    public void testSimpleTestCase_ChildAdditionFailed() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_2.txt"});
        assertEquals("CHILD_ADDITION_FAILED\n" + "NONE\n", out.toString());
    }

    @Test
    public void testSimpleTestCase_PartnerRelationships() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_3.txt"});
        assertEquals("Darcy Alice\n" +
            "Flora Audrey Helen\n", out.toString());
    }

    @Test
    public void testSimpleTestCase_AddChildAndGetRelationship() {
        CLIApplication.main(new String[]{"src/test/resources/simple_testcase_4.txt"});

        assertEquals("CHILD_ADDED\n" +
            "Dominique Minerva\n" +
            "Victoire Dominique Louis\n" +
            "Victoire Dominique Minerva\n", out.toString());
    }

    @Test
    public void testInvalidTestCase_typoInCommand() {
       CLIApplication.main(new String[]{"src/test/resources/invalid_file_testcase.txt"});
       // all output thrown to err
       assertEquals("ERROR: Invalid Command found in File\n", err.toString());
       // no output written to stdout
       assertTrue(out.size() == 0);
    }

    @Test
    public void testInvalidTestCase_noFileFound() {
        CLIApplication.main(new String[]{"src/test/resources/invalid_file.txt"});
        assertEquals("ERROR: File not found\n", err.toString());
        // no output written to stdout
        assertTrue(out.size() == 0);
    }

    @Test
    public void testInvalidTestCase_noFileParamater() {
        CLIApplication.main(new String[]{});
        assertEquals("ERROR: Requires file parameter\n", err.toString());
        // no output written to stdout
        assertTrue(out.size() == 0);
    }
}
