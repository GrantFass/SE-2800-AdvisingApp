/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: SinglePrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A single-course prerequisite.
 * The SinglePrerequisite class is responsible for:
 *     - handling cases where only one course is needed for another
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;

/**
 * Project Name: Advising App
 * Class Name: SinglePrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A single-course prerequisite.
 * The SinglePrerequisite class is responsible for:
 *     - handling cases where only one course is needed for another
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 */
public class SinglePrerequisite implements Prerequisite {
    private final Course course;

    /**
     * Create a prerequisite that depends only on one course.
     *
     * @param course the course to depend on
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public SinglePrerequisite(Course course) {
        this.course = course;
    }

    @Override
    public boolean satisfiedBy(Collection<Course> courses) {
        return courses.contains(course);
    }
}
