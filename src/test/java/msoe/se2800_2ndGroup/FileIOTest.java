package msoe.se2800_2ndGroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertTrue(FileIO.validateFileLocation(Model.getDefaultCurriculumLocation()));
        assertTrue(FileIO.validateFileLocation(Model.getDefaultOfferingsLocation()));
        assertTrue(FileIO.validateFileLocation(Model.getDefaultPrerequisitesLocation()));
        // Now test that files that should not exist return false
        assertFalse(FileIO.validateFileLocation("C://ThisSHouldReturnFalse.doc"));
        assertFalse(FileIO.validateFileLocation("C://somethingWrongHere.txt"));
        assertFalse(FileIO.validateFileLocation("Z://T.gg"));
        assertFalse(FileIO.validateFileLocation("d://somwhereOverTheMisspellign.q"));
        // Test that empty values and null values return false
        assertFalse(FileIO.validateFileLocation(""));
        assertFalse(FileIO.validateFileLocation(null));
        // Test that files that should exist return true with the correct extension
        assertTrue(FileIO.validateFileLocation(Model.getDefaultCurriculumLocation(), ".csv"));
        assertTrue(FileIO.validateFileLocation(Model.getDefaultOfferingsLocation(), ".CSV"));
        assertTrue(FileIO.validateFileLocation(Model.getDefaultPrerequisitesLocation(), ".Csv"));
        // Test that files that should exist return false with the wrong extension
        assertFalse(FileIO.validateFileLocation(Model.getDefaultCurriculumLocation(), ".gg"));
        assertFalse(FileIO.validateFileLocation(Model.getDefaultOfferingsLocation(), ".WRONG"));
        assertFalse(FileIO.validateFileLocation(Model.getDefaultPrerequisitesLocation(), ".Doc"));
        // Test that the overloaded method does not fatal with null or empty values
        assertFalse(FileIO.validateFileLocation(Model.getDefaultCurriculumLocation(), ""));
        assertFalse(FileIO.validateFileLocation(Model.getDefaultOfferingsLocation(), null));
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
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    @Test
    void useDefaultFilesQuery() {

    }

    /**
     * This method tests the overloaded getUserInputFileLocation(nameOfFile, inputStream, outputStream) method.
     * The method in FileIO.java calls upon the overloaded validateFileLocation(location, expectedFileExtension)
     *   method so this method will have few tests (this is because they are already handled by the aforementioned
     *   validation method). This method will primarily test that the proper exceptions are thrown, as well as a
     *   basic test for proper returned values.
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    @Test
    void getUserInputFileLocation() {

    }
}