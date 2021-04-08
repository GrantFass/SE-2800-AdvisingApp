/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Offering
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: When a course may be taken, per major.
 * The Offering class is responsible for:
 *     - tracking when which major can take a course
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Project Name: Advising App
 * Class Name: Offering
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: When a course may be taken, per major.
 * The Offering class is responsible for:
 *     - tracking when which major can take a course
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 */
public class Offering {
    private final Course course;
    private final Map<String, Term> majorAvailability;

    /**
     * Create a new offering.
     *
     * @param course course this offering is for
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public Offering(Course course) {
        this.course = course;
        majorAvailability = new HashMap<>();
    }

    /**
     * Create a new offering in an easier way.
     *
     * @param course course this offering is for
     * @param majorAvailability map of availability
     * @author Hunter Turcin
     * @since Wed, 7 Apr 2021
     */
    public Offering(Course course, Map<String, Term> majorAvailability) {
        this.course = course;
        this.majorAvailability = majorAvailability;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof Offering other
               && course.equals(other.course)
               && majorAvailability.equals(other.majorAvailability);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, majorAvailability);
    }

    @Override
    public String toString() {
        return String.format("Offering(course=%s, majorAvailability=%s)", course, majorAvailability);
    }

    /**
     * Put an availability.
     *
     * @param major major this is for
     * @param term term this is for
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public void putAvailability(String major, Term term) {
        majorAvailability.put(major, term);
    }

    /**
     * Get an availability.
     *
     * @param major major this is for
     * @return term this is for
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public Term getAvailability(String major) {
        return majorAvailability.get(major);
    }

    public Course getCourse() {
        return course;
    }
}
