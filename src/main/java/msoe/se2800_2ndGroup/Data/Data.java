package msoe.se2800_2ndGroup.Data;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.Offering;
import msoe.se2800_2ndGroup.ui.CourseCheckedBooleanProperty;

import java.util.*;
import java.util.logging.Logger;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: Data
 * Description:
 * * Stores various datum that has been loaded into the program
 * The Data class is responsible for:
 * * Storing the current major in the program
 * * Storing the current course data in the program
 * * Storing the current transcript data in the program
 * Modification Log:
 * * File Created by Grant on Thursday, 22 April 2021
 * * Moved variables, getters, and verification methods for datum objects from Model.java to Data
 * .java by Grant Fass on Thu, 22 Apr 2021
 * * Moved store major method from Model.java to Data.java by Grant Fass on Thu, 22 Apr 2021
 * * Add manual transcriptCourses manipulation methods by Hunter Turcin on Mon, 3 May 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 22 April 2021
 */
public class Data {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    //region variables

    // Variable to store the major of the user
    public static String major;
    // Variables to store the course data
    public static Collection<Curriculum> curricula;
    public static Collection<Course> prerequisiteCourses;
    public static ArrayList<Course> transcriptCourses = new ArrayList<>();
    public static ArrayList<Course> checkedCourses = new ArrayList<>();
    public static Collection<Offering> offerings;

    private static Collection<CourseCheckedBooleanProperty> uiProperties = new ArrayList<>();

    //endregion

    //region class var getters

    /**
     * This method returns the current major
     * <p>
     * This method returns the current value stored in the class instance variable for major
     *
     * @return the current major
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static String getMajor() {
        return major;
    }

    /**
     * This method returns the curriculum when called.
     * This method will return an empty ArrayList whenever curricula has not been loaded
     *
     * @return the curricula stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    public static Collection<Curriculum> getCurricula() {
        return Objects.requireNonNullElseGet(curricula, ArrayList::new);
    }

    /**
     * This method returns the course offerings when called.
     * This method will return an empty ArrayList whenever the offerings have not been loaded
     *
     * @return the course offerings stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    public static Collection<Offering> getOfferings() {
        return Objects.requireNonNullElseGet(offerings, ArrayList::new);
    }

    /**
     * This method returns the prerequisite courses when called.
     * This method will return an empty ArrayList whenever the prerequisite courses have not been
     * loaded
     *
     * @return the prerequisite courses stored in the program
     * @author : Grant Fass
     * @since : Tue, 30 Mar 2021
     */
    public static Collection<Course> getPrerequisiteCourses() {
        return Objects.requireNonNullElseGet(prerequisiteCourses, ArrayList::new);
    }

    /**
     * This method returns the list of transcript courses when called
     * This method will return an empty ArrayList whenever the transcript courses have not been
     * loaded.
     *
     * @return the transcript courses stored in the program
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static ArrayList<Course> getTranscriptCourses() {
        return transcriptCourses;
    }

    /**
     * Set the loaded transcript course list.
     * <p>
     * This also updates the checkboxes in the ui.
     *
     * @param transcriptCourses new transcript courses
     * @author : Hunter Turcin
     * @since : Tue, 4 May 2021
     */
    public static void setTranscriptCourses(ArrayList<Course> transcriptCourses) {
        Data.transcriptCourses = transcriptCourses;

        // Replace all checkbox data with the new transcript
        for (final var property : uiProperties) {
            property.set(isCourseChecked(property.getCourse()));
        }
    }

    /**
     * this method returns the list of all checked courses when called.
     *
     * @return the checked courses
     * @author : Grant Fass
     * @since : Thu, 6 May 2021
     */
    public static ArrayList<Course> getCheckedCourses() { return checkedCourses; }
    //endregion

    //region class variable verification methods

