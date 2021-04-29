package msoe.se2800_2ndGroup.models;

import msoe.se2800_2ndGroup.Data.Manipulators;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: AcademicTerm
 * Description:
 * An Academic Term that holds a list of courses and their credits for a specific Term
 * The AcademicTerm class is responsible for:
 * * Holding and returning courses in a given term
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by poptilec on Sunday, 25 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : poptilec
 * @since : Sunday, 25 April 2021
 */

public class AcademicTerm {
    private String name;
    private Term term;
    private int numberOfCourses;
    private int numberOfCredits;
    private int avgCreditsPerCourse;
    private List<Course> courses = new ArrayList<>();

    /**
     * Method creates a new Academic Term.
     *
     * @@author : poptilec
     * @since : Tue, 27 Apr 2021
     * @param name name of academic term
     * @param term term of academic term
     */
    public AcademicTerm(String name, Term term){
        this.name = name;
        this.term = term;
    }

    /**
     * Method adds a course to the Academic Term
     *
     * Method adds a course to the courses List
     * Method adds the credits of course to total credits
     * Method updates the number of courses in Academic Term
     * Method recomputes the average number of credits per course
     *
     * @@author : poptilec
     * @since : Tue, 27 Apr 2021
     * @param course course to be added
     */
    public void addCourse(Course course){
        courses.add(course);
        numberOfCredits+=course.credits();
        numberOfCourses++;
        updateAverageCredits();
    }

    /**
     * Method removes a course to the Academic Term
     *
     * Method removes a course from the courses List
     * Method removes the credits of course from total credits
     * Method updates the number of courses in Academic Term
     * Method recomputes the average number of credits per course
     *
     * @@author : poptilec
     * @since : Tue, 27 Apr 2021
     * @param course course to be added
     */
    public void removeCourse(Course course){
        courses.remove(course);
        numberOfCredits -= course.credits();
        numberOfCourses--;
        updateAverageCredits();
    }

    /**
     * Method returns the courses in an Academic Term
     **
     * @@author : poptilec
     * @since : Tue, 27 Apr 2021
     * @return String displaying courses in an Academic Term
     * @throws CustomExceptions.InvalidInputException
     */
    public String getCourses() throws CustomExceptions.InvalidInputException {
        StringBuilder builder = new StringBuilder();
        if (!courses.isEmpty()) {
            for (Course course : courses) {
                builder.append(Manipulators.getCourseAsString(course));
            }
        } else {
            return "No courses found for Academic Term: " + name;
        }
        return builder.toString();
    }

    public String getName(){
        return name;
    }

    public Term getTerm(){
        return term;
    }

    public int getNumberOfCourses(){
        return numberOfCourses;
    }

    public int getAvgCreditsPerCourse(){
        return avgCreditsPerCourse;
    }

    public int getNumberOfCredits(){
        return numberOfCredits;
    }

    private void updateAverageCredits(){
        int credits = 0;
        int numCourses = 0;
        for (Course course: courses){
            credits += course.credits();
            numCourses++;
        }
        avgCreditsPerCourse = credits/numCourses;
    }

    @Override
    public String toString() {
        try {
            return String.format("Name %s \n Term %s \n Number of Courses: %o \n Number of Credits: %o \n Average Credits per Course: %o \n List of Course: \n %s",
                    name, term.season(), numberOfCourses, numberOfCredits, avgCreditsPerCourse, getCourses());
        } catch (CustomExceptions.InvalidInputException e) {
            e.printStackTrace();
            return "error message?";
        }
    }
}
