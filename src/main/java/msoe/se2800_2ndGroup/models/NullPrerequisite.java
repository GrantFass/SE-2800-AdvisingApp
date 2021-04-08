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
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;
import java.util.Objects;

/**
 * Project Name: Advising App
 * Class Name: NullPrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A lack of a prerequisite.
 * The NullPrerequisite class is responsible for:
 *     - handling courses that have no prerequisite
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - equals added by Hunter Turcin on 2021-03-31
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 */
public class NullPrerequisite implements Prerequisite {
    @Override
    public boolean equals(Object object) {
        return object instanceof NullPrerequisite;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "NullPrerequisite()";
    }

    @Override
    public boolean satisfiedBy(Collection<String> codes) {
        return true;
    }
}
