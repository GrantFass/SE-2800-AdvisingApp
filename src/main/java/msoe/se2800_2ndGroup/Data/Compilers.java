package msoe.se2800_2ndGroup.Data;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions.InvalidInputException;
import msoe.se2800_2ndGroup.Graphing.GraphMaker;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
 * Class Name: Compilers
 * Description:
 * * Compiling data of various forms into one form
 * The Compilers class is responsible for:
 * * Compiling data that is in multiple forms and or multiple locations into one format and returning it
 * Modification Log:
 * * File Created by Grant on Thursday, 22 April 2021
 * * Moved compilation methods from Model.java to Compilers.java by Grant Fass on Thu, 22 Apr 2021
 * * Add method to only get courses with satisfied prerequisites by Grant Fass on Thu, 22 Apr 2021
 * * Fix output of duplicate course codes in course recommendations due to electives being satisfied by Grant Fass on Thu, 22 Apr 2021
 * * Throw exception on bad prerequisite graph course code by Hunter Turcin on Mon, 26 Apr 2021
 * * Began implementation to compile graduation plans by Grant Fass on Wed, 5 May 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 22 April 2021
 */
public class Compilers {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * This method is used to get the unsatisfied courses that occur in the given term.
     *
     * This method compares the list of all offerings in a term against the list of unsatisfied courses and looks for matches.
     * If a match is found it is added to the list of possible output then returned.
     * Also adds all electives that have not yet been satisfied
     *
     * @param offeringsInTerm the list of offerings for a given term
     * @param unsatisfiedCourses the list of courses that have not yet been satisfied
     * @return the list of the courses that have not yet been satisfied and occur in the specified term
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static List<CurriculumItem> getUnsatisfiedCurriculumItemsForSpecifiedTerm(List<Offering> offeringsInTerm, List<CurriculumItem> unsatisfiedCourses) {
        LOGGER.finer("Getting unsatisfied terms that match between offeringsInTerm and unsatisfiedCourses");
        ArrayList<CurriculumItem> outputCourses = new ArrayList<>();
        ArrayList<CurriculumItem> outputElectives = new ArrayList<>();
        for (CurriculumItem curriculumItem: unsatisfiedCourses) {
            for (Offering offering: offeringsInTerm) {
                if (!(curriculumItem instanceof Elective) && curriculumItem.satisfiedBy(offering.course())) {
                    LOGGER.finer("Adding Course: " + curriculumItem);
                    outputCourses.add(offering.course());
                } else if (curriculumItem instanceof Course) {
                    LOGGER.finest(String.format("Course %s does not match Offering %s", ((Course) curriculumItem).code(), offering.course().code()));
                }
            }
            if (curriculumItem instanceof Elective) {
                LOGGER.finer("Adding Elective: " + curriculumItem);
                outputElectives.add(curriculumItem);
            }
        }
        outputCourses.addAll(outputElectives);
        return outputCourses;
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
     * @throws CustomExceptions.InvalidCurriculaException if the curriculum was not found for the stored major
     * @throws CustomExceptions.InvalidMajorException if there is an issue with the stored major
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static List<CurriculumItem> getCurriculaExcludingCompletedCourses(ArrayList<Course> completedCourses) throws CustomExceptions.InvalidInputException {
        Data.verifyMajor();
        LOGGER.fine("Searching For Curricula");
        for (Curriculum curriculum: Data.getCurricula()) {
            LOGGER.finest("Searching if specified curricula matches: " + curriculum.major());
            if (curriculum.major().equalsIgnoreCase(Data.getMajor())) {
                LOGGER.finest("Found matching curricula: " + curriculum.major());
                return curriculum.getUnsatisfiedItems(completedCourses);
            }
        }
        LOGGER.warning("Curriculum for stored major was not found");
        throw new CustomExceptions.InvalidCurriculaException("Curriculum for selected major not found");
    }

    /**
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
     * @throws CustomExceptions.InvalidMajorException the major was not found.
     * @throws CustomExceptions.InvalidOfferingsException if the offerings list is empty
     * @author : Grant Fass
     * @since : Wed, 7 Apr 2021
     */
    public static ArrayList<Offering> getCourseOfferings(boolean displayFall, boolean displayWinter, boolean displaySpring) throws CustomExceptions.InvalidInputException {
        if (!displayFall && !displayWinter && !displaySpring) {
            return new ArrayList<>();
        }
        Data.verifyMajor();
        ArrayList<Offering> offeringsBySeason = new ArrayList<>();
        //collect the offerings by term available for the major
        try {
            Data.verifyOfferings();
            LOGGER.finer("Collecting course offerings by term for major: " + Data.major);
            for (Offering offering : Data.getOfferings()) {
                if (offering.availability().get(Data.getMajor()).equals(Term.FALL) && displayFall) {
                    offeringsBySeason.add(offering);
                } else if (offering.availability().get(Data.getMajor()).equals(Term.WINTER) && displayWinter) {
                    offeringsBySeason.add(offering);
                } else if (offering.availability().get(Data.getMajor()).equals(Term.SPRING) && displaySpring) {
                    offeringsBySeason.add(offering);
                }
            }
        } catch (NullPointerException e) {
            LOGGER.log(Level.WARNING, String.format("The specified major %s was not found which means it was not input correctly", Data.getMajor()), e);
            throw new CustomExceptions.InvalidMajorException(String.format("The specified major %s was not found which means it was not input correctly", Data.getMajor()));
        }
        return offeringsBySeason;
    }

    /**
     * This method processes a list of curriculum items and determines if the items contained within have their prerequisites satisfied
     * @param curriculumItems the list of items to check for satisfied prerequisites
     * @param completedCourseCodes the list of completed course codes used to satisfy prerequisites
     * @return the list of items only containing courses that have satisfied prerequisites and electives
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static List<CurriculumItem> getCurriculumItemsWithSatisfiedPrerequisites(List<CurriculumItem> curriculumItems, Collection<String> completedCourseCodes) {
        List<CurriculumItem> output = new ArrayList<>();
        for (CurriculumItem curriculumItem: curriculumItems) {
            if ((curriculumItem instanceof Course) && ((Course) curriculumItem).prerequisite().satisfiedBy(completedCourseCodes) || (curriculumItem instanceof Elective)) {
                output.add(curriculumItem);
            }
        }
        return output;
    }

    /**
     * This method process course recommendations for various terms and returns them
     *
     * This method first verifies that the Major, Offerings, and Transcript have been loaded and are not empty.
     * This method then computes the offerings available.
     * This method then finds the list of courses not yet satisfied in the curriculum and compares it against the offerings.
     *
     * @param getFall boolean value of if recommendations should be generated for the fall term
     * @param getWinter boolean value of if recommendations should be generated for the winter term
     * @param getSpring boolean value of if recommendations should be generated for the spring term
     * @return a List of CurriculumItem containing the course recommendations for the passed terms
     * @throws CustomExceptions.InvalidInputException for various reasons like not having a major stored, failing to validate major,
     * failing to validate offerings, failing to validate transcript, or not having a curriculum for the major.
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static List<CurriculumItem> getCourseRecommendation(boolean getFall, boolean getWinter, boolean getSpring) throws CustomExceptions.InvalidInputException {
        Data.verifyMajor();
        Data.verifyOfferings();
        Data.verifyTranscript();
        //start computing recommendations
        ArrayList<Offering> offeringsForTerm = getCourseOfferings(getFall, getWinter, getSpring);
        List<CurriculumItem> uncompletedCurriculumCourses = getCurriculaExcludingCompletedCourses(Data.getTranscriptCourses());
        List<CurriculumItem> unsatisfiedCurriculumItems = getUnsatisfiedCurriculumItemsForSpecifiedTerm(offeringsForTerm, uncompletedCurriculumCourses);
        return getCurriculumItemsWithSatisfiedPrerequisites(unsatisfiedCurriculumItems, Manipulators.getCourseCodes(Data.getTranscriptCourses()));
    }

    /**
     * This method searches through the prerequisites list in order to
     * return the prerequisites for an inputted course.
     *
     * This method filters the input String to clear whitespaces, hyphens, and underscores.
     * This method searches through prerequisites to find a course with a matching code.
     * This method returns prerequisites based on if a course was found or not.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.w3schools.com/java/java_regex.asp"}</a>: Help determining regex expressions for filtering input String
     *
     * @param course the course String that is sought for
     * @return The prerequisite codes for a given course as a String
     * @author : Claudia Poptile
     * @since : Mon, 12 Apr 2021
     */
    public static String getPrerequisitesForCourse(String course) {
        course = Manipulators.standardizeCourse(course);
        Course selected = null;
        for (Course prerequisiteCourse: Data.prerequisiteCourses){
            if (prerequisiteCourse.code().equals(course)){
                selected = prerequisiteCourse;
                break;
            }
        }
        LOGGER.finer(String.format("Searching for prerequisites for the course with code: %s", selected == null ? "null" : selected.code()));

        if (selected != null) {
            if (selected.prerequisite() instanceof AndPrerequisite and) {
                return ("Prerequisites: " + ((SinglePrerequisite) and.left()).code() + " and " + ((SinglePrerequisite) and.right()).code());
            } else if (selected.prerequisite() instanceof OrPrerequisite or){
                return ("Prerequisites: " + ((SinglePrerequisite) or.left()).code() + " or " + ((SinglePrerequisite) or.right()).code());
            } else if (selected.prerequisite() instanceof SinglePrerequisite single) {
                return ("Prerequisites: " + single.code());
            } else {
                return ("Prerequisites: No prerequisite needed");
            }
        } else {
            return "No course found.";
        }
    }

    /**
     * TODO: test me
     * Get a textual representation of a graph of a course's prerequisites.
     *
     * @param code the code of the course to analyze
     * @return the course's prerequisite graph
     * @throws InvalidInputException no course exists for code
     * @author Hunter T.
     */
    public static String getCoursePrerequisiteGraph(String code) throws InvalidInputException {
        LOGGER.finer("Generating Course Prerequisite Graph");
        Course course = null;

        for (final var search : Data.getPrerequisiteCourses()) {
            if (search.code().equals(code)) {
                course = search;
                break;
            }
        }

        if (course == null) {
            throw new CustomExceptions.InvalidInputException("no course exists for code: " + code);
        }

        final var graph = GraphMaker.getGraph(course, Data.getPrerequisiteCourses());

        return graph.getStringGraph();
    }

    public static List<AcademicTerm> generateGraduationPlan(int averageCreditsPerTerm, int creditTolerance) throws InvalidInputException {
        final int minCredits = Math.max(averageCreditsPerTerm - creditTolerance, 12);
        final int maxCredits = Math.min(averageCreditsPerTerm + creditTolerance, 19);
        List<AcademicTerm> graduationPlan = new ArrayList<>();
        List<CurriculumItem> fullCurriculum = getCurriculaExcludingCompletedCourses(new ArrayList<>());
        List<CurriculumItem> fallCourses = getUnsatisfiedCurriculumItemsForSpecifiedTerm(getCourseOfferings(true, false, false), fullCurriculum);
        List<CurriculumItem> winterCourses = getUnsatisfiedCurriculumItemsForSpecifiedTerm(getCourseOfferings(false, true, false), fullCurriculum);
        List<CurriculumItem> springCourses = getUnsatisfiedCurriculumItemsForSpecifiedTerm(getCourseOfferings(false, false, true), fullCurriculum);

        //TODO: do this for each of the 3 terms
        //TODO: move this to a method
        int termCount = 1;
        AcademicTerm temp = new AcademicTerm("Fall " + termCount, Term.FALL);
        while (!fallCourses.isEmpty()) {
            //pop course off top
            CurriculumItem item = fallCourses.remove(0);

            //add the popped course to the current term. //TODO: check if this should be done first, also check that the final course term is added
            temp.addItems(item);
            //create new term based on if full or not EX: fall 1
            if (temp.getNumberOfCredits() <= maxCredits && temp.getNumberOfCredits() >= minCredits) {
                //if within full range then add current term to graduation plan and create the next term to add courses to
                graduationPlan.add(temp);
                termCount++;
                temp = new AcademicTerm("Fall " + termCount, Term.FALL);
            }
        }

        //TODO: sort terms by fall, winter, spring and number so ordered correctly.

        /*
        can probably do something like pop each item off top of the items list, verify what term its in, add it to that term till full then move to following year
         */


        return graduationPlan;
    }
}
