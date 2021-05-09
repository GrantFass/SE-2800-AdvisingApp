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
 * Modification Log:
 * * File Created by poptilec on Sunday, 25 April 2021
 * * Refactor to use Curriculum Items instead of only Courses
 * * fix division by zero in credit averaging and fix to_string to no longer use octal values by
 * Grant Fass on Wed, 5 May 2021
 * * Update the listing of courses as strings by Grant Fass on Wed, 5 May 2021
 * * Add term index by Grant Fass on Sun, 9 May 2021
 * * Override default comparison by Grant Fass on Sun, 9 May 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : poptilec
 * @since : Sunday, 25 April 2021
 */

public class AcademicTerm implements Comparable<AcademicTerm> {
    private final String name;
    private final Term term;
    private int numberOfCourses;
    private int numberOfCredits;
    private double avgCreditsPerCourse;
    private int termIndex;
    private List<CurriculumItem> courses = new ArrayList<>();

    /**
     * Method creates a new Academic Term.
     *
     * @param name name of academic term (EX: 'Winter 2020' or 'Winter Junior Year')
     * @param term term of academic term (EX: 'Winter', 'Fall', 'Spring')
     * @param termIndex the index of which year the term occurs in. EX: the first spring term
     *                  would have an index of 1, the year 2 spring term would be index 2, etc...
     * @Modified by Grant Fass on Sun, 9 May 2021
     * @author : poptilec
     * @since : Tue, 27 Apr 2021
     */
    public AcademicTerm(String name, int termIndex, Term term) {
        this.name = name;
        this.termIndex = termIndex;
        this.term = term;
    }

    /**
     * method to return the term index which is commonly used for sorting the order of terms
     * @return the value of the term index
     * @author : Grant Fass
     * @since : Sun, 9 May 2021
     */
    public int getTermIndex() {return termIndex;}

    /**
     * Method adds a course to the Academic Term
     * <p>
     * Method adds a course to the courses List
     * Method adds the credits of course to total credits
     * Method updates the number of courses in Academic Term
     * Method recomputes the average number of credits per course
     *
     * @param course course to be added
     * @author : poptilec
     * @since : Tue, 27 Apr 2021
     */
    public void addItems(CurriculumItem course) {
        courses.add(course);
        if (course instanceof Elective) {
            numberOfCredits += 3;
        } else if (course instanceof Course) {
            numberOfCredits += ((Course) course).credits();
        }
        numberOfCourses++;
        updateAverageCredits();
    }

    /**
     * Method removes a course to the Academic Term
     * <p>
     * Method removes a course from the courses List
     * Method removes the credits of course from total credits
     * Method updates the number of courses in Academic Term
     * Method recomputes the average number of credits per course
     *
     * @param course course to be added
     * @author : poptilec
     * @since : Tue, 27 Apr 2021
     */
    public void removeCourse(CurriculumItem course) {
        courses.remove(course);
        if (course instanceof Elective) {
            numberOfCredits -= 3;
        } else if (course instanceof Course) {
            numberOfCredits -= ((Course) course).credits();
        }

        numberOfCourses--;
        updateAverageCredits();
    }

    /**
     * Method returns the courses in an Academic Term
     * *
     *
     * @return String displaying courses in an Academic Term
     * @throws CustomExceptions.InvalidInputException
     * @author : poptilec
     * @since : Tue, 27 Apr 2021
     */
    public String getCourses() throws CustomExceptions.InvalidInputException {
        StringBuilder builder = new StringBuilder();
        if (!courses.isEmpty()) {
            for (CurriculumItem course : courses) {
                builder.append(Manipulators.getCurriculumItemAsShortString(course));
            }
        } else {
            return "No courses found for Academic Term: " + name;
        }
        return builder.toString();
    }

    public String getName() {
        return name;
    }

    public Term getTerm() {
        return term;
    }

    public int getNumberOfCourses() {
        return numberOfCourses;
    }

    public double getAvgCreditsPerCourse() {
        return avgCreditsPerCourse;
    }

    public int getNumberOfCredits() {
        return numberOfCredits;
    }

    private void updateAverageCredits() {
        int credits = 0;
        int numCourses = 0;
        for (CurriculumItem course : courses) {
            if (course instanceof Elective) {
                credits += 3;
            } else if (course instanceof Course) {
                credits += ((Course) course).credits();
            }
            numCourses++;
        }
        if (numCourses == 0) {
            avgCreditsPerCourse = 0;
        } else {
            avgCreditsPerCourse = credits / (double) numCourses;
        }

    }

    @Override
    public String toString() {
        try {
            return String
                    .format("Name %s\nTerm %s\nNumber of Courses: %d\nNumber of Credits: " +
                            "%d\nAverage Credits per Course: %.2f\nList of Courses:\n%s",
                            name, term.season(), numberOfCourses, numberOfCredits,
                            avgCreditsPerCourse, getCourses());
        } catch (CustomExceptions.InvalidInputException e) {
            e.printStackTrace();
            return "error message?";
        }
    }


    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * this method overrides the default comparison of classes. It will first compare the term
     * index which represents which year an academic term occurs in. If the year is the same then
     * it will compare based on which catalog term the academic term occurs in
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.geeksforgeeks
     *  .org/how-to-override-compareto-method-in-java/"}</a>: Help overriding comparison
     *
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @author : Grant Fass
     * @since : Sun, 9 May 2021
     */
    @Override
    public int compareTo(AcademicTerm o) {
        if (this.getTermIndex() > o.getTermIndex()) {
            //this is greater
            return 1;
        } else if (this.getTermIndex() < o.getTermIndex()) {
            //o is greater
            return -1;
        } else {
            //both academic terms are in the same year and need to compare catalog terms
            if (this.getTerm().compareTo(o.getTerm()) > 0) {
                //this is greater
                return 1;
            } else if (this.getTerm().compareTo(o.getTerm()) < 0) {
                //o is greater
                return -1;
            } else if (this.getTerm().compareTo(o.getTerm()) == 0) {
                //they are equal is greater
                return 0;
            } else {
                return 0;
            }
        }
    }
}
