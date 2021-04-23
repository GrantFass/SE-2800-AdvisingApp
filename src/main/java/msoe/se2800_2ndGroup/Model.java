package msoe.se2800_2ndGroup;

import javafx.application.Platform;
import msoe.se2800_2ndGroup.Data.Compilers;
import msoe.se2800_2ndGroup.Data.Data;
import msoe.se2800_2ndGroup.Data.Manipulators;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.FileIO.DefaultLocations;
import msoe.se2800_2ndGroup.FileIO.TranscriptIO;
import msoe.se2800_2ndGroup.Graphing.GraphMaker;
import msoe.se2800_2ndGroup.loaders.CurriculumLoader;
import msoe.se2800_2ndGroup.loaders.OfferingsLoader;
import msoe.se2800_2ndGroup.loaders.PrerequisitesLoader;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static msoe.se2800_2ndGroup.FileIO.FileIO.getUserInputFileLocation;
import static msoe.se2800_2ndGroup.FileIO.FileIO.useDefaultFilesQuery;

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
 * * Fix getCourseRecommendation to work with electives by Grant Fass on Thu, 15 Apr 2021
 * * Add more custom exceptions to help with testing by Grant Fass on Thu, 15 Apr 2021
 * * Add logger by Grant Fass on Thu, 15 Apr 2021
 * * Add method to store unofficial transcripts by Grant Fass on Thu, 15 Apr 2021
 * * Perform code cleanup from group feedback by Hunter Turcin on Mon, 19 Apr 2021
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * * break into regions to further code refactoring by Grant Fass on Thu, 22 Apr 2021
 * * Moved custom exception inner classes from Model.java to CustomExceptions.java by Grant Fass on Thu, 22 Apr 2021
 * * Moved variables, getters, and verification methods for datum objects from Model.java to Data.java by Grant Fass on Thu, 22 Apr 2021
 * @since : Saturday, 20 March 2021
 * @author : Grant
 * Copyright (C): TBD
 */
public class Model {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

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
    public static void ensureFXThread(Runnable action) {
        if (Platform.isFxApplicationThread()) {
            action.run();
        } else {
            Platform.runLater(action);
        }
    }

    /**
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
     * @throws CustomExceptions.InvalidInputException the major was not found or there was an error converting a
     * specific course
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static String getCourseOfferingsAsString(boolean displayFall, boolean displayWinter, boolean displaySpring) throws CustomExceptions.InvalidInputException {
        ArrayList<Offering> courseOfferings = Compilers.getCourseOfferings(displayFall, displayWinter, displaySpring);
        return Manipulators.getCourseOfferingsAsString(courseOfferings);
    }
    //TODO: these are not necessarily needed... most only contain loggers ontop of the transcript info
    //region Transcript FileIO

    /**
     * TODO: test me
     * Method used to load the unofficial transcript into the program by calling the readInFile method from ImportTranscript
     * @param in the scanner to use for IO operations
     * @throws IOException for issues creating the specified file or reading it
     * @throws CustomExceptions.InvalidInputException for issues verifying the specified file location
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static void loadUnofficialTranscript(Scanner in) throws CustomExceptions.InvalidInputException, IOException {
        LOGGER.finer("Loading unofficial transcript using default scanner and a new importTranscript object");
        Data.transcriptCourses = TranscriptIO.readInFile(in);
    }

    /**
     * TODO: test me
     * Method used to load the unofficial transcript into the program by calling the readInFile method from ImportTranscript
     * @param file the File the transcript is at. Assumes the file has already been verified
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static void loadUnofficialTranscript(File file) throws IOException {
        AdvisingLogger.getLogger().log(Level.FINER, "Loading unofficial transcript using passed in File and a new importTranscript object");
        Data.transcriptCourses = TranscriptIO.readInFile(file);
    }

    /**
     * TODO: test me
     * Method to store an the current List of transcript courses to a new unofficial transcript pdf in the .out folder
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static void storeUnofficialTranscript() throws IOException {
        storeUnofficialTranscript("./out");
    }
    /**
     * TODO: test me
     * Method to store an the current List of transcript courses to a new unofficial transcript pdf in the specified location
     * @param outputLocation the directory to store the file in
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static void storeUnofficialTranscript(String outputLocation) throws IOException {
        String location = outputLocation + "/UnofficialTranscript.pdf";
        LOGGER.finer("Saving current transcript courses to unofficial transcript using a new unofficialTranscript object in the location: " + location);
        TranscriptIO.writeFile(Data.getTranscriptCourses(), location);
    }

    //endregion

    /**
     * TODO: test me
     * This method process course recommendations and returns them as a String
     *
     * This method first compiles all of the course recommendations before turning them into a string and returning them.
     *
     * @throws CustomExceptions.InvalidInputException for various reasons like not having a major stored, failing to validate major,
     *              failing to validate offerings, failing to validate transcript, or not having a curriculum for the major.
     * @return the list of course recommendations for the specified terms as a string
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static String getCourseRecommendation(boolean getFall, boolean getWinter, boolean getSpring) throws CustomExceptions.InvalidInputException {
        if (!getFall && !getWinter && !getSpring) {
            return "No Terms Selected";
        }
        List<CurriculumItem> courseRecommendation = Compilers.getCourseRecommendation(getFall, getWinter, getSpring);
        return Manipulators.getCurriculumItemsAsString(courseRecommendation);
    }

    /**
     * This method retrieves the prerequisites for a course
     *
     * This method calls upon the Compilers.getPrerequisitesForCourse method.
     *
     * @param course the course String that is sought for
     * @return The prerequisite codes for a given course as a String
     * @author : Claudia Poptile
     * @since : Mon, 12 Apr 2021
     */
    public static String viewPrerequisiteCourses(String course){
        return Compilers.getPrerequisitesForCourse(course);
    }

