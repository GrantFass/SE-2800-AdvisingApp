/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Curriculum
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A plan for graduation.
 * The Course class is responsible for:
 *     - verifying all courses have been taken to graduate
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Project Name: Advising App
 * Class Name: Curriculum
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A plan for graduation.
 * The Course class is responsible for:
 * - verifying all courses have been taken to graduate
 * Modification Log:
 * - File Created by Hunter Turcin on 2021-03-16
 * - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 * - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 */
public record Curriculum(String major, List<CurriculumItem> items) {
    /**
     * Determine which curriculum items are not yet satisfied.
     *
     * @param courses courses that have been taken already
     * @return unsatisfied items in curriculum order
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public List<CurriculumItem> getUnsatisfiedItems(Collection<Course> courses) {
        // key is item, value is completion status
        var completed = new HashMap<CurriculumItem, Boolean>();

        for (var item : items) {
            completed.put(item, false);
        }

        for (var course : courses) {
            for (var item : items) {
                if (!completed.get(item) && item.satisfiedBy(course)) {
                    completed.put(item, true);
                    break; // don't double-count courses
                }
            }
        }

        // return the unsatisfied items in curriculum order
        var unsatisfied = new ArrayList<CurriculumItem>();

        for (var item : items) {
            if (!completed.get(item)) {
                unsatisfied.add(item);
            }
        }

        return unsatisfied;
    }
}
