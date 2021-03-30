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
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    @Test
    void validateFileLocation() {
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