/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Course
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A course that may or may not depend on other courses.
 * The Course class is responsible for:
 *     - associating a course code with a set of prerequisites
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

/**
 * Project Name: Advising App
 * Class Name: Course
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A course that may or may not depend on other courses.
 * The Course class is responsible for:
 *     - associating a course code with a set of prerequisites
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 */
public class Course implements CurriculumItem {
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
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public Course(String code, int credits, Prerequisite prerequisite, String description) {
        this.code = code;
        this.credits = credits;
        this.prerequisite = prerequisite;
        this.description = description;
    }

    @Override
    public boolean satisfiedBy(Course course) {
        return code.equals(course.getCode());
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
