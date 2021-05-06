package msoe.se2800_2ndGroup;

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
 * * File Created by toohillt on Wednesday, 28 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Wednesday, 28 April 2021
 */
public class FutureCourseEnrollment {

    /**
     * Sorts arraylist alphabetically
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    private ArrayList<Course> sort(ArrayList<Course> courses){
        //Converts passed in array to a string array for sorting alphabetically
        ArrayList<String> courseSort = new ArrayList<>();
        for(int i = 0; i < courses.size(); i++){
            courseSort.add(courses.get(i).toString());
        }

        ArrayList<Course> newCourseList = new ArrayList<>();
        //sorts string array alphabetically and in ascending order
        Collections.sort(courseSort);
        for(int i = 0; i < newCourseList.size(); i++){
            Course course = new Course(courseSort.get(i));
            newCourseList.add(course);
        }
        //returns the new course list
        return newCourseList;
    }


    /**
     * Builds a Hashset
     * @author : Teresa Toohill
     * @since : Wed, 5 May 2021
     */
    private HashSet<Course> oneForEachCourse(ArrayList<Course> courses){
        //Create a HashSet based on the arraylist (only contains one of each course)
        HashSet<Course> hashSet = new HashSet<>();
        for(int i = 0; i < courses.size(); i++){
            hashSet.add(courses.get(i));
        }
        return hashSet;
    }

    /**
     * Gets the number of occurrences
     * @author : Teresa Toohill
     * @since : Wed, 5 May 2021
     */
    public HashSet<String> sumOccurences(HashSet<Course> courses){
        //tack on the values to the courses in the hashset or do
        // the hash set as strings and append String.format("\n Occurances: number")
        HashSet<String> occurences = new HashSet<String>();
        for(Course course : courses){
            int num = -1;
            num += Collections.frequency(courses, course);
            occurences.add("\n Occurances: " + num);
        }
        return occurences;
    }
}
