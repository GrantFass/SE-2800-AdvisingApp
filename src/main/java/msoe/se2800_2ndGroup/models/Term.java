/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Term
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A quarter of the year when courses take place.
 * The Term class is responsible for:
 *     - describing when a course happens
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Objects;

/**
 * Project Name: Advising App
 * Class Name: Term
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A quarter of the year when courses take place.
 * The Term class is responsible for:
 *     - describing when a course happens
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 */
public class Term {
    /**
     * The fall term.
     */
    public static final Term FALL = new Term(1, "Fall");

    /**
     * The winter term.
     */
    public static final Term WINTER = new Term(2, "Winter");

    /**
     * The spring term.
     */
    public static final Term SPRING = new Term(3, "Spring");

    private final int id;
    private final String season;

    private Term(int id, String season) {
        this.id = id;
        this.season = season;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Term) {
            final var other = (Term) object;
            return id == other.id && season.equals(other.season);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, season);
    }

    @Override
    public String toString() {
        return String.format("Term(id=%d, season=\"%s\")", id, season);
    }

    /**
     * Get a term from its id.
     *
     * @param id the id of the term
     * @return the term
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public static Term fromId(int id) {
        return switch (id) {
            case 1 -> FALL;
            case 2 -> WINTER;
            case 3 -> SPRING;
            default -> throw new IllegalArgumentException("unknown term id: " + id);
        };
    }

    public int getId() {
        return id;
    }

    public String getSeason() {
        return season;
    }
}
