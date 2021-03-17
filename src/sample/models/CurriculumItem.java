/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package sample.models;

/**
 * A course or elective a person must take for a curriculum.
 */
public interface CurriculumItem {
    /**
     * Determine if a singular course satisfies this item in the curriculum.
     *
     * @param course course to check
     * @return whether or not it is satisfied
     */
    boolean satisfiedBy(Course course);
}
