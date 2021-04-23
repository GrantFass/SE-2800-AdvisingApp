package msoe.se2800_2ndGroup.FileIO;

import msoe.se2800_2ndGroup.Data.Data;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.loaders.CurriculumLoader;
import msoe.se2800_2ndGroup.loaders.OfferingsLoader;
import msoe.se2800_2ndGroup.loaders.PrerequisitesLoader;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.NullPrerequisite;

import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Scanner;
import java.util.Set;
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
 * Class Name: CourseDataIO
 * Description:
 * * Handles the importing of course data
 * The CourseDataIO class is responsible for:
 * * Handling the methods used to import course data
 * Modification Log:
 * * File Created by Grant on Thursday, 22 April 2021
 * * Moved course data loading methods from Model.java to CourseDataIO.java by Grant Fass on Thu, 22 Apr 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 22 April 2021
 */
public class CourseDataIO {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

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
    public static String loadCoursesFromSpecifiedLocations(String curriculumLocation, String offeringsLocation, String prerequisitesLocation) throws IOException {

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
    public static String loadCourseDataFromScanner(Scanner in) throws CustomExceptions.InvalidInputException, IOException {
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
    public static String loadCourseDataFromDefaults() throws IOException {
        LOGGER.finer("Loading course data from default locations");
        String curriculumLocation = DefaultLocations.getDefaultCurriculumLocation();
        String offeringsLocation = DefaultLocations.getDefaultOfferingsLocation();
        String prerequisitesLocation = DefaultLocations.getDefaultPrerequisitesLocation();
        return loadCoursesFromSpecifiedLocations(curriculumLocation, offeringsLocation, prerequisitesLocation);
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
}
