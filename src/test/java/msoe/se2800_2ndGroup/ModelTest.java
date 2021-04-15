package msoe.se2800_2ndGroup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

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
=======
 * * testing the methods defined in Model.java
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Create test for the storeMajor method by Grant on Saturday, 20 March 2021
 * * Create tests for the default course data locations by Grant Fass on Fri, 26 Mar 2021
>>>>>>> 8461cec82d2990a806833a435d9e4fc049415e9e
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

    //TODO: Either use this in a future test or remove it.
    @AfterEach
    void tearDown() {
    }

    @Test
    void getCourseRecommendation() {
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
            Assertions.assertEquals("valid input", model.getMajor());
            model.storeMajor("vaLid Input");
            Assertions.assertEquals("vaLid Input", model.getMajor());
            model.storeMajor("Valid Input");
            Assertions.assertEquals("Valid Input", model.getMajor());

            //allow max length of 99 and min length of 1

            model.storeMajor("V");
            Assertions.assertEquals("V", model.getMajor());
            model.storeMajor("vaLid Input that is ninety nine characters long exactly how much longer do I need to type for thiss");
            Assertions.assertEquals("vaLid Input that is ninety nine characters long exactly how much longer do I need to type for thiss", model.getMajor());

            //no spaces
            model.storeMajor("ValidInput");
            Assertions.assertEquals("ValidInput", model.getMajor());

            //Single leading and or trailing space
            model.storeMajor(" Valid Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid Input ");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor(" Valid Input ");
            Assertions.assertEquals("Valid Input", model.getMajor());

            //Triple leading and or trailing space
            model.storeMajor("   Valid Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid Input   ");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("   Valid Input   ");
            Assertions.assertEquals("Valid Input", model.getMajor());

            //Double or Triple Space in Middle
            model.storeMajor("Valid  Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid   Input");
            Assertions.assertEquals("Valid Input", model.getMajor());

            //Underscores
            model.storeMajor("Valid_Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("_Valid _Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid_ Input_");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("_Valid_Input_");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("_Valid_ _Input_");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid___Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("___Valid___Input___");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid Input___");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid___ Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("___Valid ___Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid___ Input___");
            Assertions.assertEquals("Valid Input", model.getMajor());

            //Hyphens
            model.storeMajor("Valid-Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("-Valid -Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid- Input-");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("-Valid-Input-");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("-Valid- -Input-");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid---Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("---Valid---Input---");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid Input---");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid--- Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("---Valid ---Input");
            Assertions.assertEquals("Valid Input", model.getMajor());
            model.storeMajor("Valid--- Input---");
            Assertions.assertEquals("Valid Input", model.getMajor());
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
        assertTrue(Model.getDefaultCurriculumLocation().contains("curriculum.csv"));
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
        assertTrue(Model.getDefaultOfferingsLocation().contains("offerings.csv"));
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
        assertTrue(Model.getDefaultPrerequisitesLocation().contains("prerequisites_updated.csv"));
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

    /**
     * Test that the prerequisites are being returned correctly
     *
     * @author : Claudia Poptile
     * @since : Wed, 14 Apr 2021
     */
    @Test
    void viewPrerequisiteCourses() {
        try {
            model.loadDefaultCourseData();

            //region Invalid courses
            Assertions.assertEquals("No course found.", model.viewPrerequisiteCourses("B1020"));
            Assertions.assertEquals("No course found.", model.viewPrerequisiteCourses("B2020"));
            Assertions.assertEquals("No course found.", model.viewPrerequisiteCourses("C3200"));
            //endregion

            //region Valid courses
            // Ideal inputs - single/and/or prerequisites
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("BI1020"));
            Assertions.assertEquals("Prerequisites: BI102 and CH223", model.viewPrerequisiteCourses("BI2020"));
            Assertions.assertEquals("Prerequisites: CE2812 or EE2930", model.viewPrerequisiteCourses("CE3200"));

            // Capitalization
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("Bi1020"));
            Assertions.assertEquals("Prerequisites: BI102 and CH223", model.viewPrerequisiteCourses("bi2020"));
            Assertions.assertEquals("Prerequisites: CE2812 or EE2930", model.viewPrerequisiteCourses("cE3200"));

            // Single Whitespaces
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("BI 1020"));
            Assertions.assertEquals("Prerequisites: BI102 and CH223", model.viewPrerequisiteCourses("BI20 20"));
            Assertions.assertEquals("Prerequisites: CE2812 or EE2930", model.viewPrerequisiteCourses("C E 3 2 0 0"));
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses(" BI1030 "));

            // Multiple Whitespaces
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("BI   1020"));
            Assertions.assertEquals("Prerequisites: BI102 and CH223", model.viewPrerequisiteCourses("BI20   20"));
            Assertions.assertEquals("Prerequisites: CE2812 or EE2930", model.viewPrerequisiteCourses("C   E   3   2   0   0"));
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("   BI1030   "));

            // Hyphens
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("BI-1020"));
            Assertions.assertEquals("Prerequisites: BI102 and CH223", model.viewPrerequisiteCourses("BI20-20"));
            Assertions.assertEquals("Prerequisites: CE2812 or EE2930", model.viewPrerequisiteCourses("C-E-3-2-0-0"));
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("-BI1030-"));
            Assertions.assertEquals("Prerequisites: CE1901", model.viewPrerequisiteCourses(" -C -E -1 -9 -1 -1 - "));

            // Underscores
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("BI_1020"));
            Assertions.assertEquals("Prerequisites: BI102 and CH223", model.viewPrerequisiteCourses("BI20_20"));
            Assertions.assertEquals("Prerequisites: CE2812 or EE2930", model.viewPrerequisiteCourses("C_E_3_2_0_0"));
            Assertions.assertEquals("Prerequisites: BI1010", model.viewPrerequisiteCourses("_BI1030_"));
            Assertions.assertEquals("Prerequisites: CE1901", model.viewPrerequisiteCourses(" _C _E _1 _9 _1 _1 _ "));



        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        //endregion
    }

    /**
     * Test that the course string is being standardized correctly
     *
     * @author : Claudia Poptile
     * @since : Wed, 14 Apr 2021
     */
    @Test
    void standardizeCourse() {

        // Capitalization
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE2800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("Se2800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("se2800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("sE2800"));

        // Whitespaces
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE 2800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("  SE2800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE2800  "));
        Assertions.assertEquals("SE2800", model.standardizeCourse(" S E  2 8   0     0       "));

        // Punctuation
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE2??800?"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE!2!8!0!!!0"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("-S-E2--800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE_2__800_"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE.2..80..0"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE2,8,,,0,0,"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE(2)8((00"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE2{8}00{"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("//S/E280/0"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE|2||80|0"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE'2''''''8'0'0"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("S$$E2$$8$00"));

        // Mix
        Assertions.assertEquals("SE2800", model.standardizeCourse("SE  -2#.... 800"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("     S-e2+++8{}{}{}{00"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("se28  0????0!"));
        Assertions.assertEquals("SE2800", model.standardizeCourse("s E 2 ----8 0@0~~~~~~~"));

    }
}