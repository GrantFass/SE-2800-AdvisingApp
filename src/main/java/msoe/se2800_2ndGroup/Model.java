package msoe.se2800_2ndGroup;

import javafx.application.Platform;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: Model
 * Description:
 * * This class runs operations this way they are supported by both the GUI and CLI
 * The Model class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Created method to ensure actions run on the FX thread by Grant on Saturday, 20 March 2021
 *
 * @since : Saturday, 20 March 2021
 * @author : Grant
 * Copyright (C): TBD
 */
public class Model {

    String major;

    /**
     * This method runs the specified action or method on the FX thread to avoid errors.
     * Run methods by calling with the following format: ensureFXThread(() -> method());
     *
     * This method runs tasks on the FX thread using Platform.isFXApplicationThread and Platform.runLater
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/49676738"}</a> Help setting up method
     *
     * @param action the method to run on the FX thread
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    private void ensureFXThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    /**
     * This method exits the program
     *
     * This method runs the System.exit command with a status of 0
     * This method makes sure to execute on the FX thread using the ensureFXThread method
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/21962055"}</a> Help exiting program through if statement
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void exitProgram() {
        ensureFXThread(() -> System.exit(0));
    }

    /**
     * This method process course recommendations and returns them as a String
     *
     * <Internal Operation Notes written in 3rd person declarative>
     *     This method does not update the GUI directly so it does not need to call ensureFXThread
     *
     * Sources:
     *  <a href="#{@link}">{@link "URL"}</a>: <description of help
     *  <Student name>: <description of help>
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public String getCourseRecommendation() {
        //TODO: update errors
        if (major.isBlank() || major.isEmpty()) {
            //TODO: throw error if major is empty
        }
        else if (false) {
            //TODO: throw error if course data empty
        }
        else if (false) {
            //TODO: throw error if transcript is empty
        }


        return "";
    }

    /**
     * This method stores the input text as a major if it is valid
     *
     * This method checks that the major only contains characters a-z case insensitive between 0 and 100 times also allowing whitespace.
     * If the input string matches the regex then it is stored, otherwise an error is thrown
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/48523095"}</a>: Help Finding Regex
     *
     * @param major the major to store
     * @throws InvalidInputException if the specified input for major does not match the regex.
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void storeMajor(String major) throws InvalidInputException {
        if (major == null) {
            throw new InvalidInputException("The specified input for major was null");
        } else if (major.isEmpty() || major.isBlank()) {
            throw new InvalidInputException("The specified input for major was empty or blank");
        } else {
            //change hyphens and underscores to spaces, change double spaces to single spaces, trim spaces off start and end.
            String input = major.replaceAll("_", " ").replaceAll("-", " ").trim();
            while (input.contains("  ")) {
                input = input.replaceAll("[\\s]{2}", " ");
            }
            if (!input.matches("[a-zA-Z\\s]{1,99}")) {
                throw new InvalidInputException("The specified input for major {" + major + "} did not match the expected pattern: /^[a-zA-Z\\s]{1,99}$");
            } else {
                this.major = input;
            }
        }
    }

    /**
     * This method returns the current major
     *
     * This method returns the current value stored in the class instance variable for major
     *
     * @return the current major
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public String getMajor() {
        return this.major;
    }

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
}
