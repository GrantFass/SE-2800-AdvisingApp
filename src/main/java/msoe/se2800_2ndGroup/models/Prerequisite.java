/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;

/**
 * A specification of which courses must be taken before another course.
 */
public interface Prerequisite {
    /**
     * Check if the collection of courses satisfies this prerequisite.
     *
     * @param courses courses to use for checking
     * @return whether or not the prerequisite is satisfied
     */
    boolean satisfiedBy(Collection<Course> courses);
}
