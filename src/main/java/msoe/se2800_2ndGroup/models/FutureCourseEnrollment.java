package msoe.se2800_2ndGroup.models;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import msoe.se2800_2ndGroup.UI.Controller;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Term;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: magana041group2
 * Class Name: FutureCourseEnrollment
 * Description:
 * * <class description here>
 * The FutureCourseEnrollment class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by Teresa, Toohill on Wednesday, 28 April 2021
 * Methods Changed on 5/8
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Wednesday, 28 April 2021
 */
public class FutureCourseEnrollment {

    /**
     * Sorts arraylist of courses alphabetically.
     * This allows for easy sorting.
     * It gets the course codes to sort through that data
     * of each Course object.
     * It converts passed in arraylist to a string code arraylist
     * for sorting alphabetically.
     * Uses collections .sort() to sort.
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    public ArrayList<String> sort(ArrayList<Course> courses){
        ArrayList<String> courseSort = new ArrayList<String>();
        for(Course course : courses){
            courseSort.add(course.code());
        }

        //sorts string array alphabetically and in ascending order
        Collections.sort(courseSort);
        //returns the new course list
        return courseSort;
    }


    /**
     * Builds a Hashset by looping through
     * arraylist and adding values.
     * @author : Teresa Toohill
     * @since : Wed, 5 May 2021
     */
    public HashSet<String> oneForEachCourse(ArrayList<String> courses){
        //Create a HashSet based on the arraylist (only contains one of each course)
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(courses);
        return hashSet;
    }

    /**
     * Gets the number of occurrences
     * by using a for each loop to
     * loop through Hashset.
     * Sees if
     * @author : Teresa Toohill
     * @since : Wed, 5 May 2021
     */
    public HashSet<String> sumOccurences(HashSet<String> courses, ArrayList<String> allCourses){
        //tack on the values to the courses in the hashset or do
        // the hash set as strings and append String.format("\n Occurances: number")
        HashSet<String> occurences = new HashSet<String>();
        for(String course : allCourses){
            occurences.add("\n Occurances of " + course + ": " + Collections.frequency(allCourses, course));
        }
        return occurences;
    }
}
