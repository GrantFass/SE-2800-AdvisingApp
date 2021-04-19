package msoe.se2800_2ndGroup.models;

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
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 *     - code cleanup from group feedback by Hunter Turcin on 2021-04-19
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 16 Mar 2021
 */
public record Term(String id, String season) {
    /**
     * The fall term.
     */
    public static final Term FALL = new Term("1", "Fall");

    /**
     * The winter term.
     */
    public static final Term WINTER = new Term("2", "Winter");

    /**
     * The spring term.
     */
    public static final Term SPRING = new Term("3", "Spring");

    /**
     * Never available.
     */
    public static final Term NEVER = new Term("", "Never");

    /**
     * Get a term from its id.
     *
     * The instances from this method should be treated like singletons.
     *
     * @param id the id of the term
     * @return the term
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public static Term fromId(String id) {
        return switch (id) {
            case "1" -> FALL;
            case "2" -> WINTER;
            case "3" -> SPRING;
            case "" -> NEVER;
            default -> throw new IllegalArgumentException("unknown term id: " + id);
        };
    }
}
