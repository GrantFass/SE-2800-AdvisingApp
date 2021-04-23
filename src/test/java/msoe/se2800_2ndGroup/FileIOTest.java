package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.FileIO.DefaultLocations;
import msoe.se2800_2ndGroup.FileIO.FileIO;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: FileIOTest
 * Description:
 * * Tests for the FileIO.java class.
 * The FileIOTest class is responsible for:
 * * Testing methods related to File Input and Output
 * Modification Log:
 * * File Created by Grant on Tuesday, 30 March 2021
 * * Define basic versions of tests in javadoc by Grant Fass on Tue, 30 Mar 2021
 * * Implement basic tests for validateFileLocation, useDefaultFilesQuery, and getUserInputFileLocation by Grant Fass on Tue, 30 Mar 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Tuesday, 30 March 2021
 */
class FileIOTest {

//    @BeforeEach
//    void setUp() {
//    }

    /**
     * This method tests both of the validateFileLocation methods in FileIO.java.
     * The generic validateFileLocation(location) method tests if a file exists or not so it needs to be compared
     *   against files that exist and those that do not.
     * The overloaded validateFileLocation(location, expectedFileExtension) method tests if a file exists by calling
     *   the generic method and also validates that the location ends with the proper extension. Since this uses
     *   the generic method it should only need to test the location extension.
     * Note that this method relies on the default location getters from the Model.java class and will fail if any of
     *   those three static location getters are failing.
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    @Test
    void validateFileLocation() {
        // First test that files that should exist work with the generic method.
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultCurriculumLocation()));
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultOfferingsLocation()));
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultPrerequisitesLocation()));
        // Now test that files that should not exist return false
        assertFalse(FileIO.validateFileLocation("C://ThisSHouldReturnFalse.doc"));
        assertFalse(FileIO.validateFileLocation("C://somethingWrongHere.txt"));
        assertFalse(FileIO.validateFileLocation("Z://T.gg"));
        assertFalse(FileIO.validateFileLocation("d://somwhereOverTheMisspellign.q"));
        // Test that empty values and null values return false
        assertFalse(FileIO.validateFileLocation(""));
        assertFalse(FileIO.validateFileLocation(null));
        // Test that files that should exist return true with the correct extension
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultCurriculumLocation(), ".csv"));
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultOfferingsLocation(), ".CSV"));
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultPrerequisitesLocation(), ".Csv"));
        // Test that files that should exist return false with the wrong extension
        assertFalse(FileIO.validateFileLocation(DefaultLocations.getDefaultCurriculumLocation(), ".gg"));
        assertFalse(FileIO.validateFileLocation(DefaultLocations.getDefaultOfferingsLocation(), ".WRONG"));
        assertFalse(FileIO.validateFileLocation(DefaultLocations.getDefaultPrerequisitesLocation(), ".Doc"));
        // Test that the overloaded method does not fatal with null or empty values
        assertTrue(FileIO.validateFileLocation(DefaultLocations.getDefaultCurriculumLocation(), ""));
        assertFalse(FileIO.validateFileLocation(DefaultLocations.getDefaultOfferingsLocation(), null));
        assertFalse(FileIO.validateFileLocation("", ".Doc"));
        assertFalse(FileIO.validateFileLocation(null, ".gg"));
        assertFalse(FileIO.validateFileLocation("", ".csv"));
        assertFalse(FileIO.validateFileLocation(null, ".csv"));
        assertFalse(FileIO.validateFileLocation("", ""));
        assertFalse(FileIO.validateFileLocation(null, ""));
        assertFalse(FileIO.validateFileLocation("", null));
        assertFalse(FileIO.validateFileLocation(null, null));
    }

    /**
     * This method tests the overloaded useDefaultFilesQuery(inputStream) method by passing in different inputs
     *   and comparing the outputs
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/questions/1647907/junit-how-to-simulate-system-in-testing"}</a>:
     *  Help defining a custom input stream
     *
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    @Test
    void useDefaultFilesQuery() {
        // test that the method will return false for various capitalization of 'n' or 'no' with various leading and trailing spaces.
        String data = "n";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "n  ";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "  n";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = " n ";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "no";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "no  ";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "  no";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "  no   ";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "N";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "No";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "nO";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "NO";
        assertFalse(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        // test that the method returns true for most other cases
        data = "Y";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "YES";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "y";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "yes";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "a;dlfjk;";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "13485";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "asdf165";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "generic input";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "\n";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        data = "";
        assertTrue(FileIO.useDefaultFilesQuery(new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
    }

    /**
     * This method tests the overloaded getUserInputFileLocation(nameOfFile, inputStream, outputStream) method.
     * The method in FileIO.java calls upon the overloaded validateFileLocation(location, expectedFileExtension)
     *   method so this method will have few tests (this is because they are already handled by the aforementioned
     *   validation method). This method will primarily test that the proper exceptions are thrown, as well as a
     *   basic test for proper returned values.
     * Note that this method relies on the default location getters from the Model.java class and will fail if any of
     *   those three static location getters are failing.
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    @Test
    void getUserInputFileLocation() {
        // test that the user input location for a file that exists is returned correctly
        try {
            String data = DefaultLocations.getDefaultCurriculumLocation();
            assertEquals(data, FileIO.getUserInputFileLocation("Curriculum.csv", ".csv", new ByteArrayInputStream(data.getBytes()), System.out));
            //assertEquals(data, FileIO.getUserInputFileLocation("Curriculum.csv", new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
        } catch (CustomExceptions.InvalidInputException e) {
            fail();
        }
        // fail if file does not exist
        String data = DefaultLocations.getDefaultCurriculumLocation() + "TTTT";
        assertThrows(CustomExceptions.InvalidInputException.class, () -> FileIO.getUserInputFileLocation("Curriculum.csv", ".csv", new ByteArrayInputStream(data.getBytes()), System.out));
        //assertThrows(Model.InvalidInputException.class, () -> FileIO.getUserInputFileLocation("Curriculum.csv", new ByteArrayInputStream(data.getBytes()), new PrintStream(new ByteArrayOutputStream())));
    }
}