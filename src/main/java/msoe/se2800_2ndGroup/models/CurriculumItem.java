package msoe.se2800_2ndGroup.models;

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
 *     - code cleanup from group feedback by Hunter Turcin on 2021-04-18
 *     - Changed to extend the comparable interface so that default comparison can be overridden
 * by Grant Fass on Sun, 9 May 2021
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 16 Mar 2021
 */
public interface CurriculumItem extends Comparable<CurriculumItem> {
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