    /**
     * This method takes the passed in course string to remove any and all
     * non-alphanumeric characters in the string.
     *
     * This method relies on the use of regex to remove unwanted characters
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/"}</a>: Reference for regex expression
     *
     * @author : Claudia Poptile
     * @since : Tue, 13 Apr 2021
     * @param course course string to be standardized
     * @return course code with only capitalized letters and numbers
     */
    public static String standardizeCourse(String course){
        return course.toUpperCase().replaceAll("[^a-zA-Z0-9]", "").trim();
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
    public static void exitProgram() {
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
     * @throws CustomExceptions.InvalidInputException if the specified input for major does not match the regex.
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void storeMajor(String major) throws CustomExceptions.InvalidInputException {
        if (major == null) {
            LOGGER.warning("The specified input for major was null");
            throw new CustomExceptions.InvalidInputException("The specified input for major was null");
        } else if (major.isEmpty() || major.isBlank()) {
            LOGGER.warning("The specified input for major was empty or blank");
            throw new CustomExceptions.InvalidInputException("The specified input for major was empty or blank");
        } else {
            //change hyphens and underscores to spaces, change double spaces to single spaces, trim spaces off start and end.
            LOGGER.finer("Removing invalid characters from input major");
            String input = major.replaceAll("_", " ").replaceAll("-", " ").trim();
            while (input.contains("  ")) {
                input = input.replaceAll("[\\s]{2}", " ");
            }
            if (!input.matches("[a-zA-Z\\s]{1,99}")) {
                LOGGER.warning("The specified input for major {" + major + "} did not match the expected pattern: /^[a-zA-Z\\s]{1,99}$");
                throw new CustomExceptions.InvalidInputException("The specified input for major {" + major + "} did not match the expected pattern: /^[a-zA-Z\\s]{1,99}$");
            } else {
                LOGGER.fine("Storing specified major: " + major);
                Data.major = input.toUpperCase();
            }
        }
    }

    //region course data loading methods
    /**
     * Loads in the prerequisites, curriculum, and offerings csv files from the locations that are specified.
     * These locations do not have any checks or verification on them. Thus this method is meant to be called from
     * other locations after verification has occurred such as in loadDefaultCourseData() and loadCourseData().
     *  Assumes that all files have already been validated
     *
     * @param curriculumLocation the verified location to use for curriculum.csv
     * @param offeringsLocation the verified location to use for offerings.csv
     * @param prerequisitesLocation the verified location to use for prerequisites.csv
     * @throws IOException when there are issues reading in the CSV files
     * @return A formatted string containing the number of lines loaded into each file.
     * @author : Grant Fass
     * @since : Tue, 6 Apr 2021
     */
    public String loadCoursesFromSpecifiedLocations(String curriculumLocation, String offeringsLocation, String prerequisitesLocation) throws IOException {

        LOGGER.finer(String.format("Attempting to load courses from specified locations:\n\t%s\n\t%s\n\t%s", curriculumLocation, offeringsLocation, prerequisitesLocation));
        //Load the required courses first
        Collection<Course> courses;
        PrerequisitesLoader prerequisitesLoader = new PrerequisitesLoader(new FileReader(prerequisitesLocation));
        Data.prerequisiteCourses = courses = prerequisitesLoader.load();
        Data.prerequisiteCourses.addAll(getVirtualCourses());

        //With the courses known, read the other files
        CurriculumLoader curriculumLoader = new CurriculumLoader(new FileReader(curriculumLocation), courses);
        OfferingsLoader offeringsLoader = new OfferingsLoader(new FileReader(offeringsLocation), courses);
        Data.curricula = curriculumLoader.load();
        Data.offerings = offeringsLoader.load();
        return String.format("Loaded %d prerequisites, %d curricula, and %d offerings", Data.prerequisiteCourses.size(), Data.curricula.size(), Data.offerings.size());
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
        LOGGER.finer("Loading course data from default locations");
        String curriculumLocation = DefaultLocations.getDefaultCurriculumLocation();
        String offeringsLocation = DefaultLocations.getDefaultOfferingsLocation();
        String prerequisitesLocation = DefaultLocations.getDefaultPrerequisitesLocation();
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
     * @throws CustomExceptions.InvalidInputException when there is an issue with the user input locations for the files
     * @throws IOException if there is an issue reading in the CSV files
     * @return A formatted string containing the number of lines loaded into each file.
     * @author : Grant Fass
     * @since : Thu, 1 Apr 2021
     */
    public String loadCourseData(Scanner in) throws CustomExceptions.InvalidInputException, IOException {
        LOGGER.finer("Querying user using passed scanner to retrieve locations and load course data");
        //Ask the user if default file locations should be used or if a custom location should be used
        boolean useDefaultFiles = useDefaultFilesQuery(in);
        //set the locations to the default
        String curriculumLocation = DefaultLocations.getDefaultCurriculumLocation();
        String offeringsLocation = DefaultLocations.getDefaultOfferingsLocation();
        String prerequisitesLocation = DefaultLocations.getDefaultPrerequisitesLocation();
        //if the user wants to use custom locations then query them to retrieve the locations and validate the files
        if (!useDefaultFiles) {
            //get new locations and validate
            curriculumLocation = getUserInputFileLocation("curriculum.csv", ".csv", in, System.out);
            offeringsLocation = getUserInputFileLocation("offerings.csv", ".csv", in, System.out);
            prerequisitesLocation = getUserInputFileLocation("prerequisites.csv", ".csv", in, System.out);
        }

        return loadCoursesFromSpecifiedLocations(curriculumLocation, offeringsLocation, prerequisitesLocation);
    }
    //endregion

    //region graphing methods
    /**
     * TODO: test me
     * Get a textual representation of a graph of a course's prerequisites.
     *
     * @param code the code of the course to analyze
     * @return the course's prerequisite graph
     */
    public String getCourseGraph(String code) {
        return Compilers.getCoursePrerequisiteGraph(code);
    }

    /**
     * Create the set of virtual courses to represent prerequisites that don't
     * map to actual courses.
     *
     * Sources:
     *   * <a href="https://catalog.msoe.edu/">MSOE Course Catalog</a>: Code explanation
     *   * Grant Fass: suggestion to use made-up Course objects
     *
     * @return the set of virtual courses
     */
    private static Collection<Course> getVirtualCourses() {
        return Set.of(
                new Course("SO", 0, new NullPrerequisite(), "Sophomore Standing"),
                new Course("JR", 0, new NullPrerequisite(), "Junior Standing"),
                new Course("SR", 0, new NullPrerequisite(), "Senior Standing"),
                new Course("IC", 0, new NullPrerequisite(), "Instructor Consent"),
                new Course("CE", 0, new NullPrerequisite(), "Computer Engineering Major"),
                new Course("UX", 0, new NullPrerequisite(), "User Experience Program Enrollment")
        );
    }
    //endregion
}
