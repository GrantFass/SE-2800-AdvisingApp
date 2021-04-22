package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: FileIO
 * Description:
 * * This class contains methods used for various FileIO functions.
 * * Many of these methods were previously private in Model.java
 * * This was overcomplicating the class and testing so they are now
 * * static methods in this class.
 * * Creation inspired by <a href="#{@link}">{@link "https://stackoverflow.com/a/52054"}</a>
 * The FileIO class is responsible for:
 * * Holding static methods related to user input and output
 * Modification Log:
 * * File Created by Grant on Tuesday, 30 March 2021
 * * Transferred methods from Model.java to FileIO.java by Grant Fass on Tue, 30 Mar 2021
 * * Create new method to query user to use default file location that is passed a scanner
 * * Add logger by Grant Fass on Thu, 15 Apr 2021
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Tuesday, 30 March 2021
 */
public class FileIO {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

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
    public static boolean validateFileLocation(String location) {
        LOGGER.finer("Validating the file from the input location: " + location);
        if (!validateLocation(location)) {
            LOGGER.fine("The specified file location is null, blank, or missing and failed validation");
            return false;
        }
        File file = new File(location);
        boolean isValid = file.exists();
        LOGGER.fine("The specified file location was validated and " + (isValid ? "passed validation" : "failed validation"));
        return isValid;
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
    public static boolean validateFileLocation(String location, String expectedFileExtension) {
        LOGGER.finer("Validating the file from the input location: " + location + " with the specified file extension: " + expectedFileExtension);
        if (!validateLocation(location) || expectedFileExtension == null) {
            LOGGER.fine("The specified file location or extension was null, blank, or missing and failed validation");
            return false;
        }
        boolean isValid = location.toLowerCase().endsWith(expectedFileExtension.toLowerCase()) && validateFileLocation(location);
        LOGGER.fine("The specified file location was validated with the expected file extension and " + (isValid ? "passed validation" : "failed validation"));
        return isValid;
    }

    /**
     * This method determines if the default file location should be used or not
     *
     * This method queries the user about the file source to use
     * The method then retrieves user input from the passed in scanner
     *
     * @param in the scanner used to query the user
     * @return true if default files should be used, false otherwise
     * @author : Grant Fass
     * @since : Thu, 1 Apr 2021
     */
    public static boolean useDefaultFilesQuery(Scanner in) {
        boolean useDefault = true;
        System.out.println("Would you like to use the default file location (y/n)?");
        String response = in.hasNext() ? in.nextLine().toLowerCase().trim() : "";
        if (response.equals("n") || response.equals("no")) {
            useDefault = false;
        }
        LOGGER.fine(useDefault ? "Using default file location" : "Not using default file location");
        return useDefault;
    }

    /**
     * This method determines if the default file location should be used or not
     *
     * This method queries the user about the file source to use
     * The method then retrieves user input from the specified input source
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/6416179"}</a>: Helped determine how to
     *              pass in an input stream to better facilitate testing.
     *
     * @param inputStream the source for the input used to determine if default files should be used.
     *                    The value should usually be System.in. If a null value is entered it should
     *                    default to System.in
     * @return true if default files should be used, false otherwise
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static boolean useDefaultFilesQuery(InputStream inputStream, PrintStream outputStream) {
        if (inputStream == null) {
            inputStream = System.in;
        }
        boolean useDefault = true;
        try (Scanner in = new Scanner(inputStream)) {
            outputStream.println("Would you like to use the default file location (y/n)?");
            String response = in.hasNext() ? in.nextLine().toLowerCase().trim() : "";
            if (response.equals("n") || response.equals("no")) {
                useDefault = false;
            }
        }
        LOGGER.fine(useDefault ? "Using default file location" : "Not using default file location");
        return useDefault;
    }

    /**
     * This method determines if the default file location should be used or not
     *
     * This method queries the user about the file source to use
     * The method then retrieves user input from System.in.
     * This version of the method calls the more verbose option by passing in the
     * default input source.
     *
     * @return true if default files should be used, false otherwise
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    public static boolean useDefaultFilesQuery() {
        return useDefaultFilesQuery(System.in, System.out);
    }

    /**
     * This method queries the user for the location of a specified file and returns it
     *
     * This method uses the passed in scanner to query the user to retrieve
     * the location of the file specified by the parameter String in the method header.
     * The location of the user input file is then validated.
     * If the validation fails then an Exception is thrown.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/6416179"}</a>: Helped determine how to
     *              pass in an input stream to better facilitate testing.
     *
     * @param nameOfFile the name of the file to query the user to enter the location for
     * @param in An existing scanner to use to query the user for input
     * @param out output stream to print to
     * @throws CustomExceptions.InvalidInputException if there is a problem validating the file location input by the user
     * @return the path to the file as a String or an error if there is a problem validating the location
     * @author : Grant Fass
     * @since : Thu, 1 Apr 2021
     */
    public static String getUserInputFileLocation(String nameOfFile, String fileExtension, Scanner in, PrintStream out) throws CustomExceptions.InvalidInputException {
        LOGGER.fine("Getting user input file location");
        String location = "";
        //query user and get file
        out.format("Please enter the location to retrieve the %s file from: ", nameOfFile);
        location = in.nextLine().trim();
        //validate
        if (!validateFileLocation(location, fileExtension)) {
            LOGGER.warning("The specified location has failed validation");
            throw new CustomExceptions.InvalidInputException("The specified location failed validation");
        }
        if (validateLocation(location)) {
            return location;
        }
        LOGGER.warning("The specified location was empty or blank");
        throw new CustomExceptions.InvalidInputException("the specified location is empty or blank");
    }

    /**
     * This method queries the user for the location of a specified file and returns it
     *
     * This method uses the standard input and output streams for the system to query the user to retrieve
     * the location of the file specified by the parameter String in the method header.
     * The location of the user input file is then validated.
     * If the validation fails then an Exception is thrown.
     * This version of the method takes in an input stream as a parameter to better facilitate testing.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/6416179"}</a>: Helped determine how to
     *              pass in an input stream to better facilitate testing.
     *
     * @param nameOfFile the name of the file to query the user to enter the location for
     * @param fileExtension the extension of the file to use / verify
     * @param inputStream the source for the input used to determine if default files should be used.
     *                    The value should usually be System.in. If a null value is entered it should
     *                    default to System.in
     * @param outputStream the source for the text to be output to
     * @throws CustomExceptions.InvalidInputException if there is a problem validating the file location input by the user
     * @return the path to the file as a String or an error if there is a problem validating the location
     * @author : Grant Fass, Hunter Turcin
     * @since : Fri, 26 Mar 2021
     */
    public static String getUserInputFileLocation(String nameOfFile, String fileExtension, InputStream inputStream, PrintStream outputStream) throws CustomExceptions.InvalidInputException {
        try (final var in = new Scanner(inputStream)) {
            return getUserInputFileLocation(nameOfFile, fileExtension, in, outputStream);
        }
    }

    /**
     * This method queries the user for the location of a specified file and returns it
     *
     * This method uses the standard input and output streams for the system to query the user to retrieve
     * the location of the file specified by the parameter String in the method header.
     * The location of the user input file is then validated.
     * If the validation fails then an Exception is thrown.
     * This version of the method calls the more verbose option by passing in
     * the default input source.
     *
     * @param nameOfFile the name of the file to query the user to enter the location for
     * @param fileExtension the extension of the file to use / verify
     * @throws CustomExceptions.InvalidInputException if there is a problem validating the file location input by the user
     * @return the path to the file as a String or an error if there is a problem validating the location
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static String getUserInputFileLocation(String nameOfFile, String fileExtension) throws CustomExceptions.InvalidInputException {
        return getUserInputFileLocation(nameOfFile, fileExtension, System.in, System.out);
    }

    /**
     * Verify a location is not null or blank.
     *
     * @param location location to verify
     * @return true if well-formed
     * @author : Hunter Turcin
     * @since : Tue, 20 Apr 2021
     */
    private static boolean validateLocation(String location) {
        return !(location == null || location.isBlank());
    }


}
