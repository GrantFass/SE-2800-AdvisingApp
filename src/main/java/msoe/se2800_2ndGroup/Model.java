package msoe.se2800_2ndGroup;

import javafx.application.Platform;
import msoe.se2800_2ndGroup.Data.Compilers;
import msoe.se2800_2ndGroup.Data.Data;
import msoe.se2800_2ndGroup.Data.Manipulators;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.FileIO.CourseDataIO;
import msoe.se2800_2ndGroup.FileIO.RecommendationsIO;
import msoe.se2800_2ndGroup.FileIO.TranscriptIO;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.CurriculumItem;
import msoe.se2800_2ndGroup.models.Offering;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * * Add Method stubs and basic implementations for loading course data by Grant Fass on Fri, 26
 * Mar 2021
 * * Update course data loading implementation with changes from Hunter T. yesterday by Grant
 * Fass on Tue, 30 Mar 2021
 * * Transferred methods from Model.java to FileIO.java by Grant Fass on Tue, 30 Mar 2021
 * * Create new method to load course data that is passed a scanner by Grant Fass on Tue, 30 Mar
 * 2021
 * * Create method to load course data on startup by Grant Fass on Tue, 6 Apr 2021
 * * Implement methods to get and view course offerings by term by Grant Fass on Wed, 7 Apr 2021
 * * Add method to load in the unofficial transcript using the ImportTranscript class by Grant
 * Fass on Tue, 13 Apr 2021
 * * Implement methods for getting course recommendations for a term or terms.
 * * Fix getCourseRecommendation to work with electives by Grant Fass on Thu, 15 Apr 2021
 * * Add more custom exceptions to help with testing by Grant Fass on Thu, 15 Apr 2021
 * * Add logger by Grant Fass on Thu, 15 Apr 2021
 * * Add method to store unofficial transcripts by Grant Fass on Thu, 15 Apr 2021
 * * Perform code cleanup from group feedback by Hunter Turcin on Mon, 19 Apr 2021
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * * break into regions to further code refactoring by Grant Fass on Thu, 22 Apr 2021
 * * Moved custom exception inner classes from Model.java to CustomExceptions.java by Grant Fass
 * on Thu, 22 Apr 2021
 * * Moved variables, getters, and verification methods for datum objects from Model.java to Data
 * .java by Grant Fass on Thu, 22 Apr 2021
 * * Moved static data manipulation methods from Model.java to Manipulators.java by Grant Fass on
 * Thu, 22 Apr 2021
 * * Moved compilation methods from Model.java to Compilers.java by Grant Fass on Thu, 22 Apr 2021
 * * Moved course data loading methods from Model.java to CourseDataIO.java by Grant Fass on Thu,
 * 22 Apr 2021
 * * Moved store major method from Model.java to Data.java by Grant Fass on Thu, 22 Apr 2021
 * * Add storing course recommendations by Hunter Turcin on Sun, 9 May 2021
 *
 * @author : Grant
 * Copyright (C): TBD
 * @since : Saturday, 20 March 2021
 */
public class Model {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * This method runs the specified action or method on the FX thread to avoid errors.
     * Run methods by calling with the following format: ensureFXThread(() -> method());
     * <p>
     * This method runs tasks on the FX thread using Platform.isFXApplicationThread and Platform
     * .runLater
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://stackoverflow.com/a/49676738"}</a> Help setting up method
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
     * <p>
     * This method gets the ArrayList of offerings for the input terms.
     * This method then iterates through the offerings and extracts the useful information to a
     * string.
     * This string is then appended to a string builder which is returned.
     *
     * @param displayFall   a boolean representing weather or not to collect Offerings for the
     *                      fall term
     * @param displayWinter a boolean representing weather or not to collect Offerings for the
     *                      winter term
     * @param displaySpring a boolean representing weather or not to collect Offerings for the
     *                      spring term
     * @return the offerings for the input terms as a string.
     * @throws CustomExceptions.InvalidInputException the major was not found or there was an
     * error converting a
     *                                                specific course
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static String getCourseOfferingsAsString(boolean displayFall, boolean displayWinter,
                                                    boolean displaySpring)
    throws CustomExceptions.InvalidInputException {
        ArrayList<Offering> courseOfferings =
                Compilers.getCourseOfferings(displayFall, displayWinter, displaySpring);
        return Manipulators.getCourseOfferingsAsString(courseOfferings);
    }

