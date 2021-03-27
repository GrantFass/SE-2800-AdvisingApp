package msoe.se2800_2ndGroup;

import javafx.application.Platform;

import java.io.File;
import java.util.Scanner;

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
 * * Defining the possible commands for the program
 * * Defining methods that can run on both the CLI and GUI
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Created method to ensure actions run on the FX thread by Grant on Saturday, 20 March 2021
 * * Add Method stubs and basic implementations for loading course data by Grant Fass on Fri, 26 Mar 2021
 *
 * @since : Saturday, 20 March 2021
 * @author : Grant
 * Copyright (C): TBD
 */
public class Model {

    private String major;

    /**
     * This method returns the absolute path to the file in the method header as a String.
     *
     * This method uses the class.getResource method to find the required resource path
     * This method relies on a resource link in pom.xml
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/44241904"}</a>: Help finding the location of the file
     *
     * @return the absolute path to the default location for curriculum.csv as a String
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static String getDefaultCurriculumLocation() {
        return (Model.class.getResource("curriculum.csv")).toString();
    }

    /**
     * This method returns the absolute path to the file in the method header as a String.
     *
     * This method uses the class.getResource method to find the required resource path
     * This method relies on a resource link in pom.xml
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/44241904"}</a>: Help finding the location of the file
     *
     * @return the absolute path to the default location for curriculum.csv as a String
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static String getDefaultOfferingsLocation() {
        return (Model.class.getResource("offerings.csv")).toString();
    }

    /**
     * This method returns the absolute path to the file in the method header as a String.
     *
     * This method uses the class.getResource method to find the required resource path
     * This method relies on a resource link in pom.xml
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/44241904"}</a>: Help finding the location of the file
     *
     * @return the absolute path to the default location for curriculum.csv as a String
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static String getDefaultPrerequisitesLocation() {
        return (Model.class.getResource("prerequisites_updated.csv")).toString();
    }

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
     * This method does not update the GUI directly so it does not need to call ensureFXThread
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public String getCourseRecommendation() {
        //TODO: FIXME
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
        //TODO: write method

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
     * This method loads all of the course data
     *
     * This model loads the three required CSV files.
     * The method queries the user to determine if the default CSV file locations should be used or if
     * custom locations should be used.
     * If custom locations are used then the specified input file paths are validated
     * Each CSV file is then read into the program
     *
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public void loadCourseData() throws InvalidInputException {
        //TODO: Test Me
        /*
        Steps:
        1. get file locations
            a. if default files then set the paths to the defaults
            b. if not default then query the user for the path to each file
                i. validate each of the file locations
                    1. throw error if file is not valid?
                ii. set the paths to the validated files
        2. read in files
            a. call the loader classes with the specified locations
         */
        boolean useDefaultFiles = useDefaultFilesQuery();
        String curriculumLocation = getDefaultCurriculumLocation();
        String offeringsLocation = getDefaultOfferingsLocation();
        String prerequisitesLocation = getDefaultPrerequisitesLocation();
        if (!useDefaultFiles) {
            //get new locations and validate
            curriculumLocation = getUserInputFileLocation("curriculum.csv");
            offeringsLocation = getUserInputFileLocation("offerings.csv");
            prerequisitesLocation = getUserInputFileLocation("prerequisites.csv");
        }
        //read files
        //TODO: Read Files
    }


    /**
     * This method validates that the input location for a file matches specified criteria
     *
     * This method validates that the input location is valid by attempting to turn the location into
     * a file then verifying that the file exists
     *
     * @param location the path to the file as a String
     * @return True if the location passes validation, false otherwise
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    private boolean validateFileLocation(String location) {
        //TODO: Test Me
        File file = new File(location);
        return file.exists();
    }

    /**
     * This method validates that the input location for a file matches specified criteria
     *
     * This method validates that the input location is valid by attempting to turn the location into
     * a file then verifying that the file exists
     * This overloaded method additionally checks that the file extension matches the input extension
     *
     * @param location the path to the file as a String
     * @param expectedFileExtension the extension of the file that is expected.
     * @return True if the location passes validation, false otherwise
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    private boolean validateFileLocation(String location, String expectedFileExtension) {
        //TODO: Test Me
        return validateFileLocation(location) && location.endsWith(expectedFileExtension);
    }

    /**
     * This method determines if the default file location should be used or not
     *
     * This method queries the user about the file source to use
     * The method then retrieves user input.
     *
     * @return true if default files should be used, false otherwise
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    private boolean useDefaultFilesQuery() {
        //TODO: Test Me
        boolean useDefault = true;
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Would you like to use the default file location (y/n)?");
            String response = in.nextLine().toLowerCase().trim();
            if (response.equals("n")) {
                useDefault = false;
            }
        }
        return useDefault;
    }

    /**
     * This method queries the user for the location of a specified file and returns it
     *
     * This method uses the standard input and output streams for the system to query the user to retrieve
     * the location of the file specified by the parameter String in the method header.
     * The location of the user input file is then validated.
     * If the validation fails then an Exception is thrown.
     *
     * @param nameOfFile the name of the file to query the user to enter the location for
     * @throws InvalidInputException if there is a problem validating the file location input by the user
     * @return the path to the file as a String or an error if there is a problem validating the location
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    private String getUserInputFileLocation(String nameOfFile) throws InvalidInputException {
        //TODO: Test Me
        String location = "";
        try (Scanner in = new Scanner((System.in))) {
            //query user and get file
            System.out.format("Please enter the location to retrieve the %s file from: ", nameOfFile);
            location = in.nextLine().toLowerCase().trim();
            //validate
            if (!validateFileLocation(location, ".csv")) {
                throw new InvalidInputException("The specified location failed validation");
            }
        }
        if (!location.isEmpty() && !location.isBlank()) {
            return location;
        }
        throw new InvalidInputException("the specified location is empty or blank");
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
