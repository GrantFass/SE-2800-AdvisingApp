package msoe.se2800_2ndGroup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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

    @Test
    void getCourseRecommendation() {
    }

    @Test
    void testGetCourseRecommendation() {
        //TODO
    }

    @Test
    void getCurricula() {

        //TODO
    }

    @Test
    void getOfferings() {

        //TODO
    }

    @Test
    void getPrerequisiteCourses() {

        //TODO
    }
}