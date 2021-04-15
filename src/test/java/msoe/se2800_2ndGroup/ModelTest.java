package msoe.se2800_2ndGroup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: ModelTest
 * Description:
 * * This class contains tests for the Model.java class
 * The ModelTest class is responsible for:
<<<<<<< HEAD
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * testing the methods defined in Model.java
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Create test for the storeMajor method by Grant on Saturday, 20 March 2021
 * * Create tests for the default course data locations by Grant Fass on Fri, 26 Mar 2021
 * * Fix Store Major Test by Grant Fass on Thu, 15 Apr 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Saturday, 20 March 2021
 */
class ModelTest {
    Model model;

    /**
     * Creates a new Model instance before each test is run and stores it.
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    @BeforeEach
    void setUp() {
        model = new Model();
    }

    /**
     * This method tests that the store major method is handling input correctly
     *
     * This method calls the storeMajor method from an instance of Model then gets the major to verify if it stored or not and if so that it stored correctly.
     * Invalid inputs are null, only numbers, empty, contain numbers, contain special chars, or any combination
     * Valid inputs should not contain double spaces, start or end in spaces, and should replace underscores and hyphens with spaces. Also tests max and min length strings
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://howtodoinjava.com/junit5/expected-exception-example/"}</a>: Help Creating AssertThrows tests
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    @Test
    void storeMajor() {
        //region Invalid Inputs
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor(""));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor(null));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("Invalid Input1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1Invalid Input"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("Inva1lid Input"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1Invalid Input1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1Inva31id In4put1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1In4va35641id In4pu55t1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("!*(Begins in Special Chars"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("Ends in Special Char%%$"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("Inval$$id Inp&*u(t"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1In4)--va3$5641id In4p(u55t1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("This string is invalid because it is longer than one hundred characters long and I need to keep typing"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("This string is invalid because it is exactly one hundred characters long and I need to keep typinggg"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("1This string is invalid be3cause it is exactly one hu1ndred characters long and it contains numbers1"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("This str2ing 2 is 1invalid because it is longer than one hu12ndred characters long and It contains numbers123"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("This str2ing 2 is 1i+nvalid because it is longer than one hu12ndred characters long and It contains $specia%l characte^rs and n*umbers"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("(Th#is str2ing 2 is 1invalid its exactly 100 charac@ters long co^ntains num&bers with special chars%"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("!This st@ring is# exact$ly one hundered charac)ters an>d cont&ains specia.l charac^ters too typing %"));
        Assertions.assertThrows(Model.InvalidInputException.class, () -> model.storeMajor("!This st@ring is# ov$er one hundered charac)ters an>d cont&ains specia.l charac^ters too keep longer typing %"));
        //endregion

        //region Valid Inputs
        try {
            //Capitalization
            model.storeMajor("valid input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("vaLid Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());

            //allow max length of 99 and min length of 1

            model.storeMajor("V");
            Assertions.assertEquals("V", model.getMajor());
            model.storeMajor("vaLid Input that is ninety nine characters long exactly how much longer do I need to type for thiss");
            Assertions.assertEquals("VALID INPUT THAT IS NINETY NINE CHARACTERS LONG EXACTLY HOW MUCH LONGER DO I NEED TO TYPE FOR THISS", model.getMajor());

            //no spaces
            model.storeMajor("ValidInput");
            Assertions.assertEquals("VALIDINPUT", model.getMajor());

            //Single leading and or trailing space
            model.storeMajor(" Valid Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid Input ");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor(" Valid Input ");
            Assertions.assertEquals("VALID INPUT", model.getMajor());

            //Triple leading and or trailing space
            model.storeMajor("   Valid Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid Input   ");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("   Valid Input   ");
            Assertions.assertEquals("VALID INPUT", model.getMajor());

            //Double or Triple Space in Middle
            model.storeMajor("Valid  Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid   Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());

            //Underscores
            model.storeMajor("Valid_Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("_Valid _Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid_ Input_");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("_Valid_Input_");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("_Valid_ _Input_");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid___Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("___Valid___Input___");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid Input___");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid___ Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("___Valid ___Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid___ Input___");
            Assertions.assertEquals("VALID INPUT", model.getMajor());

            //Hyphens
            model.storeMajor("Valid-Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("-Valid -Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid- Input-");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("-Valid-Input-");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("-Valid- -Input-");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid---Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("---Valid---Input---");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid Input---");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid--- Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("---Valid ---Input");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
            model.storeMajor("Valid--- Input---");
            Assertions.assertEquals("VALID INPUT", model.getMajor());
        } catch (Exception e) {
            //These should already be handled with the previous invalid inputs thus should fail.
            System.out.println(e.getMessage());
            fail();
        }
        //endregion
    }

    /**
     * Test that the default location for the file is returning correctly
     *
     * This method tests that the returned value is not null, thus implying that the file exists
     *
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    @Test
    void getDefaultCurriculumLocation() {
        assertNotNull(Model.getDefaultCurriculumLocation());
        assertTrue(Model.getDefaultCurriculumLocation().contains("/msoe/se2800_2ndGroup/curriculum.csv"));
    }

    /**
     * Test that the default location for the file is returning correctly
     *
     * This method tests that the returned value is not null, thus implying that the file exists
     *
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    @Test
    void getDefaultOfferingsLocation() {
        assertNotNull(Model.getDefaultOfferingsLocation());
        assertTrue(Model.getDefaultOfferingsLocation().contains("/msoe/se2800_2ndGroup/offerings.csv"));
    }

    /**
     * Test that the default location for the file is returning correctly
     *
     * This method tests that the returned value is not null, thus implying that the file exists
     *
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    @Test
    void getDefaultPrerequisitesLocation() {
        assertNotNull(Model.getDefaultPrerequisitesLocation());
        assertTrue(Model.getDefaultPrerequisitesLocation().contains("/msoe/se2800_2ndGroup/prerequisites_updated.csv"));
    }

    /**
     * Test that proper error message is thrown when the major is missing or the offerings list is empty. These errors
     * should match those thrown by verifyMajor() and verifyOfferings().
     * Test that no errors are thrown for all possible combinations of boolean values as method parameters
     * Test that the expected output matches the actual output for each term for the default data.
     */
    @Test
    void getCourseOfferingsAsString() {
        //Test that error is thrown when no offerings and no major have been loaded
        try {
            model.getCourseOfferingsAsString(false, true, false);
        } catch (Model.InvalidInputException e) {
            assertEquals("The specified major is missing or blank", e.getMessage());
        }
        //Test that error is still thrown when no offerings have been loaded but major has
        try {
            model.storeMajor("CS");
            model.getCourseOfferingsAsString(false, true, false);
        } catch (Model.InvalidInputException e) {
            assertEquals("There are no offerings loaded right now", e.getMessage());
        }
        try {
            //load course data so that verification of offerings does not throw errors
            model.loadDefaultCourseData();
            //test that does not throw error for various variable combinations
            model.getCourseOfferingsAsString(false, false, false);
            model.getCourseOfferingsAsString(false, false, true);
            model.getCourseOfferingsAsString(false, true, false);
            model.getCourseOfferingsAsString(false, true, true);
            model.getCourseOfferingsAsString(true, false, false);
            model.getCourseOfferingsAsString(true, false, true);
            model.getCourseOfferingsAsString(true, true, false);
            model.getCourseOfferingsAsString(true, true, true);
            //test that method outputs that there are no terms selected if all input is false
            assertEquals("No Terms Selected\n", model.getCourseOfferingsAsString(false, false, false));
            //test that method output matches the expected output for each term
            String expectedFallOutput = "   CODE CR |                              DESCRIPTION : PREREQUISITES\n" +
                    " BA2220  3 |        Foundations of Business Economics : NullPrerequisite()\n" +
                    " CS1011  4 |                   Software Development I : NullPrerequisite()\n" +
                    " CS2911  4 |                        Network Protocols : OrPrerequisite[left=SinglePrerequisite[code=CS1011], right=SinglePrerequisite[code=SE1011]]\n" +
                    " CS3040  4 |                    Programming Languages : SinglePrerequisite[code=CS2040]\n" +
                    " CS3400  4 |                         Machine Learning : AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=MA383]]\n" +
                    " CS3860  4 |                         Database Systems : AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=MA2310]]\n" +
                    " CS4000  3 |                          Senior Design I : SinglePrerequisite[code=SR]\n" +
                    " GS1001  4 |                       Freshman Studies I : NullPrerequisite()\n" +
                    "  MA136  4 |                               Calculus I : NullPrerequisite()\n" +
                    " MA2323  4 |                              Calculus IV : SinglePrerequisite[code=MA2314]\n" +
                    " MA2310  3 |                   Discrete Mathematics I : SinglePrerequisite[code=SO]\n" +
                    "  MA262  3 |               Probability and Statistics : SinglePrerequisite[code=MA137]\n" +
                    " SE2030  3 | Software Engineering Tools and Practices : SinglePrerequisite[code=CS2852]\n";
            String expectedWinterOutput = "   CODE CR |                              DESCRIPTION : PREREQUISITES\n" +
                    " CS1021  4 |                  Software Development II : OrPrerequisite[left=SinglePrerequisite[code=CS1011], right=SinglePrerequisite[code=SE1011]]\n" +
                    " CS2300  4 |                    Computational Science : OrPrerequisite[left=SinglePrerequisite[code=CS1021], right=SinglePrerequisite[code=SE1021]]\n" +
                    " CS2711  4 |                    Computer Organization : OrPrerequisite[left=SinglePrerequisite[code=CS1011], right=SinglePrerequisite[code=SE1011]]\n" +
                    " CS3300  4 |                             Data Science : AndPrerequisite[left=SinglePrerequisite[code=CS3400], right=SinglePrerequisite[code=MA262]]\n" +
                    " CS3840  4 |                        Operating Systems : AndPrerequisite[left=SinglePrerequisite[code=CS2711], right=OrPrerequisite[left=SinglePrerequisite[code=CS2040], right=SinglePrerequisite[code=SE2040]]]\n" +
                    " CS4010  3 |                         Senior Design II : SinglePrerequisite[code=SR]\n" +
                    " GS1002  4 |                      Freshman Studies II : NullPrerequisite()\n" +
                    "  HU432  3 | Ethics for Professional Managers and Engineers : SinglePrerequisite[code=JR]\n" +
                    "  MA137  4 |                              Calculus II : SinglePrerequisite[code=MA136]\n" +
                    " MA3320  3 |                  Discrete Mathematics II : SinglePrerequisite[code=MA2310]\n" +
                    "  OR402  1 |                    Professional Guidance : SinglePrerequisite[code=JR]\n" +
                    " PH2011  4 |                    Physics I - Mechanics : SinglePrerequisite[code=MA136]\n" +
                    " SE2811  4 |                Software Component Design : AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=SE2030]]\n" +
                    " SE2840  4 |              Web Application Development : AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=CS2911]]\n";
            String expectedSpringOutput = "   CODE CR |                              DESCRIPTION : PREREQUISITES\n" +
                    " BA3444  3 |   Organizational Behavior and Leadership : SinglePrerequisite[code=SO]\n" +
                    " CS2040  4 |                 Programming in C and C++ : SinglePrerequisite[code=CS2852]\n" +
                    " CS2400  3 |  Introduction to Artificial Intelligence : AndPrerequisite[left=AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=CS2300]], right=SinglePrerequisite[code=MA2310]]\n" +
                    " CS2852  4 |                          Data Structures : OrPrerequisite[left=SinglePrerequisite[code=CS1021], right=SinglePrerequisite[code=SE1021]]\n" +
                    " CS3310  4 |                   Data Science Practicum : SinglePrerequisite[code=CS3300]\n" +
                    " CS3851  4 |                               Algorithms : AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=MA3320]]\n" +
                    " CS3450  4 |                            Deep Learning : SinglePrerequisite[code=CS3400]\n" +
                    " CS4020  3 |                        Senior Design III : SinglePrerequisite[code=SR]\n" +
                    " GS1003  4 |                     Freshman Studies III : NullPrerequisite()\n" +
                    " MA2314  4 |                             Calculus III : SinglePrerequisite[code=MA137]\n" +
                    "  MA383  3 |                           Linear Algebra : OrPrerequisite[left=OrPrerequisite[left=SinglePrerequisite[code=MA231], right=SinglePrerequisite[code=MA225]], right=SinglePrerequisite[code=MA3501]]\n" +
                    " PH2021  4 |            Physics II - ElectroMagnetism : AndPrerequisite[left=SinglePrerequisite[code=PH2011], right=SinglePrerequisite[code=MA137]]\n" +
                    " SE2800  3 |           Software Engineering Process I : AndPrerequisite[left=SinglePrerequisite[code=CS2852], right=SinglePrerequisite[code=SE2030]]\n";

            assertEquals(expectedSpringOutput, model.getCourseOfferingsAsString(false, false, true));
            assertEquals(expectedWinterOutput, model.getCourseOfferingsAsString(false, true, false));
            assertEquals(expectedFallOutput, model.getCourseOfferingsAsString(true, false, false));
        } catch (Model.InvalidInputException | IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void loadUnofficialTranscript() {

    }

    @Test
    void getCourseRecommendation() {

    }

    @Test
    void loadDefaultCourseData() {

    }

    @Test
    void loadCourseData() {

    }

    @Test
    void getCourseGraph() {

    }

    @Test
    void getVirtualCourses() {

    }
}