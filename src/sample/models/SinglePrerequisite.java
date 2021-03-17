/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package sample.models;

import java.util.Collection;

/**
 * A prerequisite that depends on a single course.
 */
public class SinglePrerequisite implements Prerequisite {
    private final Course course;

    /**
     * Create a prerequisite that depends only on one course.
     *
     * @param course the course to depend on
     */
    public SinglePrerequisite(Course course) {
        this.course = course;
    }

    @Override
    public boolean satisfiedBy(Collection<Course> courses) {
        return courses.contains(course);
    }
}
