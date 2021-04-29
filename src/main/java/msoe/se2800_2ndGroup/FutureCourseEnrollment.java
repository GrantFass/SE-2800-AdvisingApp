package msoe.se2800_2ndGroup;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Term;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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
    //Best to moved elsewhere, but unsure where at the moment
    //Will ask team tomorrow

    /**
     * This method allows the user to select multiple
     * PDF transcript files for upload
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    public void readInMultipleFiles(){
        //get all .pdf files in a directory
        System.out.println("Please select multiple transcripts for" +
                "Future Course Enrollment:");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select All Files Desired");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*"));
        //load all of the pdf files and put their courses into one big arraylist
        //ArrayList<File> files = fileChooser.showOpenMultipleDialog(stage);
    }

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

    private HashSet<Course> oneForEachCourse(ArrayList<Course> courses){
        //Create a HashSet based on the arraylist (only contains one of each course)
        HashSet<Course> hashSet = new HashSet<>();
        courses.forEach(course ->
        {
            hashSet.add(course);
        });
        return hashSet;
    }

    /**
     * This method gets the projected
     * enrollment for one course for one term
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    public Course getOneTermEnrollmentForOneCourse(){
        Course course = new Course("MA 000");
        return course;
    }

    /**
     * This method gets the projected
     * enrollment for one course for multiple terms
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    public ArrayList<Course> getOneTermEnrollment(){
        ArrayList<Course> courses = new ArrayList<>();
        return courses;
    }

    /**
     * Get term preference
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    public ArrayList<Term> getTermPreference(){
        ArrayList<Term> terms = new ArrayList<>();
        return terms;
    }

    /**
     * Enrollment counts
     * @author : Teresa Toohill
     * @since : Wed, 28 Apr 2021
     */
    public int getEnrollmentCounts(ArrayList<Course> courses){
        return courses.size();
    }

}
