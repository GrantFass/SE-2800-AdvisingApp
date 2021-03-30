package msoe.se2800_2ndGroup.loaders;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import msoe.se2800_2ndGroup.models.AndPrerequisite;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.NullPrerequisite;
import msoe.se2800_2ndGroup.models.OrPrerequisite;
import msoe.se2800_2ndGroup.models.Prerequisite;
import msoe.se2800_2ndGroup.models.SinglePrerequisite;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

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
     * @param reader The Reader object created with a FileReader containing the path to the file to be read.
     * @author : Hunter Turcin
     * @since : Sun, 21 Mar 2021
     */
    public PrerequisitesLoader(Reader reader) throws IOException {
        parser = CSVFormat.DEFAULT.parse(reader);
    }

    /**
     * Load courses from the CSV document.
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

    private boolean checkHeaders() {
        final var headers = parser.getHeaderNames();
        final var expected = List.of(COURSE, CREDITS, PREREQS, DESCRIPTION);

        return headers.size() == expected.size() && headers.containsAll(expected);
    }

    private Course loadCourse(CSVRecord record) {
        final var code = record.get(COURSE);
        final var credits = Integer.parseInt(CREDITS);
        final var prerequisites = loadPrerequisite(record.get(PREREQS));
        final var description = record.get(DESCRIPTION);

        return new Course(code, credits, prerequisites, description);
    }

    private Prerequisite loadPrerequisite(String line) {
        // AND has precedence over OR.
        final var andCodes = line.split(" ");

        Prerequisite outer = new NullPrerequisite();

        for (final var andCode : andCodes) {
            final var orCodes = line.split("\\|"); // match only pipe characters

            Prerequisite inner = new NullPrerequisite();

            for (final var orCode : orCodes) {
                inner = new OrPrerequisite(inner, new SinglePrerequisite(orCode));
            }

            outer = new AndPrerequisite(outer, inner);
        }

        return outer;
    }
}
