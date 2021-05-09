package msoe.se2800_2ndGroup.Data;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: Manipulators
 * Description:
 * * Manipulating data of one form and turning it into another
 * The Manipulators class is responsible for:
 * * Manipulating data of one form and turning it into another
 * Modification Log:
 * * File Created by Grant on Thursday, 22 April 2021
 * * Moved static data manipulation methods from Model.java to Manipulators.java by Grant Fass on Thu, 22 Apr 2021
 * * Add methods to output short versions of courses, electives, and curriculum items as strings by Grant Fass on Wed, 5 May 2021
 * * Add method to output entire list of Academic Terms as a string by Grant Fass on Wed, 5 May 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 22 April 2021
 */
public class Manipulators {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * This method extracts the important information from a given offering and returns a string containing the values
     * <p>
     * This method uses String formatting to display a passed offering in a readable format.
     * The format is CODE CREDITS | DESCRIPTION : PREREQUISITES.
     *
     * @param offering the offering to extract information from
     * @return an offerings information in a string format
     * @throws CustomExceptions.InvalidInputException if the offering was null
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static String getOfferingAsString(Offering offering) throws CustomExceptions.InvalidInputException {
        if (offering == null) {
            LOGGER.warning("The input offering to convert to string was null");
            throw new CustomExceptions.InvalidInputException("The input offering was null");
        }
        return getCourseAsString(offering.course());
    }

