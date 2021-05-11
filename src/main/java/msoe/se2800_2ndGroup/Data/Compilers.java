package msoe.se2800_2ndGroup.Data;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions.InvalidInputException;
import msoe.se2800_2ndGroup.Graphing.GraphMaker;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.*;

import java.util.ArrayList;
import java.util.Calendar;
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
 * * Finish implementation to compile graduation plans as AcademicTerm objects by Grant Fass on Wed, 5 May 2021
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
     * <p>
     * This method compares the list of all offerings in a term against the list of unsatisfied courses and looks for matches.
     * If a match is found it is added to the list of possible output then returned.
     * Also adds all electives that have not yet been satisfied
     *
     * @param offeringsInTerm    the list of offerings for a given term
     * @param unsatisfiedCourses the list of courses that have not yet been satisfied
     * @return the list of the courses that have not yet been satisfied and occur in the specified term
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static List<CurriculumItem> getUnsatisfiedCurriculumItemsForSpecifiedTerm(List<Offering> offeringsInTerm, List<CurriculumItem> unsatisfiedCourses) {
        LOGGER.finer("Getting unsatisfied terms that match between offeringsInTerm and unsatisfiedCourses");
        ArrayList<CurriculumItem> outputCourses = new ArrayList<>();
        ArrayList<CurriculumItem> outputElectives = new ArrayList<>();
        for (CurriculumItem curriculumItem : unsatisfiedCourses) {
            for (Offering offering : offeringsInTerm) {
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
     * <p>
     * This method goes through the list of curricula searching for the correct major.
     * Once the correct major is found it uses the getUnsatisfiedItems method to retrieve a list of the courses
     * that have not yet been completed.
     *
     * @param completedCourses the list of already completed courses, usually from the loaded unofficial transcript
     * @return the list of CurriculumItems that have not yet been satisfied
     * @throws CustomExceptions.InvalidCurriculaException if the curriculum was not found for the stored major
     * @throws CustomExceptions.InvalidMajorException     if there is an issue with the stored major
     * @author : Grant Fass
     * @since : Tue, 13 Apr 2021
     */
    public static List<CurriculumItem> getCurriculaExcludingCompletedCourses(ArrayList<Course> completedCourses) throws CustomExceptions.InvalidInputException {
        Data.verifyMajor();
        LOGGER.fine("Searching For Curricula");
        for (Curriculum curriculum : Data.getCurricula()) {
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
     * <p>
     * This method goes through all of the offerings that were stored during course data loading.
     * For each of the offerings it will determine if it is available for the given term and major.
     * This method uses the stored major.
     *
     * @param displayFall   a boolean representing weather or not to collect Offerings for the fall term
     * @param displayWinter a boolean representing weather or not to collect Offerings for the winter term
     * @param displaySpring a boolean representing weather or not to collect Offerings for the spring term
     * @return an ArrayList of Offerings from the selected terms
     * @throws CustomExceptions.InvalidMajorException     the major was not found.
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
     *
     * @param curriculumItems      the list of items to check for satisfied prerequisites
     * @param completedCourseCodes the list of completed course codes used to satisfy prerequisites
     * @return the list of items only containing courses that have satisfied prerequisites and electives
     * @author : Grant Fass
     * @since : Thu, 22 Apr 2021
     */
    public static List<CurriculumItem> getCurriculumItemsWithSatisfiedPrerequisites(List<CurriculumItem> curriculumItems, Collection<String> completedCourseCodes) {
        List<CurriculumItem> output = new ArrayList<>();
        for (CurriculumItem curriculumItem : curriculumItems) {
            if ((curriculumItem instanceof Course) && ((Course) curriculumItem).prerequisite().satisfiedBy(completedCourseCodes) || (curriculumItem instanceof Elective)) {
                output.add(curriculumItem);
            }
        }
        return output;
    }

    /**
     * This method process course recommendations for various terms and returns them
     * <p>
     * This method first verifies that the Major, Offerings, and Transcript have been loaded and are not empty.
     * This method then computes the offerings available.
     * This method then finds the list of courses not yet satisfied in the curriculum and compares it against the offerings.
     *
     * @param getFall   boolean value of if recommendations should be generated for the fall term
     * @param getWinter boolean value of if recommendations should be generated for the winter term
     * @param getSpring boolean value of if recommendations should be generated for the spring term
     * @return a List of CurriculumItem containing the course recommendations for the passed terms
     * @throws CustomExceptions.InvalidInputException for various reasons like not having a major stored, failing to validate major,
     *                                                failing to validate offerings, failing to validate transcript, or not having a curriculum for the major.
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
     * <p>
     * This method filters the input String to clear whitespaces, hyphens, and underscores.
     * This method searches through prerequisites to find a course with a matching code.
     * This method returns prerequisites based on if a course was found or not.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://www.w3schools.com/java/java_regex.asp"}</a>: Help determining regex expressions for filtering input String
     *
     * @param course the course String that is sought for
     * @return The prerequisite codes for a given course as a String
     * @author : Claudia Poptile
     * @since : Mon, 12 Apr 2021
     */
    public static String getPrerequisitesForCourse(String course) {
        course = Manipulators.standardizeCourse(course);
        Course selected = null;
        for (Course prerequisiteCourse : Data.prerequisiteCourses) {
            if (prerequisiteCourse.code().equals(course)) {
                selected = prerequisiteCourse;
                break;
            }
        }
        LOGGER.finer(String.format("Searching for prerequisites for the course with code: %s", selected == null ? "null" : selected.code()));

        if (selected != null) {
            if (selected.prerequisite() instanceof AndPrerequisite and) {
                return ("Prerequisites: " + ((SinglePrerequisite) and.left()).code() + " and " + ((SinglePrerequisite) and.right()).code());
            } else if (selected.prerequisite() instanceof OrPrerequisite or) {
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

    //region Graduation Plan methods

    /**
     * This method is used to generate the entire sorted graduation plan
     * <p>
     * This method generates the graduation plan based on the number of credits per term and the tolerance for the credits.
     * This method first computes the max and min credit values. This method then retrieves the full curriculum for the
     * specified major stored in the program. Following that the program splits the courses by each term they occur in.
     * Then the Academic terms are generated for each of the credit terms before they are sorted and returned
     *
     * @param averageCreditsPerTerm the average target number of credits per term
     * @param creditTolerance       the number of credits to accept above or below the target.
     *                              note that if this value is too low then the plan will be inaccurate
     * @return the sorted graduation plan as a list of Academic terms
     * @throws InvalidInputException if there is an issue retrieving the curriculum
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    public static List<AcademicTerm> generateGraduationPlan(int averageCreditsPerTerm, int creditTolerance) throws InvalidInputException {
        final int minCredits = Math.max(averageCreditsPerTerm - creditTolerance, 12);
        final int maxCredits = Math.min(averageCreditsPerTerm + creditTolerance, 19);

        List<CurriculumItem> fullCurriculum = getCurriculaExcludingCompletedCourses(new ArrayList<>());

        List<CurriculumItem> fallCourses = getUnsatisfiedCurriculumItemsForSpecifiedTerm(getCourseOfferings(true, false, false), fullCurriculum);
        List<CurriculumItem> winterCourses = getUnsatisfiedCurriculumItemsForSpecifiedTerm(getCourseOfferings(false, true, false), fullCurriculum);
        List<CurriculumItem> springCourses = getUnsatisfiedCurriculumItemsForSpecifiedTerm(getCourseOfferings(false, false, true), fullCurriculum);
        winterCourses.removeAll(fallCourses);
        springCourses.removeAll(fallCourses);
        springCourses.removeAll(winterCourses);

        List<AcademicTerm> fallTerms = generateAllForSingleTerm(fallCourses, "Fall", Term.FALL, maxCredits, minCredits);
        List<AcademicTerm> winterTerms = generateAllForSingleTerm(winterCourses, "Winter", Term.WINTER, maxCredits, minCredits);
        List<AcademicTerm> springTerms = generateAllForSingleTerm(springCourses, "Spring", Term.SPRING, maxCredits, minCredits);

        return getSortedGraduationPlanFromTerms(fallTerms, winterTerms, springTerms);
    }

    /**
     * This method is used to generate the sorted graduation plan output from the listing of each academic term in a catalog term.
     * <p>
     * This method goes through each term in order and adds them to the graduation plan based on their occuring order.
     *
     * @param fallTerms   the academic terms that occur in the Fall quarter
     * @param winterTerms the academic terms that occur in the Winter quarter
     * @param springTerms the academic terms that occur in the Spring quarter
     * @return the sorted graduation list
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    private static List<AcademicTerm> getSortedGraduationPlanFromTerms(List<AcademicTerm> fallTerms, List<AcademicTerm> winterTerms, List<AcademicTerm> springTerms) {
        List<AcademicTerm> graduationPlan = new ArrayList<>();
        AcademicTerm temp;
        do {
            if (!fallTerms.isEmpty()) {
                temp = fallTerms.remove(0);
                graduationPlan.add(temp);
            }
            if (!winterTerms.isEmpty()) {
                temp = winterTerms.remove(0);
                graduationPlan.add(temp);
            }
            if (!springTerms.isEmpty()) {
                temp = springTerms.remove(0);
                graduationPlan.add(temp);
            }
        } while (!(fallTerms.isEmpty() && winterTerms.isEmpty() && springTerms.isEmpty()));
        return graduationPlan;
    }

    /**
     * TODO: need to do this only for single term then return both the term and the list of unused courses...
     * TODO: this is so that not all the electives end up in the fall term...
     * TODO: could also just change the term on the last few as they will probably be only electives...
     * This method is used to generate all of the AcademicTerm objects which contain courses based on a given term.
     * <p>
     * This method takes in a list of curriculum items which contains all of the courses that are offered in the
     * curriculum for the specified term. This method also takes in the term and a maximum and minimum number of credits
     * to use when generating terms. For this method to work correctly there should be a difference between the two
     * credit values of at least 2. Note that the lower the difference the lower the accuracy in generating courses.
     * <p>
     * This method will begin by popping each course off of the list one at a time. Each of these popped courses is
     * added to a temporary term. Once the term is within the target credit range it is added to the output and reset.
     * This continues until all of the courses have been exhausted.
     *
     * @param courseListOfferedForTermInCurriculum all of the courses in the curriculum for the specified term
     * @param termName                             the name for the term.
     * @param term                                 the specified term IE: Fall, Winter, Spring
     * @param maxCredits                           the maximum number of credits to aim for in a term
     * @param minCredits                           the minimum number of credits to aim for in a term
     * @return the list of all the generated academic terms
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    private static List<AcademicTerm> generateAllForSingleTerm(List<CurriculumItem> courseListOfferedForTermInCurriculum, String termName, Term term, int maxCredits, int minCredits) {
        List<AcademicTerm> output = new ArrayList<>();
        int termCount = 1;
        AcademicTerm temp = new AcademicTerm(termName + " " + termCount, term);
        while (!courseListOfferedForTermInCurriculum.isEmpty()) {
            //pop course off top
            CurriculumItem item = courseListOfferedForTermInCurriculum.remove(0);

            //add the popped course to the current term.
            temp.addItems(item);
            //create new term based on if full or not EX: fall 1
            if (temp.getNumberOfCredits() <= maxCredits && temp.getNumberOfCredits() >= minCredits) {
                //if within full range then add current term to graduation plan and create the next term to add courses to
                output.add(temp);
                termCount++;
                temp = new AcademicTerm(termName + " " + termCount, term);
            }
        }
        if (temp.getNumberOfCredits() != 0) {
            output.add(temp);
        }
        return output;
    }

    /**
     * This method is used to generate the projected graduation date
     * <p>
     * This method generates the graduation plan.
     * This method retrieves how many terms are in the graduation plan to calculate year of graduation.
     *
     *
     * @@author : Claudia Poptile
     * @since : Sat, 8 May 2021
     * @return expected graduation date as a string
     * @throws InvalidInputException
     */
    public static String getGraduationDate() throws InvalidInputException {
        List<AcademicTerm> graduationPlan = generateGraduationPlan(16, 2);
        String season = graduationPlan.get(graduationPlan.size()-1).getTerm().season();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int numberOfTerms = graduationPlan.size();
        int gradYear = (numberOfTerms/3) +currentYear;

        return season + " " + gradYear;
    }

    //endregion
}
