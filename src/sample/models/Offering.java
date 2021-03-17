/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package sample.models;

import java.util.HashMap;
import java.util.Map;

/**
 * A description of when a course may be taken by a given major.
 */
public class Offering {
    private final Course course;
    private final Map<String, Term> majorAvailability = new HashMap<>();

    /**
     * Create a new offering.
     *
     * @param course course this offering is for
     */
    public Offering(Course course) {
        this.course = course;
    }

    /**
     * Put an availability.
     *
     * @param major major this is for
     * @param term term this is for
     */
    public void putAvailability(String major, Term term) {
        majorAvailability.put(major, term);
    }

    /**
     * Get an availability.
     *
     * @param major major this is for
     * @return term this is for
     */
    public Term getAvailability(String major) {
        return majorAvailability.get(major);
    }

    public Course getCourse() {
        return course;
    }
}
