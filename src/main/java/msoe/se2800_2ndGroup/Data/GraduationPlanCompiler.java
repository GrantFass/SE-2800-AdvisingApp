package msoe.se2800_2ndGroup.Data;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.*;

import java.util.ArrayList;
import java.util.Collections;
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
 * Class Name: GraduationPlanCompiler
 * Description:
 * * This class is used to generate graduation plans and handle other responsibilities associated
 * with them.
 * The GraduationPlanCompiler class is responsible for:
 * * generating the courses that will take place in each term based on the number of credits per
 * term.
 * * Turning the generated graduation plan into a string for output
 * Modification Log:
 * * File Created by Grant on Sunday, 09 May 2021
 * * Moved existing methods from Compilers.java by Grant Fass on Sun, 9 May 2021
 * * Rewrote all code so that the ordering of courses in terms is more consistent and the
 * electives are not all stacked at the end by Grant Fass on Sun, 9 May 2021
 * * Add logger by Grant Fass on Sun, 9 May 2021
 * * Remove names from generating AcademicTerms and refactor comparisons to use built in
 * comparison override in the AcademicTerm class by Grant Fass on Sun, 9 May 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Sunday, 09 May 2021
 */
public class GraduationPlanCompiler {

    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * This method is used to generate the entire sorted graduation plan
     * <p>
     * This method generates the graduation plan based on the number of credits per term and the
     * tolerance for the credits.
     * This method first computes the max and min credit values. This method then retrieves the
     * full curriculum for the
     * specified major stored in the program. Following that the program splits the courses by
     * each term they occur in.
     * Then the Academic terms are generated for each of the credit terms before they are sorted
     * and returned
     *
     * @param averageCreditsPerTerm the average target number of credits per term
     * @param creditTolerance       the number of credits to accept above or below the target.
     *                              note that if this value is too low then the plan will be
     *                              inaccurate
     * @return the sorted graduation plan as a list of Academic terms
     * @throws CustomExceptions.InvalidInputException if there is an issue retrieving the curriculum
     * @author : Grant Fass
     * @since : Wed, 5 May 2021
     */
    public static List<AcademicTerm> generateGraduationPlanVersion2(int averageCreditsPerTerm,
                                                                    int creditTolerance)
    throws CustomExceptions.InvalidInputException {
        LOGGER.finer("generating credit minimum and maximums");
        final int minCredits = Math.max(averageCreditsPerTerm - creditTolerance, 12);
        final int maxCredits = Math.min(averageCreditsPerTerm + creditTolerance, 19);
        LOGGER.finer("getting full curriculum for stored major");
        List<CurriculumItem> fullCurriculum =
                Compilers.getCurriculaExcludingCompletedCourses(new ArrayList<>());
        LOGGER.finer("getting terms courses are offered in");
        List<CurriculumItem> fallCourses = Compilers.getUnsatisfiedCurriculumItemsForSpecifiedTerm(
                Compilers.getCourseOfferings(true, false, false), fullCurriculum);
        List<CurriculumItem> winterCourses = Compilers
                .getUnsatisfiedCurriculumItemsForSpecifiedTerm(
                        Compilers.getCourseOfferings(false, true, false), fullCurriculum);
        List<CurriculumItem> springCourses = Compilers
                .getUnsatisfiedCurriculumItemsForSpecifiedTerm(
                        Compilers.getCourseOfferings(false, false, true), fullCurriculum);
        LOGGER.finer("generating graduation plan");
        List<AcademicTerm> graduationPlan = new ArrayList<>();

        int numberOfFallTerms = 1;
        int numberOfWinterTerms = 1;
        int numberOfSpringTerms = 1;
        AcademicTerm temporaryFallTerm =
                new AcademicTerm(numberOfFallTerms, Term.FALL);
        AcademicTerm temporaryWinterTerm =
                new AcademicTerm(numberOfWinterTerms, Term.WINTER);
        AcademicTerm temporarySpringTerm =
                new AcademicTerm(numberOfSpringTerms, Term.SPRING);

        for (CurriculumItem item : fullCurriculum) {
            if (item instanceof Course temporaryCourse) {
                if (fallCourses.contains(temporaryCourse)) {
                    LOGGER.finer("Adding " + temporaryCourse.code() + " to FALL term.");
                    temporaryFallTerm.addItems(temporaryCourse);
                    if (temporaryFallTerm.getNumberOfCredits() <= maxCredits &&
                        temporaryFallTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporaryFallTerm);
                        ++numberOfFallTerms;
                        temporaryFallTerm =
                                new AcademicTerm(numberOfFallTerms, Term.FALL);
                    }
                } else if (winterCourses.contains(temporaryCourse)) {
                    LOGGER.finer("Adding " + temporaryCourse.code() + " to WINTER term.");
                    temporaryWinterTerm.addItems(temporaryCourse);
                    if (temporaryWinterTerm.getNumberOfCredits() <= maxCredits &&
                        temporaryWinterTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporaryWinterTerm);
                        ++numberOfWinterTerms;
                        temporaryWinterTerm =
                                new AcademicTerm(numberOfWinterTerms,
                                                 Term.WINTER);
                    }
                } else if (springCourses.contains(temporaryCourse)) {
                    LOGGER.finer("Adding " + temporaryCourse.code() + " to SPRING term.");
                    temporarySpringTerm.addItems(temporaryCourse);
                    if (temporarySpringTerm.getNumberOfCredits() <= maxCredits &&
                        temporarySpringTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporarySpringTerm);
                        ++numberOfSpringTerms;
                        temporarySpringTerm =
                                new AcademicTerm(numberOfSpringTerms,
                                                 Term.SPRING);
                    }
                } else {
                    LOGGER.finer("COURSE NOT FOUND: Defaulting to add " + temporaryCourse.code() +
                                " to FALL term.");
                    temporaryFallTerm.addItems(temporaryCourse);
                    if (temporaryFallTerm.getNumberOfCredits() <= maxCredits &&
                        temporaryFallTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporaryFallTerm);
                        ++numberOfFallTerms;
                        temporaryFallTerm =
                                new AcademicTerm(numberOfFallTerms, Term.FALL);
                    }
                }

            } else if (item instanceof Elective temporaryElective) {
                //Set up following a 3 variable binary truth table.
//                if (temporaryFallTerm.compareTo(temporaryWinterTerm) <= 0 && temporaryFallTerm.compareTo(temporarySpringTerm) <= 0) {
                if (temporaryFallTerm.getTermIndex() <= temporaryWinterTerm.getTermIndex() &&
                    temporaryFallTerm.getTermIndex() <= temporarySpringTerm.getTermIndex()) {
                    LOGGER.finer("Adding " + temporaryElective.getCode() + " to FALL term.");
                    temporaryFallTerm.addItems(temporaryElective);
                    if (temporaryFallTerm.getNumberOfCredits() <= maxCredits &&
                        temporaryFallTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporaryFallTerm);
                        ++numberOfFallTerms;
                        temporaryFallTerm =
                                new AcademicTerm(numberOfFallTerms, Term.FALL);
                    }
                }
