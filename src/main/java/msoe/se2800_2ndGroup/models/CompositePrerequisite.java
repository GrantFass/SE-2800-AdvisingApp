package msoe.se2800_2ndGroup.models;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: CompositePrerequisite
 * Description:
 * * Represents Or and And Prerequisites
 * The CompositePrerequisite class is responsible for:
 * * representing composite prerequisites
 * Modification Log:
 * * File Created by turcinh on Monday, 19 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : turcinh
 * @since : Monday, 19 April 2021
 */
public interface CompositePrerequisite extends Prerequisite {
    /**
     * Get the left prerequisite.
     *
     * @return the left prerequisite
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    Prerequisite left();

    /**
     * Get the right prerequisite.
     *
     * @return the right prerequisite
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    Prerequisite right();
}