    /**
     * this method automatically determines if the curriculum item is a course or an elective and returns the proper
     * short string based on that.
     * @param item the item to convert to a string
     * @return the item as a short string of the code and number of credits
     * @throws CustomExceptions.InvalidInputException if the item was null
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    public static String getCurriculumItemAsShortString(CurriculumItem item) throws CustomExceptions.InvalidInputException {
        if (item instanceof Elective) {
            return getElectiveAsShortString((Elective) item);
        } else {
            return getCourseAsShortString((Course) item);
        }
    }

    /**
     * this method extracts the important information from a given course and returns a string containing the values
     * <p>
     * this method uses string formatting to display a passed course in a readable format.
     * The format is CODE CREDITS | DESCRIPTION : PREREQUISITES.
     *
     * @param course the course to extract information from
     * @return a courses information in string format
     * @throws CustomExceptions.InvalidInputException if the course was null
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static String getCourseAsString(Course course) throws CustomExceptions.InvalidInputException {
        if (course == null) {
            LOGGER.warning("The input course to convert to string was null");
            throw new CustomExceptions.InvalidInputException("The input course was null");
        }
        String output = String.format("%10s %3s | %50s : %s", course.code(), course.credits(), course.description(), course.prerequisite());
        LOGGER.finest("Converting Course: " + output);
        return output + "\n";
    }

    /**
     * this method extracts the important information from a given course and returns a string containing the values
     * <p>
     * this method uses string formatting to display a passed course in a readable format.
     * The format is CODE | CREDITS
     *
     * @param course the course to extract information from
     * @return a courses information in string format
     * @throws CustomExceptions.InvalidInputException if the course was null
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static String getCourseAsShortString(Course course) throws CustomExceptions.InvalidInputException {
        if (course == null) {
            LOGGER.warning("The input course to convert to string was null");
            throw new CustomExceptions.InvalidInputException("The input course was null");
        }
        String output = String.format("%10s | %3s", course.code(), course.credits());
        LOGGER.finest("Converting Course: " + output);
        return output;
    }

    /**
     * this method extracts the important information from a given elective and returns a string containing the values
     * <p>
     * This method outputs the values for an elective in a readable format using the same formatting specifications
     * as the getCourseAsString method.
     *
     * @param elective the elective to extract information from
     * @return an electives information in string format
     * @throws CustomExceptions.InvalidInputException if the elective was null
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static String getElectiveAsString(Elective elective) throws CustomExceptions.InvalidInputException {
        if (elective == null) {
            LOGGER.warning("The input elective to convert to string was null");
            throw new CustomExceptions.InvalidInputException("The input elective was null");
        }
        String output = String.format("%10s %3s | %50s : %s", elective.getCode(), "?", "Elective Course Choice", "See Academic Catalog");
        LOGGER.finest("Converting Elective: " + output);
        return output + "\n";
    }

    /**
     * this method extracts the important information from a given elective and returns a string containing the values
     * <p>
     * This method outputs the values for an elective in a readable format using the same formatting specifications
     * as the getCourseAsShortString method. Only returns the course code and the number of credits
     *
     * @param elective the elective to extract information from
     * @return an electives information in string format
     * @throws CustomExceptions.InvalidInputException if the elective was null
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    public static String getElectiveAsShortString(Elective elective) throws CustomExceptions.InvalidInputException {
        if (elective == null) {
            LOGGER.warning("The input elective to convert to string was null");
            throw new CustomExceptions.InvalidInputException("The input elective was null");
        }
        String output = String.format("%10s | %3s", elective.getCode(), "3-4");
        LOGGER.finest("Converting Elective: " + output);
        return output;
    }

    /**
     * This method returns the list of offerings for the input terms as a readable string.
     * <p>
     * This method gets the ArrayList of offerings for the input terms.
     * This method then iterates through the offerings and extracts the useful information to a string.
     * This string is then appended to a string builder which is returned.
     *
     * @param offerings the list of offerings to convert to a string
     * @return the offerings for the input terms as a string.
     * @throws CustomExceptions.InvalidInputException the major was not found or there was an error converting a
     *                                                specific course
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static String getCourseOfferingsAsString(List<Offering> offerings) throws CustomExceptions.InvalidInputException {
        LOGGER.finer("Building string output for course offerings as string");
        StringBuilder builder = new StringBuilder();
        if (!offerings.isEmpty()) {
            builder.append(String.format("%10s %3s | %50s : %s\n", "CODE", "CR", "DESCRIPTION", "PREREQUISITES"));
            for (Offering o : offerings) {
                //format is CODE CREDITS | DESCRIPTION : PRERECS
                builder.append(Manipulators.getOfferingAsString(o));
            }
        } else {
            return "No Terms Selected\n";
        }
        return builder.toString();
    }

    /**
     * This method converts a list of curriculum items to a single string as output
     * <p>
     * This method first checks if the passed in listing of items is empty.
     * After verifying the items are not empty it iterates through each item checking if it is a course
     * or an elective, converting them to a string, then appending them to the builder to be returned.
     *
     * @param curriculumItems the list of curriculum items to convert into a string
     * @return a string containing either a message stating that no courses were found or a listing of each
     * course and elective in the list of curriculum items as a string.
     * @throws CustomExceptions.InvalidInputException if there is an issue converting a course or an elective
     *                                                to their string format
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static String getCurriculumItemsAsString(List<CurriculumItem> curriculumItems) throws CustomExceptions.InvalidInputException {
        LOGGER.finer("Building Curriculum Item Output");
        StringBuilder builder = new StringBuilder();
        if (!curriculumItems.isEmpty()) {
            builder.append(String.format("%7s %2s | %40s : %s\n", "CODE", "CR", "DESCRIPTION", "PREREQUISITES"));
            for (CurriculumItem curriculumItem : curriculumItems) {
                if (curriculumItem instanceof Course) {
                    builder.append(getCourseAsString((Course) curriculumItem));
                } else if (curriculumItem instanceof Elective) {
                    builder.append(getElectiveAsString((Elective) curriculumItem));
                }
            }
        } else {
            builder.append("No courses found\n");
        }
        return builder.toString();
    }

    /**
     * This method converts a list of courses to a list of course codes
     * @param courses the courses to convert to codes
     * @return a list of course codes
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static Collection<String> getCourseCodes(List<Course> courses) {
        Collection<String> out = new ArrayList<>();
        for(Course course: courses) {
            out.add(course.code());
        }
        return out;
    }

    /**
     * This method takes the passed in course string to remove any and all
     * non-alphanumeric characters in the string.
     * <p>
     * This method relies on the use of regex to remove unwanted characters
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://www.geeksforgeeks.org/how-to-remove-all-non-alphanumeric-characters-from-a-string-in-java/"}</a>: Reference for regex expression
     *
     * @param course course string to be standardized
     * @return course code with only capitalized letters and numbers
     * @author : Claudia Poptile
     * @since : Tue, 13 Apr 2021
     */
    public static String standardizeCourse(String course) {
        return course.toUpperCase().replaceAll("[^a-zA-Z0-9]", "").trim();
    }

    /**
     * method used to convert the graduation plan list to a string
     * @param graduationPlan the plan to convert
     * @return the plan as a string
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    public static String getGraduationPlanAsString(List<AcademicTerm> graduationPlan) {
        StringBuilder builder = new StringBuilder();
        for (AcademicTerm term: graduationPlan) {
            builder.append(term).append("\n");
        }
        return builder.toString();
    }
}
