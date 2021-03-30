/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: CurriculumItem
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A course or elective a student must take.
 * The CurriculumItem interface is responsible for:
 *     - verifying the course or elective has been taken
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

/**
 * Project Name: Advising App
 * Class Name: CurriculumItem
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A course or elective a student must take.
 * The CurriculumItem interface is responsible for:
 *     - verifying the course or elective has been taken
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 */
public interface CurriculumItem {
    /**
     * Determine if a singular course satisfies this item in the curriculum.
     *
     * @param course course to check
     * @return whether or not it is satisfied
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    boolean satisfiedBy(Course course);
}
