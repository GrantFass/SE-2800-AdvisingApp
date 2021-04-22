package msoe.se2800_2ndGroup.Exceptions;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: CustomExceptions
 * Description:
 * * Custom Exceptions for use in the program
 * The CustomExceptions class is responsible for:
 * * Defining the custom exceptions used throughout the program
 * Modification Log:
 * * File Created by Grant on Thursday, 22 April 2021
 * * Moved custom exception inner classes from Model.java to CustomExceptions.java by Grant Fass on Thu, 22 Apr 2021
 *
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 22 April 2021
 */
public class CustomExceptions {

    /**
     * This class creates a custom checked exception for invalid input
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.baeldung.com/java-new-custom-exception"}</a>: Help creating custom exceptions
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static class InvalidInputException extends Exception {
        public InvalidInputException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * This class creates a custom checked exception to be used whenever the specified major is invalid.
     * Extends InvalidInputException
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static class InvalidMajorException extends InvalidInputException {
        public InvalidMajorException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * This class creates a custom checked exception to be used whenever the specified offerings is invalid.
     * Extends InvalidInputException
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static class InvalidOfferingsException extends InvalidInputException {
        public InvalidOfferingsException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * This class creates a custom checked exception to be used whenever the specified curricula is invalid.
     * Extends InvalidInputException
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static class InvalidCurriculaException extends InvalidInputException {
        public InvalidCurriculaException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * This class creates a custom checked exception to be used whenever the specified prerequisites is invalid.
     * Extends InvalidInputException
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static class InvalidPrerequisitesException extends InvalidInputException {
        public InvalidPrerequisitesException(String errorMessage) {
            super(errorMessage);
        }
    }

    /**
     * This class creates a custom checked exception to be used whenever the specified transcript courses is invalid.
     * Extends InvalidInputException
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static class InvalidTranscriptException extends InvalidInputException {
        public InvalidTranscriptException(String errorMessage) {
            super(errorMessage);
        }
    }
}
