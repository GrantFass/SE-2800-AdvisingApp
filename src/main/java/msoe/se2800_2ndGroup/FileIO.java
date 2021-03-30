package msoe.se2800_2ndGroup;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

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
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Tuesday, 30 March 2021
 */
public class FileIO {

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
        if (location.isBlank() || location.isEmpty()) {
            return false;
        }
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
    public static boolean validateFileLocation(String location, String expectedFileExtension) {
        if (location.isBlank() || location.isEmpty()) {
            return false;
        }
        return location.endsWith(expectedFileExtension) && validateFileLocation(location);
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
    public static boolean useDefaultFilesQuery(InputStream inputStream) {
        //TODO: Test Me
        if (inputStream == null) {
            inputStream = System.in;
        }
        boolean useDefault = true;
        try (Scanner in = new Scanner(inputStream)) {
            System.out.println("Would you like to use the default file location (y/n)?");
            String response = in.nextLine().toLowerCase().trim();
            if (response.equals("n")) {
                useDefault = false;
            }
        }
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
        return useDefaultFilesQuery(System.in);
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
     * @param inputStream the source for the input used to determine if default files should be used.
     *                    The value should usually be System.in. If a null value is entered it should
     *                    default to System.in
     * @throws Model.InvalidInputException if there is a problem validating the file location input by the user
     * @return the path to the file as a String or an error if there is a problem validating the location
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static String getUserInputFileLocation(String nameOfFile, InputStream inputStream, PrintStream outputStream) throws Model.InvalidInputException {
        //TODO: Test Me
        if (inputStream == null) {
            inputStream = System.in;
        }
        String location;
        try (Scanner in = new Scanner(inputStream)) {
            //query user and get file
            outputStream.format("Please enter the location to retrieve the %s file from: ", nameOfFile);
            location = in.nextLine().toLowerCase().trim();
            //validate
            if (!validateFileLocation(location, ".csv")) {
                throw new Model.InvalidInputException("The specified location failed validation");
            }
        }
        if (!location.isEmpty() && !location.isBlank()) {
            return location;
        }
        throw new Model.InvalidInputException("the specified location is empty or blank");
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
     * @throws Model.InvalidInputException if there is a problem validating the file location input by the user
     * @return the path to the file as a String or an error if there is a problem validating the location
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public static String getUserInputFileLocation(String nameOfFile) throws Model.InvalidInputException {
        return getUserInputFileLocation(nameOfFile, System.in, System.out);
    }

}
