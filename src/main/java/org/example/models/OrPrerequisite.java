/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package org.example.models;

import java.util.Collection;

/**
 * A prerequisite that requires one of two prerequisites to be satisfied.
 */
public class OrPrerequisite implements Prerequisite {
    private final Prerequisite left;
    private final Prerequisite right;

    /**
     * Create a new prerequisite that depends on one of two prerequisites.
     *
     * @param left first dependency
     * @param right second dependency
     */
    public OrPrerequisite(Prerequisite left, Prerequisite right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean satisfiedBy(Collection<Course> courses) {
        return left.satisfiedBy(courses) || right.satisfiedBy(courses);
    }
}