//                else if (temporaryWinterTerm.compareTo(temporaryFallTerm) < 0 && temporaryWinterTerm.compareTo(temporarySpringTerm) <= 0) {
                else if (temporaryWinterTerm.getTermIndex() < temporaryFallTerm.getTermIndex() &&
                           temporaryWinterTerm.getTermIndex() <=
                           temporarySpringTerm.getTermIndex()) {
                    LOGGER.finer("Adding " + temporaryElective.getCode() + " to WINTER term.");
                    temporaryWinterTerm.addItems(temporaryElective);
                    if (temporaryWinterTerm.getNumberOfCredits() <= maxCredits &&
                        temporaryWinterTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporaryWinterTerm);
                        ++numberOfWinterTerms;
                        temporaryWinterTerm =
                                new AcademicTerm(numberOfWinterTerms,
                                                 Term.WINTER);
                    }
                }
//                else if (temporarySpringTerm.compareTo(temporaryFallTerm) < 0 && temporarySpringTerm.compareTo(temporaryWinterTerm) < 0) {
                else if (temporarySpringTerm.getTermIndex() < temporaryFallTerm.getTermIndex() &&
                           temporarySpringTerm.getTermIndex() <
                           temporaryWinterTerm.getTermIndex()) {
                    LOGGER.finer("Adding " + temporaryElective.getCode() + " to SPRING term.");
                    temporarySpringTerm.addItems(temporaryElective);
                    if (temporarySpringTerm.getNumberOfCredits() <= maxCredits &&
                        temporarySpringTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporarySpringTerm);
                        ++numberOfSpringTerms;
                        temporarySpringTerm =
                                new AcademicTerm(numberOfSpringTerms,
                                                 Term.SPRING);
                    }
                } else {
                    LOGGER.fine(
                            "ELECTIVE NOT FOUND: Defaulting to add " + temporaryElective.getCode() +
                            " to " + "FALL term.");
                    temporaryFallTerm.addItems(temporaryElective);
                    if (temporaryFallTerm.getNumberOfCredits() <= maxCredits &&
                        temporaryFallTerm.getNumberOfCredits() >= minCredits) {
                        //if within full range then add current term to graduation plan and create
                        // the next term to add courses to
                        graduationPlan.add(temporaryFallTerm);
                        ++numberOfFallTerms;
                        temporaryFallTerm =
                                new AcademicTerm(numberOfFallTerms, Term.FALL);
                    }
                }
            }
        }
        LOGGER.finer("Sorting graduation plan");
        Collections.sort(graduationPlan);
        LOGGER.finer("Graduation plan sorted");
        return graduationPlan;
    }
}
