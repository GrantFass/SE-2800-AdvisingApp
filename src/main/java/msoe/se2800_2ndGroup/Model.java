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
 * * Add method to load in the unofficial transcript using the ImportTranscript class by Grant Fass on Tue, 13 Apr 2021
 * * Implement methods for getting course recommendations for a term or terms.
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
    private ArrayList<Course> transcriptCourses;

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
        return getCourseAsString(offering.getCourse());
    }

    /**
     * this method extracts the important information from a given course and returns a string containing the values
     *
     * this method uses string formatting to display a passed course in a readable format.
     * The format is CODE CREDITS | DESCRIPTION : PREREQUISITES.
     * @param course the course to extract information from
     * @return a courses information in string format
     * @throws InvalidInputException if the course was null
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    private String getCourseAsString(Course course) throws InvalidInputException {
        if (course == null) {
            throw new InvalidInputException("The input course was null");
        }
        return String.format("%7s %2s | %40s : %s\n", course.code(), course.credits(), course.description(), course.prerequisite());
    }

    /**
     * Method used to load the unofficial transcript into the program by calling the readInFile method from ImportTranscript
     * @param in the scanner to use for IO operations
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public void loadUnofficialTranscript(Scanner in) {
        ImportTranscript importTranscript = new ImportTranscript();
        transcriptCourses = importTranscript.readInFile(in);
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
     * This method will verify that the user transcript has been loaded
     *
     * This method checks to see if the transcriptCourses collection is null or empty
     *
     * @throws InvalidInputException when there is no data stored for the transcript courses
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    private void verifyTranscript() throws InvalidInputException {
        if (transcriptCourses == null || transcriptCourses.isEmpty()) {
            throw new InvalidInputException("Transcript course data is not yet loaded");
        }
    }

    /**
     * TODO: test me
     * This method process course recommendations and returns them as a String
     *
     * This method first verifies that the Major, Offerings, and Transcript have been loaded and are not empty.
     * This method then computes the offerings available.
     * This method then finds the list of courses not yet satisfied in the curriculum and compares it against the offerings.
     * This method then takes the unsatisfied offerings that are available to be taken in the specified term and outputs them as a string.
     *
     * @throws InvalidInputException for various reasons like not having a major stored, failing to validate major,
     *              failing to validate offerings, failing to validate transcript, or not having a curriculum for the major.
     * @return the list of course recommendations for the specified terms as a string
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public String getCourseRecommendation(boolean getFall, boolean getWinter, boolean getSpring) throws InvalidInputException {
        verifyMajor();
        verifyOfferings();
        verifyTranscript();
        //start computing recommendations
        ArrayList<Offering> offeringsForTerm = getCourseOfferings(getFall, getWinter, getSpring);
        List<CurriculumItem> uncompletedCurriculumCourses = getCurriculaExcludingCompletedCourses(transcriptCourses);
        List<Course> unsatisfiedOfferingsForTerm = getUnsatisfiedMatchingTerm(offeringsForTerm, uncompletedCurriculumCourses);
        //return output
        StringBuilder builder = new StringBuilder();
        for (Course course: unsatisfiedOfferingsForTerm) {
            builder.append(getCourseAsString(course));
        }
        return builder.toString();
    }

    /**
     * This method is used to get the unsatisfied courses that occur in the given term.
     *
     * This method compares the list of all offerings in a term against the list of unsatisfied courses and looks for matches.
     * If a match is found it is added to the list of possible output then returned.
     *
     * @param offeringsInTerm the list of offerings for a given term
     * @param unsatisfiedCourses the list of courses that have not yet been satisfied
     * @return the list of the courses that have not yet been satisfied and occur in the specified term
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    private List<Course> getUnsatisfiedMatchingTerm(List<Offering> offeringsInTerm, List<CurriculumItem> unsatisfiedCourses) {
        ArrayList<Course> out = new ArrayList<>();
        for (CurriculumItem curriculumItem: unsatisfiedCourses) {
            for (Offering offering: offeringsInTerm) {
                if (curriculumItem.satisfiedBy(offering.getCourse())) {
                    out.add(offering.getCourse());
                }
            }
        }
        return out;
    }

    /**
     * Method used to compute what courses from the curriculum have not yet been completed
     *
     * This method goes through the list of curricula searching for the correct major.
     * Once the correct major is found it uses the getUnsatisfiedItems method to retrieve a list of the courses
     * that have not yet been completed.
     *
     * @param completedCourses the list of already completed courses, usually from the loaded unofficial transcript
     * @return the list of CurriculumItems that have not yet been satisfied
     * @throws InvalidInputException if the major was not found
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    private List<CurriculumItem> getCurriculaExcludingCompletedCourses(ArrayList<Course> completedCourses) throws InvalidInputException {
        for (Curriculum curriculum: curricula) {
            if (curriculum.major().equalsIgnoreCase(major)) {
                return curriculum.getUnsatisfiedItems(completedCourses);
            }
        }
        throw new InvalidInputException("Curriculum for selected major not found");
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