    /**
     * Get the offerings for the specified terms.
     *
     * @param fall   whether or not to include fall offerings
     * @param winter whether or not to include winter offerings
     * @param spring whether or not to include spring offerings
     * @return all desired offerings
     * @throws CustomExceptions.InvalidInputException major not found
     * @author : Hunter Turcin
     * @since : Tue, 27 Apr 2021
     */
    public static List<Offering> getCourseOfferings(boolean fall, boolean winter, boolean spring)
    throws CustomExceptions.InvalidInputException {
        return Compilers.getCourseOfferings(fall, winter, spring);
    }


    //region Transcript FileIO

    /**
     * TODO: test me
     * Method used to load the unofficial transcript into the program by calling the readInFile
     * method from ImportTranscript
     *
     * @param in the scanner to use for IO operations
     * @throws IOException                            for issues creating the specified file or
     * reading it
     * @throws CustomExceptions.InvalidInputException for issues verifying the specified file
     * location
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static void loadUnofficialTranscript(Scanner in)
    throws CustomExceptions.InvalidInputException, IOException {
        LOGGER.finer(
                "Loading unofficial transcript using default scanner and a new importTranscript " +
                "object");
        Data.setTranscriptCourses(TranscriptIO.readInFile(in));
    }

    /**
     * TODO: test me
     * Method used to load the unofficial transcript into the program by calling the readInFile
     * method from ImportTranscript
     *
     * @param file the File the transcript is at. Assumes the file has already been verified
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static void loadUnofficialTranscript(File file) throws IOException {
        AdvisingLogger.getLogger().log(Level.FINER,
                                       "Loading unofficial transcript using passed in File and a " +
                                       "new importTranscript object");
        Data.setTranscriptCourses(TranscriptIO.readInFile(file));
    }

    /**
     * TODO: test me
     * Method to store an the current List of transcript courses to a new unofficial transcript
     * pdf in the .out folder
     *
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static void storeUnofficialTranscript() throws IOException {
        storeUnofficialTranscript("./out");
    }

    /**
     * TODO: test me
     * Method to store an the current List of transcript courses to a new unofficial transcript
     * pdf in the specified location
     *
     * @param outputLocation the directory to store the file in
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static void storeUnofficialTranscript(String outputLocation) throws IOException {
        String location = String.format("%s\\UnofficialTranscript-(%tF).pdf", outputLocation,
                                        System.currentTimeMillis());
        LOGGER.finer(
                "Saving current transcript courses to unofficial transcript using a new " +
                "unofficialTranscript object in the location: " +
                location);
        TranscriptIO.writeFile(Data.getTranscriptCourses(), location);
    }

    /**
     * TODO: test me
     * Method to store an the custom List of transcript courses to a new unofficial transcript
     * pdf in the specified location
     *
     * @param outputLocation the directory to store the file in
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass
     * @since : Mon, 3 May 2021
     */
    public static void storeCustomUnofficialTranscript(String outputLocation,
                                                       ArrayList<Course> courses)
    throws IOException {
        String location = String.format("%s\\UnofficialTranscript-(%tF).pdf", outputLocation,
                                        System.currentTimeMillis());
        LOGGER.finer(
                "Saving current transcript courses to unofficial transcript using a new " +
                "unofficialTranscript object in the location: " +
                location);
        TranscriptIO.writeFile(courses, location);
    }

    //endregion

    public static void storeCourseRecommendations(String directory, boolean fall, boolean winter,
                                                  boolean spring)
    throws IOException, CustomExceptions.InvalidInputException {
        final var location = String.format("%s\\CourseRecommendations-(%tF).pdf", directory,
                                           System.currentTimeMillis());
        LOGGER.finer("Saving current course recommendations at: " + location);
        final var recommendations = getCourseRecommendation(fall, winter, spring);
        RecommendationsIO.write(recommendations, location);
    }

