/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package msoe.se2800_2ndGroup.models;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

/**
 * An elective that can be satisfied by some courses.
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
    public boolean satisfiedBy(Course course) {
        return predicate.test(course);
    }

    public String getCode() {
        return code;
    }
}
