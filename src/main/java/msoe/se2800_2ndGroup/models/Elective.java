package msoe.se2800_2ndGroup.models;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Elective
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: An elective that can be satisfied by some courses.
 * The Elective class is responsible for:
 *     - verifying it has been satisfied by an appropriate course
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 *     - code cleanup from group feedback by Hunter Turcin on 2021-04-18
 *     - add elective descriptions by Hunter Turcin on 2021-05-09
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 16 Mar 2021
 */
public class Elective implements CurriculumItem {
    // Information based on https://csse.msoe.us/se/se35/
    private static final Collection<String> sciencePrefixes = Set.of("BI", "CH", "PH");
    private static final Collection<String> mathSciencePrefixes = Set.of("BI", "CH", "EB", "MA", "PH", "SC");
    private static final Collection<String> humanitiesSocialSciencePrefixes = Set.of("HU", "SS");

    private final String code;
    private final Predicate<Course> predicate;

    /**
     * Create a new elective.
     *
     * The elective code determines which course code prefixes are considered
     * to satisfy this elective (e.g. FREE is satisfied by all courses).
     *
     * @param code type code of the elective
     * @throws IllegalArgumentException unknown elective code
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public Elective(String code) throws IllegalArgumentException {
        this.code = code;
        this.predicate = switch (code) {
            case "FREE" -> course -> true;
            case "SCIEL" -> course -> sciencePrefixes.stream().anyMatch(prefix -> course.code().startsWith(prefix)) && course.credits() == 4;
            case "MASCIEL" -> course -> mathSciencePrefixes.stream().anyMatch(prefix -> course.code().startsWith(prefix));
            case "HUSS" -> course -> humanitiesSocialSciencePrefixes.stream().anyMatch(prefix -> course.code().startsWith(prefix));
            case "TECHEL" -> course -> course.code().startsWith("TC");
            case "BUSEL" -> course -> course.code().startsWith("BA");
            default -> throw new IllegalArgumentException("unknown elective code: " + code);
        };
    }

    /**
     * Check another elective has the same code.
     *
     * @param object other elective
     * @return true if equal
     * @author : Hunter Turcin
     * @since : Sun, 4 Apr 2021
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof Elective other && code.equals(other.code);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(code);
    }

    @Override
    public String toString() {
        return String.format("Elective(code=\"%s\")", code);
    }

    /**
     * Check if a course satisfies this elective.
     *
     * @param course course to check
     * @return true if satisfied
     */
    @Override
    public boolean satisfiedBy(Course course) {
        return predicate.test(course);
    }

    public String getCode() {
        return code;
    }

    /**
     * Get the description for this elective.
     *
     * @return the description for this elective
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    public String getDescription() {
        return switch (code) {
            case "FREE" -> "Free Elective";
            case "SCIEL" -> "Science Elective";
            case "MASCIEL" -> "Math/Science Elective";
            case "HUSS" -> "Humanities/Social Science Elective";
            case "TECHEL" -> "Technical Elective";
            case "BUSEL" -> "Business Elective";
            default -> throw new IllegalStateException("bad code: " + code);
        };
    }
}