    /**
     * TODO: test me
     * This method process course recommendations and returns them as a String
     * <p>
     * This method first compiles all of the course recommendations before turning them into a
     * string and returning them.
     *
     * @return the list of course recommendations for the specified terms as a string
     * @throws CustomExceptions.InvalidInputException for various reasons like not having a major
     * stored, failing to validate major,
     *                                                failing to validate offerings, failing to
     *                                                validate transcript, or not having a
     *                                                curriculum for the major.
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static String getCourseRecommendationAsString(boolean getFall, boolean getWinter,
                                                         boolean getSpring)
    throws CustomExceptions.InvalidInputException {
        if (!getFall && !getWinter && !getSpring) {
            return "No Terms Selected";
        }
        List<CurriculumItem> courseRecommendation =
                Compilers.getCourseRecommendation(getFall, getWinter, getSpring);
        return Manipulators.getCurriculumItemsAsString(courseRecommendation);
    }

    /**
     * Get course recommendations for the specified terms.
     *
     * @param fall   whether or not to include fall
     * @param winter whether or not to include winter
     * @param spring whether or not to include spring
     * @return the requested recommendations
     * @throws CustomExceptions.InvalidInputException bad major, bad data loaded, or no terms
     * selected
     * @author : Hunter Turcin
     * @since : Tue, 27 Apr 2021
     */
    public static List<CurriculumItem> getCourseRecommendation(boolean fall, boolean winter,
                                                               boolean spring)
    throws CustomExceptions.InvalidInputException {
        if (!fall && !winter && !spring) {
            throw new CustomExceptions.InvalidInputException("no terms selected");
        }

        return Compilers.getCourseRecommendation(fall, winter, spring);
    }

    /**
     * This method retrieves the prerequisites for a course
     * <p>
     * This method calls upon the Compilers.getPrerequisitesForCourse method.
     *
     * @param course the course String that is sought for
     * @return The prerequisite codes for a given course as a String
     * @author : Claudia Poptile
     * @since : Mon, 12 Apr 2021
     */
    public static String viewPrerequisiteCourses(String course) {
        return Compilers.getPrerequisitesForCourse(course);
    }

    /**
     * This method exits the program
     * <p>
     * This method runs the System.exit command with a status of 0
     * This method makes sure to execute on the FX thread using the ensureFXThread method
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://stackoverflow.com/a/21962055"}</a> Help exiting program
     * through if statement
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static void exitProgram() {
        ensureFXThread(() -> System.exit(0));
    }

    //region course data loading methods

    /**
     * This method loads all of the course data by querying the user for locations through the
     * passed scanner
     *
     * @param in An existing scanner to use to query the user for input
     * @return A formatted string containing the number of lines loaded into each file.
     * @throws CustomExceptions.InvalidInputException when there is an issue with the user input
     * locations for the files
     * @throws IOException                            if there is an issue reading in the CSV files
     * @author : Grant Fass
     * @since : Thu, 1 Apr 2021
     */
    public static String loadCourseData(Scanner in)
    throws CustomExceptions.InvalidInputException, IOException {
        return CourseDataIO.loadCourseDataFromScanner(in);
    }

    /**
     * This method loads all of the course data into the program from the default locations
     *
     * @return A formatted string containing the number of lines loaded into each file.
     * @throws IOException if there is an issue reading in the CSV files
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static String loadDefaultCourseData() throws IOException {
        return CourseDataIO.loadCourseDataFromDefaults();
    }

    /**
     * Loads in course data from the specified locations by using CourseDataIO.java
     *
     * @param curriculumLocation    the verified location to use for curriculum.csv
     * @param offeringsLocation     the verified location to use for offerings.csv
     * @param prerequisitesLocation the verified location to use for prerequisites.csv
     * @return A formatted string containing the number of lines loaded into each file.
     * @throws IOException when there are issues reading in the CSV files
     * @author : Grant Fass
     * @since : Tue, 6 Apr 2021
     */
    public static String loadCoursesFromSpecifiedLocations(String curriculumLocation,
                                                           String offeringsLocation,
                                                           String prerequisitesLocation)
    throws IOException {
        return CourseDataIO.loadCoursesFromSpecifiedLocations(curriculumLocation, offeringsLocation,
                                                              prerequisitesLocation);
    }

    //endregion

    /**
     * Method to store a major into the program using the Data class method for storing majors
     *
     * @param major the major to store
     * @throws CustomExceptions.InvalidInputException if the specified input for the major does not match
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static void storeMajor(String major) throws CustomExceptions.InvalidInputException {
        Data.storeMajor(major);
    }

    //region graphing methods

    /**
     * TODO: test me
     * Get a textual representation of a graph of a course's prerequisites.
     *
     * @param code the code of the course to analyze
     * @return the course's prerequisite graph
     */
    public static String getCourseGraph(String code) throws CustomExceptions.InvalidInputException {
        return Compilers.getCoursePrerequisiteGraph(code);
    }

    //endregion
}
