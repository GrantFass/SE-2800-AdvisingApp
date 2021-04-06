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
 *     - handling cases where only one course is needed for another
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - equals added by Hunter Turcin on 2021-03-31
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 */
public class SinglePrerequisite implements Prerequisite {
    private final String code;

    /**
     * Create a prerequisite that depends only on one course.
     *
     * @param code the course code to depend on
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public SinglePrerequisite(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof SinglePrerequisite) {
            SinglePrerequisite other = (SinglePrerequisite) object;
            return code.equals(other.code);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return String.format("SinglePrerequisite(code=\"%s\")", code);
    }

    @Override
    public boolean satisfiedBy(Collection<String> codes) {
        return codes.contains(code);
    }
}
