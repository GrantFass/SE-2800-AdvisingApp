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
 *     - equals added by Hunter Turcin on 2021-03-31
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;
import java.util.Objects;

/**
 * Project Name: Advising App
 * Class Name: SinglePrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A single-course prerequisite.
 * The SinglePrerequisite class is responsible for:
 * - handling cases where only one course is needed for another
 * Modification Log:
 * - File Created by Hunter Turcin on 2021-03-16
 * - equals added by Hunter Turcin on 2021-03-31
 * - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 * - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 */
public record SinglePrerequisite(String code) implements Prerequisite {
    @Override
    public boolean satisfiedBy(Collection<String> codes) {
        return codes.contains(code);
    }
}
