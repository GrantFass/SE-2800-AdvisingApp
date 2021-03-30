package msoe.se2800_2ndGroup;

import javafx.application.Platform;
import msoe.se2800_2ndGroup.loaders.CurriculumLoader;
import msoe.se2800_2ndGroup.loaders.OfferingsLoader;
import msoe.se2800_2ndGroup.loaders.PrerequisitesLoader;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.Offering;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static msoe.se2800_2ndGroup.FileIO.getUserInputFileLocation;
import static msoe.se2800_2ndGroup.FileIO.useDefaultFilesQuery;

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
 * * Update course data loading implementation with changes from Hunter T. yesterday by Grant Fass on Tue, 30 Mar 2021
 * * Transferred methods from Model.java to FileIO.java by Grant Fass on Tue, 30 Mar 2021
 * @since : Saturday, 20 March 2021
 * @author : Grant
 * Copyright (C): TBD
 */
public class Model {

    // Variable to store the major of the user
    private String major;
    // Variables to store the course data
    private Collection<Curriculum> curricula;
    private Collection<Offering> offerings;
    private Collection<Course> prerequisiteCourses;

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
        return (Model.class.getResource("curriculum.csv")).toString().replace("file:/", "");
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
        return (Model.class.getResource("offerings.csv")).toString().replace("file:/", "");
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
        return (Model.class.getResource("prerequisites_updated.csv")).toString().replace("file:/", "");
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
     * This method returns the curriculum when called.
     * This method will return an empty ArrayList whenever curricula has not been loaded
     * @return the curricula stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     * TODO: FIX METHOD SIGNATURE
     */
    public Collection<Curriculum> getCurricula() {
        if (curricula == null) {
            return new ArrayList<>();
        }
        return curricula;
    }

    /**
     * This method returns the course offerings when called.
     * This method will return an empty ArrayList whenever the offerings have not been loaded
     * @return the course offerings stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     * TODO: FIX METHOD SIGNATURE
     */
    public Collection<Offering> getOfferings() {
        if (offerings == null || offerings.isEmpty()) {
            return new ArrayList<>();
        }
        return offerings;
    }

    /**
     * This method returns the prerequisite courses when called.
     * This method will return an empty ArrayList whenever the prerequisite courses have not been loaded
     * @return the prerequisite courses stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     * TODO: FIX METHOD SIGNATURE
     */
    public Collection<Course> getPrerequisiteCourses() {
        if (prerequisiteCourses == null || prerequisiteCourses.isEmpty()) {
            return new ArrayList<>();
        }
        return prerequisiteCourses;
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
     * This method loads all of the course data
     *
     * This model loads the three required CSV files.
     * The method queries the user to determine if the default CSV file locations should be used or if
     * custom locations should be used.
     * If custom locations are used then the specified input file paths are validated
     * Each CSV file is then read into the program through the use of Loader objects that utilize the
     * Apache Commons-CSV library.
     * Note that this method calls static methods from FileIO.java class
     *
     * @throws InvalidInputException when there is an issue with the user input locations for the files
     * @throws IOException if there is an issue reading in the CSV files
     * @author : Grant Fass
     * @since : Fri, 26 Mar 2021
     */
    public void loadCourseData() throws InvalidInputException, IOException {
        //TODO: Test Me
        //Ask the user if default file locations should be used or if a custom location should be used
        boolean useDefaultFiles = useDefaultFilesQuery();
        //set the locations to the default
        String curriculumLocation = getDefaultCurriculumLocation();
        String offeringsLocation = getDefaultOfferingsLocation();
        String prerequisitesLocation = getDefaultPrerequisitesLocation();
        //if the user wants to use custom locations then query them to retrieve the locations and validate the files
        if (!useDefaultFiles) {
            //get new locations and validate
            curriculumLocation = getUserInputFileLocation("curriculum.csv");
            offeringsLocation = getUserInputFileLocation("offerings.csv");
            prerequisitesLocation = getUserInputFileLocation("prerequisites.csv");
        }
        //Create the loaders used to read in the files
        Collection<Course> courses = new ArrayList<>(); //TODO: Fix Me
        CurriculumLoader curriculumLoader = new CurriculumLoader(new FileReader(curriculumLocation), courses);
        OfferingsLoader offeringsLoader = new OfferingsLoader(new FileReader(offeringsLocation), courses);
        PrerequisitesLoader prerequisitesLoader = new PrerequisitesLoader(new FileReader(prerequisitesLocation));
        //Read in the files
        curricula = curriculumLoader.load();
        offerings = offeringsLoader.load();
        prerequisiteCourses = prerequisitesLoader.load();
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
