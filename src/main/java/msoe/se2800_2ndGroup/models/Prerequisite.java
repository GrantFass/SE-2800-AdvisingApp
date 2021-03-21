/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Prerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A specification of which courses must be taken before another course.
 * The Prerequisite interface is responsible for:
 *     - verifying a course's dependencies have been met
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;

/**
 * Project Name: Advising App
 * Class Name: Prerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A specification of which courses must be taken before another course.
 * The Prerequisite interface is responsible for:
 *     - verifying a course's dependencies have been met
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 */
public interface Prerequisite {
    /**
     * Check if the collection of courses satisfies this prerequisite.
     *
     * @param courses courses to use for checking
     * @return whether or not the prerequisite is satisfied
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    boolean satisfiedBy(Collection<Course> courses);
}
