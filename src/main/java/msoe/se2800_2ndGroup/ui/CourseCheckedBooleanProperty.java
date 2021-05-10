package msoe.se2800_2ndGroup.ui;

import javafx.beans.property.BooleanPropertyBase;
import msoe.se2800_2ndGroup.Data.Data;
import msoe.se2800_2ndGroup.models.Course;

/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: CourseCheckedBooleanProperty
 * Creation Date: Monday, 3 May 2021
 * Original Author: Hunter Turcin
 * Description: A JavaFX property for manipulating course completion status.
 * The CourseCheckedBooleanProperty class is responsible for:
 *     - manipulating course completion status
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-05-03
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 3 May 2021
 */
public class CourseCheckedBooleanProperty extends BooleanPropertyBase {
    private final Course course;

    /**
     * Create a new property for manipulating the checked state of this course.
     *
     * @param course course to keep track of
     * @author : Hunter Turcin
     * @since : Mon, 3 May 2021
     */
    public CourseCheckedBooleanProperty(Course course) {
        this.course = course;
    }

    @Override
    public boolean get() {
        return Data.isCourseChecked(course);
    }

    @Override
    public void set(boolean checked) {
        Data.setCourseChecked(course, checked);
        invalidated();
        fireValueChangedEvent();
    }

    @Override
    public Object getBean() {
        return null;
    }

    @Override
    public String getName() {
        return "";
    }

    public Course getCourse() {
        return course;
    }
}
