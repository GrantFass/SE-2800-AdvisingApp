/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: OrPrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A prerequisite that requires at least one other prerequisite.
 * The OrPrerequisite class is responsible for:
 *     - handling classes with "one of the following" prerequisites
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;

/**
 * Project Name: Advising App
 * Class Name: OrPrerequisite
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A prerequisite that requires at least one other prerequisite.
 * The OrPrerequisite class is responsible for:
 *     - handling classes with "one of the following" prerequisites
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 */
public class OrPrerequisite implements Prerequisite {
    private final Prerequisite left;
    private final Prerequisite right;

    /**
     * Create a new prerequisite that depends on one of two prerequisites.
     *
     * @param left first dependency
     * @param right second dependency
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public OrPrerequisite(Prerequisite left, Prerequisite right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean satisfiedBy(Collection<String> codes) {
        return left.satisfiedBy(codes) || right.satisfiedBy(codes);
    }
}