    /**
     * this method will check to see if the stored major is valid
     * <p>
     * This method will first check that the stored major is not null, blank, or empty; throwing
     * an error if it is.
     * This method will then check that the major is formatted correctly in its Abbreviated Code
     * form.
     * This method will throw an error if the stored major does not exist, or is improperly
     * formatted.
     *
     * @throws CustomExceptions.InvalidMajorException if the stored major does not exist, or is
     * improperly formatted.
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static void verifyMajor() throws CustomExceptions.InvalidInputException {
        LOGGER.finer("Verifying Major has been stored and matches an expected major code");
        Set<String> potentialMajors = new HashSet<>(
                Arrays.asList("EE", "BSE PT", "CE", "UX", "AE", "NU", "CS", "AS", "SE", "MIS", "ME",
                              "BME", "IE", "ME A"));
        if (getMajor() == null || getMajor().isBlank() || getMajor().isEmpty()) {
            LOGGER.warning("The specified major is missing or blank");
            throw new CustomExceptions.InvalidMajorException(
                    "The specified major is missing or blank");
        } else if (!potentialMajors.contains(getMajor())) {
            LOGGER.warning(String.format(
                    "The specified major %s was not found within the listing of acceptable majors" +
                    " which means it was not input correctly",
                    major));
            throw new CustomExceptions.InvalidMajorException(String.format(
                    "The specified major %s was not found within the listing of acceptable majors" +
                    " which means it was not input correctly",
                    major));
        }
    }

    /**
     * This method will verify that the course data offerings is loaded and is not empty
     * <p>
     * This method checks to see that the collection of offerings is not empty.
     *
     * @throws CustomExceptions.InvalidOfferingsException if the collection of offerings is empty
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static void verifyOfferings() throws CustomExceptions.InvalidInputException {
        LOGGER.finer("Verifying Offerings have been loaded");
        if (getOfferings().isEmpty()) {
            LOGGER.warning("There are no offerings loaded right now");
            throw new CustomExceptions.InvalidOfferingsException(
                    "There are no offerings loaded " + "right now");
        }
    }

    /**
     * This method will verify that some transcript data is available
     * <p>
     * This method checks to see if at least transcript or checked courses exist
     *
     * @throws CustomExceptions.InvalidTranscriptException when there is no data stored for the
     * transcript courses
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static void verifyTranscript() throws CustomExceptions.InvalidInputException {
        LOGGER.finer("Verifying Transcript has been loaded");
        final var courses = new HashSet<Course>();
        if (getTranscriptCourses() != null) {
            courses.addAll(getTranscriptCourses());
        }
        if (getCheckedCourses() != null) {
            courses.addAll(getCheckedCourses());
        }
        if (courses.isEmpty()) {
            LOGGER.warning("Transcript course data is not yet loaded");
            throw new CustomExceptions.InvalidTranscriptException(
                    "Transcript course data is not " + "yet loaded");
        }
    }

    //endregion

    /**
     * This method stores the input text as a major if it is valid
     * <p>
     * This method checks that the major only contains characters a-z case insensitive between 0
     * and 100 times also allowing whitespace.
     * If the input string matches the regex then it is stored, otherwise an error is thrown
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://stackoverflow.com/a/48523095"}</a>: Help Finding Regex
     *
     * @param major the major to store
     * @throws CustomExceptions.InvalidInputException if the specified input for major does not
     * match the regex.
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static void storeMajor(String major) throws CustomExceptions.InvalidInputException {
        if (major == null) {
            LOGGER.warning("The specified input for major was null");
            throw new CustomExceptions.InvalidInputException(
                    "The specified input for major was null");
        } else if (major.isEmpty() || major.isBlank()) {
            LOGGER.warning("The specified input for major was empty or blank");
            throw new CustomExceptions.InvalidInputException(
                    "The specified input for major was empty or blank");
        } else {
            //change hyphens and underscores to spaces, change double spaces to single spaces,
            // trim spaces off start and end.
            LOGGER.finer("Removing invalid characters from input major");
            String input = major.replaceAll("_", " ").replaceAll("-", " ").trim();
            while (input.contains("  ")) {
                input = input.replaceAll("[\\s]{2}", " ");
            }
            if (!input.matches("[a-zA-Z\\s]{1,99}")) {
                LOGGER.warning("The specified input for major {" + major +
                               "} did not match the expected pattern: /^[a-zA-Z\\s]{1,99}$");
                throw new CustomExceptions.InvalidInputException(
                        "The specified input for major {" + major +
                        "} did not match the expected pattern: /^[a-zA-Z\\s]{1,99}$");
            } else {
                LOGGER.fine("Storing specified major: " + major);
                Data.major = input.toUpperCase();
            }
        }
    }

    /**
     * Determine if a course has been checked already by the user.
     *
     * @param course course to get the status of
     * @return true if checked, false if not
     * @author : Hunter Turcin
     * @since : Mon, 3 May 2021
     */
    public static boolean isCourseChecked(Course course) {
        return getCheckedCourses().contains(course);
    }

    /**
     * Set the checked status of a course.
     *
     * @param course  course to set the checked status of
     * @param checked new checked status
     * @author : Hunter Turcin
     * @since : Mon, 3 May 2021
     */
    public static void setCourseChecked(Course course, boolean checked) {
        if (checked) {
            getCheckedCourses().add(course);
        } else {
            getCheckedCourses().remove(course);
        }
    }

    /**
     * Register a ui checkbox for updating when the transcript changes.
     *
     * @param property the property to track
     * @author : Hunter Turcin
     * @since : Tue, 4 May 2021
     */
    public static void registerProperty(CourseCheckedBooleanProperty property) {
        uiProperties.add(property);
    }
}
