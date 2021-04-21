package msoe.se2800_2ndGroup.models;

import java.util.HashMap;
import java.util.Map;

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
 *     - code cleanup from group feedback by Hunter Turcin on 2021-04-18
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 16 Mar 2021
 */
public record Offering(Course course, Map<String, Term> availability) {
    /**
     * Create a new offering.
     *
     * @param course course this offering is for
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public Offering(Course course) {
        this(course, new HashMap<>());
    }
}
