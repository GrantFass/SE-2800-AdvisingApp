package msoe.se2800_2ndGroup.models;

import java.util.Collection;

/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: NullPrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A lack of a prerequisite.
 * The Elective class is responsible for:
 *     - handling courses that have no prerequisite
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - equals added by Hunter Turcin on 2021-03-31
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup from group feedback by Hunter Turcin on 2021-04-18
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 16 Mar 2021
 */
public record NullPrerequisite() implements Prerequisite {
    /**
     * Check if this prerequisite is satisfied, which is always true.
     *
     * @param codes course codes to use for checking
     * @return true
     * @author : Hunter Turcin
     * @since : Tue, 16 Mar 2021
     */
    @Override
    public boolean satisfiedBy(Collection<String> codes) {
        return true;
    }
}
