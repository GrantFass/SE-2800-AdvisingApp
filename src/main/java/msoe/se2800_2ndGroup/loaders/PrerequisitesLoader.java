package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: PrerequisitesLoader
 * Description:
 * * Creates model objects from a prerequisites CSV document.
 * The PrerequisitesLoader class is responsible for:
 * * loading a prerequisites CSV
 * Modification Log:
 * * File Created by turcinh on Sunday, 21 March 2021
 * * Modify javadoc for constructor slightly by Grant Fass on Tue, 30 Mar 2021
 * * code cleanup from group feedback by turcinh on Monday, 19 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : turcinh
 * @since : Sunday, 21 March 2021
 */
public class PrerequisitesLoader {
    private static final String COURSE = "Course";
    private static final String CREDITS = "Credits";
    private static final String PREREQS = "Prereqs";
    private static final String DESCRIPTION = "Description";

    private final CSVParser parser;

    /**
     * Create a new prerequisites CSV document loader.
     *
     * @param reader The Reader object created with a FileReader containing the path to the file
     *               to be read.
     * @throws IOException could not read the CSV file
     * @author : Hunter Turcin
     * @since : Sun, 21 Mar 2021
     */
    public PrerequisitesLoader(Reader reader) throws IOException {
        parser = CSVFormat.DEFAULT.withHeader().parse(reader);
    }

    /**
     * Load courses from the CSV document.
     * <p>
     * Each record corresponds to one course object.
     *
     * @return loaded courses
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    public Collection<Course> load() {
        // TODO: collect errors in one place to report to user
        if (!checkHeaders()) {
            return List.of();
        }

        final var courses = new ArrayList<Course>();

        for (final var record : parser) {
            final var course = loadCourse(record);
            courses.add(course);
        }

        return courses;
    }

    /**
     * Check if only expected headers are present.
     *
     * @return true if the file is well-formed, false if not
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    private boolean checkHeaders() {
        final var headers = parser.getHeaderNames();
        final var expected = List.of(COURSE, CREDITS, PREREQS, DESCRIPTION);

        return headers.size() == expected.size() && headers.containsAll(expected);
    }

    /**
     * Create a course object from a CSV record.
     *
     * @param record current CSV record
     * @return new course object
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    private Course loadCourse(CSVRecord record) {
        final var code = record.get(COURSE);
        final var credits = Integer.parseInt(record.get(CREDITS));
        final var prerequisites = loadPrerequisite(record.get(PREREQS));
        final var description = record.get(DESCRIPTION);

        return new Course(code, credits, prerequisites, description);
    }

    /**
     * Convert a serialized prerequisite to an object.
     * <p>
     * Serialized prerequisites are a list of codes separated by pipe characters (OR)
     * or spaces (AND).
     * <p>
     * Deserialization is done in such a way that the resulting prerequisite is easy
     * to graph. For example, constructs like And(Null, Simple) are never created.
     *
     * @param line serialized prerequisite
     * @return the new prerequisite
     */
    private Prerequisite loadPrerequisite(String line) {
        // AND has precedence over OR.
        final var andCodes = line.split("\\s");

        Prerequisite outer = new NullPrerequisite();

        for (final var andCode : andCodes) {
            final var orCodes = andCode.split("\\|"); // match only pipe characters

            if (orCodes.length > 1) {
                final var inner = getInnerPrerequisite(orCodes);

                outer = outer instanceof NullPrerequisite ? inner
                                                          : new AndPrerequisite(outer, inner);
            } else if (!orCodes[0].isEmpty()) {
                outer = outer instanceof NullPrerequisite ? new SinglePrerequisite(orCodes[0])
                                                          : new AndPrerequisite(outer,
                                                                                new SinglePrerequisite(
                                                                                        orCodes[0]));
            }
        }

        return outer;
    }

    /**
     * Get the "inner" prerequisite for this set of OR-delimited codes.
     *
     * @param orCodes OR-delimited codes
     * @return the inner prerequisite
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    private Prerequisite getInnerPrerequisite(String[] orCodes) {
        var inner = new OrPrerequisite(new SinglePrerequisite(orCodes[0]),
                                       new SinglePrerequisite(orCodes[1]));

        for (var i = 2; i < orCodes.length; i++) {
            final var orCode = orCodes[i];
            inner = new OrPrerequisite(inner, new SinglePrerequisite(orCode));
        }

        return inner;
    }
}
