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
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
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
 * - associating a course code with a set of prerequisites
 * Modification Log:
 * - File Created by Hunter Turcin on 2021-03-16
 * - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 * - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 */
public record Course(String code, int credits, Prerequisite prerequisite, String description) implements CurriculumItem {
    @Override
    public boolean satisfiedBy(Course course) {
        return code.equals(course.code);
    }
}
