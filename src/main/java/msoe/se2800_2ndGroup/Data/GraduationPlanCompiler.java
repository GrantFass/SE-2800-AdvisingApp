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
 * * Break out the adding of courses into its own method by Grant Fass on Sun, 9 May 2021
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
     * full curriculum for the specified major stored in the program. This method then goes
     * through each of the curriculum items. It will then add each item to a temporary Academic
     * term based on which Calendar term it occurs in. Terms that fall into the target credit
     * range are added to the graduation plan which is then returned.
     *
     * @param averageCreditsPerTerm the average target number of credits per term
     * @param creditTolerance       the number of credits to accept above or below the target.
     *                              note that if this value is too low then the plan will be
     *                              inaccurate
     * @return the sorted graduation plan as a list of Academic terms
     * @throws CustomExceptions.InvalidInputException if there is an issue retrieving the curriculum
     * @author : Grant Fass
     * @since : Sun, 9 May 2021
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
        AcademicTerm temporaryFallTerm = new AcademicTerm(numberOfFallTerms, Term.FALL);
        AcademicTerm temporaryWinterTerm = new AcademicTerm(numberOfWinterTerms, Term.WINTER);
        AcademicTerm temporarySpringTerm = new AcademicTerm(numberOfSpringTerms, Term.SPRING);

        for (CurriculumItem item : fullCurriculum) {
            if (item instanceof Course temporaryCourse) {
                if (fallCourses.contains(temporaryCourse)) {
                    temporaryFallTerm =
                            addCurriculumItemToTerm(temporaryFallTerm, Term.FALL, temporaryCourse,
                                                    numberOfFallTerms, maxCredits, minCredits,
                                                    graduationPlan);
                    numberOfFallTerms = temporaryFallTerm.getTermIndex();
                } else if (winterCourses.contains(temporaryCourse)) {
                    temporaryWinterTerm = addCurriculumItemToTerm(temporaryWinterTerm, Term.WINTER,
                                                                  temporaryCourse,
                                                                  numberOfWinterTerms, maxCredits,
                                                                  minCredits, graduationPlan);
                    numberOfWinterTerms = temporaryWinterTerm.getTermIndex();
                } else if (springCourses.contains(temporaryCourse)) {
                    temporarySpringTerm = addCurriculumItemToTerm(temporarySpringTerm, Term.SPRING,
                                                                  temporaryCourse,
                                                                  numberOfSpringTerms, maxCredits,
                                                                  minCredits, graduationPlan);
                    numberOfSpringTerms = temporarySpringTerm.getTermIndex();
                } else {
                    LOGGER.finer("COURSE NOT FOUND: Defaulting to add " + temporaryCourse.code() +
                                 " to FALL term.");
                    temporaryFallTerm =
                            addCurriculumItemToTerm(temporaryFallTerm, Term.FALL, temporaryCourse,
                                                    numberOfFallTerms, maxCredits, minCredits,
                                                    graduationPlan);
                    numberOfFallTerms = temporaryFallTerm.getTermIndex();
                }

            } else if (item instanceof Elective temporaryElective) {
                //Set up following a 3 variable binary truth table.
                if (temporaryFallTerm.compareTo(temporaryWinterTerm) <= 0 &&
                    temporaryFallTerm.compareTo(temporarySpringTerm) <= 0) {

                    temporaryFallTerm =
                            addCurriculumItemToTerm(temporaryFallTerm, Term.FALL, temporaryElective,
                                                    numberOfFallTerms, maxCredits, minCredits,
                                                    graduationPlan);
                    numberOfFallTerms = temporaryFallTerm.getTermIndex();
                } else if (temporaryWinterTerm.compareTo(temporaryFallTerm) < 0 &&
                           temporaryWinterTerm.compareTo(temporarySpringTerm) <= 0) {
                    temporaryWinterTerm = addCurriculumItemToTerm(temporaryWinterTerm, Term.WINTER,
                                                                  temporaryElective,
                                                                  numberOfWinterTerms, maxCredits,
                                                                  minCredits, graduationPlan);
                    numberOfWinterTerms = temporaryWinterTerm.getTermIndex();
                } else if (temporarySpringTerm.compareTo(temporaryFallTerm) < 0 &&
                           temporarySpringTerm.compareTo(temporaryWinterTerm) < 0) {
                    temporarySpringTerm = addCurriculumItemToTerm(temporarySpringTerm, Term.SPRING,
                                                                  temporaryElective,
                                                                  numberOfSpringTerms, maxCredits,
                                                                  minCredits, graduationPlan);
                    numberOfSpringTerms = temporarySpringTerm.getTermIndex();
                } else {
                    LOGGER.fine(
                            "ELECTIVE NOT FOUND: Defaulting to add " + temporaryElective.getCode() +
                            " to " + "FALL term.");
                    temporaryFallTerm =
                            addCurriculumItemToTerm(temporaryFallTerm, Term.FALL, temporaryElective,
                                                    numberOfFallTerms, maxCredits, minCredits,
                                                    graduationPlan);
                    numberOfFallTerms = temporaryFallTerm.getTermIndex();
                }
            }
        }
        LOGGER.finer("Sorting graduation plan");
        Collections.sort(graduationPlan);
        LOGGER.finer("Graduation plan sorted");
        return graduationPlan;
    }

    /**
     * This method adds a given curriculum item to the given term
     * <p>
     * This method will take in a curriculum item, a term, and data associated with that term in
     * order to add the item to the term. then if the term is full it will be added to the
     * graduation plan which is passed by reference.
     *
     * @param temporaryTerm           the Academic term to add the curriculum item to
     * @param term                    the Calender term of the Academic Term ie: Term.Fall
     * @param temporaryCurriculumItem the curriculum item to add to the Academic term
     * @param numberOfTerms           the current ones based index of the Academic terms for the
     *                                passed
     *                                Calendar term.
     * @param maxCredits              the maximum number of credits to use when constructing the
     *                                given term
     * @param minCredits              the minimum number of credits to use when constructing the
     *                                given term
     * @param graduationPlan          the graduation plan which is a list of Academic terms
     *                                passed by
     *                                reference
     * @return the Academic term that was passed in with the passed course added to it when that
     * term is not full. otherwise it will return a new term for that calendar term
     * @author : Grant Fass
     * @since : Sun, 9 May 2021
     */
    private static AcademicTerm addCurriculumItemToTerm(AcademicTerm temporaryTerm, Term term,
                                                        CurriculumItem temporaryCurriculumItem,
                                                        int numberOfTerms, int maxCredits,
                                                        int minCredits,
                                                        List<AcademicTerm> graduationPlan) {

        LOGGER.finer("Adding " + getCurriculumItemCode(temporaryCurriculumItem) + " to " +
                     term.season() + " term.");
        temporaryTerm.addItems(temporaryCurriculumItem);
        if (temporaryTerm.getNumberOfCredits() <= maxCredits &&
            temporaryTerm.getNumberOfCredits() >= minCredits) {
            graduationPlan.add(temporaryTerm);
            ++numberOfTerms;
            temporaryTerm = new AcademicTerm(numberOfTerms, term);
        }
        return temporaryTerm;
    }

    /**
     * method to return the course code for the given item
     *
     * @param item the item to get the code of
     * @return the items code
     * @author : Grant Fass
     * @since : Sun, 9 May 2021
     */
    private static String getCurriculumItemCode(CurriculumItem item) {
        return (item instanceof Course) ? ((Course) item).code() : ((Elective) item).getCode();
    }
}
