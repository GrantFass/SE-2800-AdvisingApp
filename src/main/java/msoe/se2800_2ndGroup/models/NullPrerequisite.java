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
 * A lack of a prerequisite; this prerequisite is always satisfied.
 */
public class NullPrerequisite implements Prerequisite {
    @Override
    public boolean satisfiedBy(Collection<Course> courses) {
        return true;
    }
}
