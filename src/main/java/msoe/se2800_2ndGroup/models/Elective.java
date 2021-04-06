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
 * Copyright (C): 2021
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
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
     * @param code type code of the elective
     * @author Hunter Turcin
     * @since Sun, 16 Mar 2021
     */
    public Elective(String code) {
        this.code = code;
        this.predicate = switch (code) {
            case "FREE" -> course -> true;
            case "SCIEL" -> course -> sciencePrefixes.stream().anyMatch(prefix -> course.getCode().startsWith(prefix)) && course.getCredits() == 4;
            case "MASCIEL" -> course -> mathSciencePrefixes.stream().anyMatch(prefix -> course.getCode().startsWith(prefix));
            case "HUSS" -> course -> humanitiesSocialSciencePrefixes.stream().anyMatch(prefix -> course.getCode().startsWith(prefix));
            case "TECHEL" -> course -> course.getCode().startsWith("TC");
            default -> throw new IllegalArgumentException("unknown elective code: " + code);
        };
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Elective) {
            final var other = (Elective) object;
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
        return String.format("Elective(code=\"%s\")", code);
    }

    @Override
    public boolean satisfiedBy(Course course) {
        return predicate.test(course);
    }

    public String getCode() {
        return code;
    }
}
