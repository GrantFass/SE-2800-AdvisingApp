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
 * Project Name: clean
 * Class Name: AcademicTerm
 * Description:
 * * <class description here>
 * The AcademicTerm class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by poptilec on Sunday, 25 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : poptilec
 * @since : Tuesday, 27 April 2021
 */

public class AcademicTerm {
    private String name;
    private Term term;
    private int numberOfCourses;
    private int numberOfCredits;
    private int avgCreditsPerCourse;
    private List<Course> courses = new ArrayList<>();

    public AcademicTerm(String name, Term term){
        this.name = name;
        this.term = term;
    }

    public boolean addCourse(Course course){
        courses.add(course);
        numberOfCredits+=course.credits();
        numberOfCourses++;
        updateAverageCredits();
        return true;
    }

    public boolean removeCourse(Course course){
        courses.remove(course);
        numberOfCredits -= course.credits();
        numberOfCourses--;
        updateAverageCredits();
        return true;
    }

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
