package msoe.se2800_2ndGroup;

import javafx.application.Platform;
import msoe.se2800_2ndGroup.loaders.CurriculumLoader;
import msoe.se2800_2ndGroup.loaders.OfferingsLoader;
import msoe.se2800_2ndGroup.loaders.PrerequisitesLoader;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.Offering;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;

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
 * * Create new method to load course data that is passed a scanner by Grant Fass on Tue, 30 Mar 2021
 * * Create method to load course data on startup by Grant Fass on Tue, 6 Apr 2021
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
        if (offerings == null) {
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
        if (prerequisiteCourses == null) {
            return new ArrayList<>();
        }
        return prerequisiteCourses;
    }

    /**
     * This method process course recommendations and returns them as a String
     *
     * This method does not update the GUI directly so it does not need to call ensureFXThread
     *
     * @throws InvalidInputException when the major does not exist in the list of offerings
     * @return //TODO
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public String getCourseRecommendation() throws InvalidInputException {
        /*
        Going to need to read through each line in the offerings data structure
        each offering has a course and a majorAvailability
        each course has the following:
            a code as a String: EX. "CS2400"
            credits as an int: EX. 3
            prerequisite as nested AndPrerequisites and OrPrerequisites which eventually lead to SinglePrerequisites which give the course code
            description as a String: EX. "Introduction to Artificial Intelligence"
        majorAvailability is a hash map.
            the keys are strings for each of the majors: EX. "EE"
            the values are Term objects.
                each term has an id and a season. both formatted as Strings
                if the term id is blank, or the season is never then the course is never available for the specified major
                otherwise the id should match the season: EX. id="3", season="Spring"
         */

        /*
        TODO Steps:
        0. verify that a major is stored at the moment
        1. go through entire list of offerings
             a. store the offerings available to the stored major by the term they are available for.
        2. remove courses that are already completed according to the transcript
        3. remove any courses that prerequisites are not satisfied for (ADD TOGGLE POSSIBLY)
        4. Build outputs
            prioritize courses that are required for a lot of others
         */
        ArrayList<String> potentialMajors = new ArrayList<>(Arrays.asList("EE", "BSE PT", "CE", "UX", "AE", "NU", "CS", "AS", "SE", "MIS", "ME", "BME", "IE", "ME A"));
        //TODO: FIXME && TEST_ME
        //TODO: update errors
        if (major == null || major.isBlank() || major.isEmpty()) {
            //TODO: throw error if major is empty
        } else if (offerings.isEmpty()) {
            //TODO: throw error if course data empty
        } else if (!potentialMajors.contains(major)) { // TODO: check if this can be simplified or more dynamic
            //TODO: throw error since major is not input correctly
        }
        else if (false) {
            //TODO: throw error if transcript is empty
        } else {
            //TODO: write method.
            ArrayList<Offering> fallOfferings = new ArrayList<>();
            ArrayList<Offering> winterOfferings = new ArrayList<>();
            ArrayList<Offering> springOfferings = new ArrayList<>();
            //collect the offerings by term available for the major
            for(Offering offering : offerings) {
                try {
                    if (offering.getAvailability(major).getSeason().equalsIgnoreCase("fall")) {
                        fallOfferings.add(offering);
                    } else if (offering.getAvailability(major).getSeason().equalsIgnoreCase("winter")) {
                        winterOfferings.add(offering);
                    } else if (offering.getAvailability(major).getSeason().equalsIgnoreCase("spring")) {
                        springOfferings.add(offering);
                    } else {
                        //TODO: log that skipping?
                        //this means the course is not available for the specified major
                    }
                } catch (NullPointerException e) {
                    throw new InvalidInputException(String.format("The specified major %s was not found which means it was not input correctly", major));
                }
            }
            System.out.print("");
        }


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
                this.major = input.toUpperCase();
            }
        }
    }

    /**
     * Loads in the prerequisites, curriculum, and offerings csv files from the locations that are specified.
     * These locations do not have any checks or verification on them. Thus this method is meant to be called from
     * other locations after verification has occurred such as in loadDefaultCourseData() and loadCourseData().
     *
     * @param curriculumLocation the verified location to use for curriculum.csv
     * @param offeringsLocation the verified location to use for offerings.csv
     * @param prerequisitesLocation the verified location to use for prerequisites.csv
     * @throws IOException when there are issues reading in the CSV files
     * @return A formatted string containing the number of lines loaded into each file.
     * @author : Grant Fass
     * @since : Tue, 6 Apr 2021
     */
    private String loadCoursesFromSpecifiedLocations(String curriculumLocation, String offeringsLocation, String prerequisitesLocation) throws IOException {
        //Load the required courses first
        Collection<Course> courses;
        PrerequisitesLoader prerequisitesLoader = new PrerequisitesLoader(new FileReader(prerequisitesLocation));
        prerequisiteCourses = courses = prerequisitesLoader.load();

        //With the courses known, read the other files
        CurriculumLoader curriculumLoader = new CurriculumLoader(new FileReader(curriculumLocation), courses);
        OfferingsLoader offeringsLoader = new OfferingsLoader(new FileReader(offeringsLocation), courses);
        curricula = curriculumLoader.load();
        offerings = offeringsLoader.load();
        return String.format("Loaded %d prerequisites, %d curricula, and %d offerings", prerequisiteCourses.size(), curricula.size(), offerings.size());
    }

    /**
     * This method loads in all of the course data from the default locations
     *
     * This method is usually meant to be called on program startup so that the user can start working right away with
     * some of the files being loaded.
     *
     * @throws IOException when there are issues reading the CSV files in.
     * @return A formatted string containing the number of lines loaded into each file.
     * @author : Grant Fass
     * @since : Tue, 6 Apr 2021
     */
    public String loadDefaultCourseData() throws IOException {
        String curriculumLocation = getDefaultCurriculumLocation();
        String offeringsLocation = getDefaultOfferingsLocation();
        String prerequisitesLocation = getDefaultPrerequisitesLocation();
        return loadCoursesFromSpecifiedLocations(curriculumLocation, offeringsLocation, prerequisitesLocation);
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
     * This is passed a scanner so that the program does not create one scanner inside another
     * This method is thought to be called from the CLI.
     *
     * @param in An existing scanner to use to query the user for input
     * @throws InvalidInputException when there is an issue with the user input locations for the files
     * @throws IOException if there is an issue reading in the CSV files
     * @return A formatted string containing the number of lines loaded into each file.
     * @author : Grant Fass
     * @since : Thu, 1 Apr 2021
     */
    public String loadCourseData(Scanner in) throws InvalidInputException, IOException {
        //Ask the user if default file locations should be used or if a custom location should be used
        boolean useDefaultFiles = useDefaultFilesQuery(in);
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

        return loadCoursesFromSpecifiedLocations(curriculumLocation, offeringsLocation, prerequisitesLocation);
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
