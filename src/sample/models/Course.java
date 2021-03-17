/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package sample.models;

/**
 * A course that may or may not depend on other courses.
 */
public class Course {
    private final String code;
    private final int credits;
    private final Prerequisite prerequisite;
    private final String description;

    /**
     * Create a new course.
     *
     * @param code course registration code
     * @param credits credit-hours per term
     * @param prerequisite any prerequisites
     * @param description human-readable description of course
     */
    public Course(String code, int credits, Prerequisite prerequisite, String description) {
        this.code = code;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public int getCredits() {
        return credits;
    }

    public Prerequisite getPrerequisite() {
        return prerequisite;
    }

    public String getDescription() {
        return description;
    }
}
