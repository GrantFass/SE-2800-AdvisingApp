package msoe.se2800_2ndGroup;

import javafx.application.Platform;
import msoe.se2800_2ndGroup.loaders.CurriculumLoader;
import msoe.se2800_2ndGroup.loaders.OfferingsLoader;
import msoe.se2800_2ndGroup.loaders.PrerequisitesLoader;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.CurriculumItem;
import msoe.se2800_2ndGroup.models.Offering;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.*;

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
 * * Implement methods to get and view course offerings by term by Grant Fass on Wed, 7 Apr 2021
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
     * TODO: test me
     * This method returns the curriculum when called.
     * This method will return an empty ArrayList whenever curricula has not been loaded
     * @return the curricula stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     * TODO: FIX METHOD SIGNATURE
     */
    public Collection<Curriculum> getCurricula() {
        return Objects.requireNonNullElseGet(curricula, ArrayList::new);
    }

    /**
     * TODO: test me
     * This method returns the course offerings when called.
     * This method will return an empty ArrayList whenever the offerings have not been loaded
     * @return the course offerings stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     * TODO: FIX METHOD SIGNATURE
     */
    public Collection<Offering> getOfferings() {
        return Objects.requireNonNullElseGet(offerings, ArrayList::new);
    }

    /**
     * TODO: test me
     * This method returns the prerequisite courses when called.
     * This method will return an empty ArrayList whenever the prerequisite courses have not been loaded
     * @return the prerequisite courses stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     * TODO: FIX METHOD SIGNATURE
     */
    public Collection<Course> getPrerequisiteCourses() {
        return Objects.requireNonNullElseGet(prerequisiteCourses, ArrayList::new);
    }

    /**
     * TODO: test me
     * This method collects all of the offerings available in for the terms that are given
     *
     * This method goes through all of the offerings that were stored during course data loading.
     * For each of the offerings it will determine if it is available for the given term and major.
     * This method uses the stored major.
     *
     * @param displayFall a boolean representing weather or not to collect Offerings for the fall term
     * @param displayWinter a boolean representing weather or not to collect Offerings for the winter term
     * @param displaySpring a boolean representing weather or not to collect Offerings for the spring term
     * @return an ArrayList of Offerings from the selected terms
     * @throws InvalidInputException the major was not found.
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public ArrayList<Offering> getCourseOfferings(boolean displayFall, boolean displayWinter, boolean displaySpring) throws InvalidInputException {
        verifyMajor();
        ArrayList<Offering> offerings = new ArrayList<>();
        //collect the offerings by term available for the major
        try {
            for (Offering offering : this.offerings) {
                if (offering.getAvailability(major).getSeason().equalsIgnoreCase("fall") && displayFall) {
                    offerings.add(offering);
                } else if (offering.getAvailability(major).getSeason().equalsIgnoreCase("winter") && displayWinter) {
                    offerings.add(offering);
                } else if (offering.getAvailability(major).getSeason().equalsIgnoreCase("spring") && displaySpring) {
                    offerings.add(offering);
                }
            }
        } catch (NullPointerException e) {
            throw new InvalidInputException(String.format("The specified major %s was not found which means it was not input correctly", major));
        }
        return offerings;
    }

    /**
     * TODO: test me
     * This method returns the list of offerings for the input terms as a readable string.
     *
     * This method gets the ArrayList of offerings for the input terms.
     * This method then iterates through the offerings and extracts the useful information to a string.
     * This string is then appended to a string builder which is returned.
     *
     * @param displayFall a boolean representing weather or not to collect Offerings for the fall term
     * @param displayWinter a boolean representing weather or not to collect Offerings for the winter term
     * @param displaySpring a boolean representing weather or not to collect Offerings for the spring term
     * @return the offerings for the input terms as a string.
     * @throws InvalidInputException the major was not found.
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public String getCourseOfferingsAsString(boolean displayFall, boolean displayWinter, boolean displaySpring) throws InvalidInputException {
        if (!displayFall && !displayWinter && !displaySpring) {
            return "No Terms Selected\n";
        }
        ArrayList<Offering> courseOfferings = getCourseOfferings(displayFall, displayWinter, displaySpring);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%7s %2s | %40s : %s\n", "CODE", "CR", "DESCRIPTION", "PREREQUISITES"));
        for (Offering o: courseOfferings) {
            //format is CODE CREDITS | DESCRIPTION : PRERECS
            builder.append(getOfferingAsString(o));
        }
        return builder.toString();
    }

    /**
     * TODO: test me
     * This method extracts the important information from a given offering and returns a string containing the values
     *
     * This method uses String formatting to display a passed offering in a readable format.
     * The format is CODE CREDITS | DESCRIPTION : PREREQUISITES.
     * @param offering the offering to extract information from
     * @return an offerings information in a string format
     * @throws InvalidInputException if the offering was null
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    private String getOfferingAsString(Offering offering) throws InvalidInputException {
        if (offering == null) {
            throw new InvalidInputException("The input offering was null");
        }
        return String.format("%7s %2s | %40s : %s\n", offering.getCourse().code(), offering.getCourse().credits(), offering.getCourse().description(), offering.getCourse().prerequisite());
    }

    /**
     * TODO: test me
     * this method will check to see if the stored major is valid
     *
     * This method will first check that the stored major is not null, blank, or empty; throwing an error if it is.
     * This method will then check that the major is formatted correctly in its Abbreviated Code form.
     * This method will throw an error if the stored major does not exist, or is improperly formatted.
     * @throws InvalidInputException if the stored major does not exist, or is improperly formatted.
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    private void verifyMajor() throws InvalidInputException {
        Set<String> potentialMajors = new HashSet<>(Arrays.asList("EE", "BSE PT", "CE", "UX", "AE", "NU", "CS", "AS", "SE", "MIS", "ME", "BME", "IE", "ME A"));
        if (major == null || major.isBlank() || major.isEmpty()) {
            throw new InvalidInputException("The specified major is missing or blank");
        } else if (!potentialMajors.contains(major)) { // TODO: check if this can be simplified or more dynamic
            throw new InvalidInputException(String.format("The specified major %s was not found within the listing of acceptable majors which means it was not input correctly", major));
        }
    }

    /**
     * TODO: test me
     * This method will verify that the course data offerings is loaded and is not empty
     *
     * This method checks to see that the collection of offerings is not empty.
     *
     * @throws InvalidInputException if the collection of offerings is empty
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    private void verifyOfferings() throws InvalidInputException {
        if (offerings.isEmpty()) {
            throw new InvalidInputException("There are no offerings loaded right now");
        }
    }

    /**
     * TODO: test me
     * TODO: implement me
     * This method will verify that the user transcript has been loaded
     *
     * //TODO:
     *
     * @throws InvalidInputException //TODO
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    private void verifyTranscript() throws InvalidInputException {
        //TODO:
    }

    /**
     * TODO: test me
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
        //TODO: FIXME && TEST_ME
        verifyMajor();
        verifyOfferings();
        verifyTranscript();

            /*
            TODO: REPLACE ME with code for transcripts
             assume fall for testing
             manually insert completed courses for testing
             FassG completed courses not including WIP.
             */
        String targetTerm = "spring";
        Set<String> completedCourses = new HashSet<>(Arrays.asList("CS1011", "HU445", "HU446", "MA136", "MA262",
                "GS1001", "GS1003", "CH200", "GS1002", "BA2220", "CS1021", "MA137", "PH2011", "BA3444", "CS2852",
                "MA2314", "PH2021", "CS2911", "HU4480", "MA2310", "MA2323", "SE2030", "SS415AM", "CS2300", "CS2711",
                "SE2811"));

        //start computing recommendations
        ArrayList<Offering> offeringsForTerm = getCourseOfferings(false, false, true);
        ArrayList<Offering> uncompleted = getUncompletedOfferings(offeringsForTerm, completedCourses);
        ArrayList<Course> temp = getCurriculaExcludingCompletedCourses(completedCourses);

        System.out.print("");


        return "";
    }

    /**
     * TODO: test me
     * this method takes the offerings for a given term and the completed courses up till now and returns the list of offerings not yet taken
     *
     * this method goes through all of the available offerings for a given term.
     * for each of the offerings in the term it checks if the course code is in the set of completed courses
     * if the offering is not in the set of completed courses then it is added to the output ArrayList.
     *
     * @param offeringsForGivenTerm the ArrayList of offerings for a given term
     * @param completedCourses the Set containing all of the courses that have been completed so far. (contains the course codes as strings)
     * @return the list of offerings not yet taken for a given term
     * @author : Grant Fass
     * @since : Tue, 6 Apr 2021
     */
    private ArrayList<Offering> getUncompletedOfferings(ArrayList<Offering> offeringsForGivenTerm, Set<String> completedCourses) {
        ArrayList<Offering> offeringsNotYetTaken = new ArrayList<>();
        for (Offering offering: offeringsForGivenTerm) {
            if (!completedCourses.contains(offering.getCourse().code())) {
                offeringsNotYetTaken.add(offering);
            }
        }
        return offeringsNotYetTaken;
    }

    //TODO: Add javadocs
    /*
     * TODO: test me
    determine if this method is even necessary once the getUnsatisfiedItems method can be used.
     */
    private ArrayList<Course> getCurriculaExcludingCompletedCourses(Set<String> completedCourses) throws InvalidInputException {
        boolean foundCurriculum = false;
        for (Curriculum curriculum: curricula) {
            if (curriculum.major().equalsIgnoreCase(major)) {
                foundCurriculum = true;
                //curriculum.getUnsatisfiedItems() //TODO: FIX ME / Implement me once transcript is working.
            }
        }
        if (!foundCurriculum) {
            throw new InvalidInputException("Curriculum for selected major not found");
        }
        return new ArrayList<>();
    }

    public String viewPrerequisiteCourses(String course){
        //TODO: validate course- maybe create a new method

        // replace whitespaces, hyphens, underscores
        course = course.toUpperCase().replaceAll("\\s+","").replaceAll("_", "").replaceAll("-", "").trim();

        Course selected = null;
        //TODO: search through prereq. list for course, return prereqs.
        //Below code is just testing- does not work
        for (Course prerequisiteCourse: prerequisiteCourses){
            if (prerequisiteCourse.code().equals(course)){
                selected = prerequisiteCourse;
            } else {
                selected = null;
            }
//            System.out.println(prerequisiteCourse.getCode());
//            System.out.println(prerequisiteCourse.getPrerequisite());
        }
        if (selected != null) {
            return selected.prerequisite().toString();
        } else {
            return "No prereqs. found";
        }
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
     * TODO: test me
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
            curriculumLocation = getUserInputFileLocation("curriculum.csv", ".csv", in);
            offeringsLocation = getUserInputFileLocation("offerings.csv", ".csv", in);
            prerequisitesLocation = getUserInputFileLocation("prerequisites.csv", ".csv", in);
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
