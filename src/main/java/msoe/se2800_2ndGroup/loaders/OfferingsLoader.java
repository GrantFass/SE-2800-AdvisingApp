package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Offering;
import msoe.se2800_2ndGroup.models.Term;
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
 * Class Name: OfferingsLoader
 * Description:
 * * Creates model objects from an offerings CSV document.
 * The Curriculum class is responsible for:
 * * loading an offerings CSV
 * Modification Log:
 * * File Created by turcinh on Sunday, 29 March 2021
 * * Modify javadoc for constructor slightly by Grant Fass on Tue, 30 Mar 2021
 * * code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 * * code cleanup from group feedback by turcinh on Monday, 19 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : turcinh
 * @since : Sunday, 29 March 2021
 */
public class OfferingsLoader {
    private static final String COURSE = "Course";

    private final CSVParser parser;
    private final Collection<Course> courses;

    /**
     * Create a new offerings CSV loader.
     * <p>
     * Courses must be loaded first with {@see PrerequisitesLoader}.
     *
     * @param reader  The Reader object created with a FileReader containing the path to the file
     *               to be read.
     * @param courses courses to use
     * @throws IOException could not read data
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    public OfferingsLoader(Reader reader, Collection<Course> courses) throws IOException {
        this.parser = CSVFormat.DEFAULT.withHeader().parse(reader);
        this.courses = courses;
    }

    /**
     * Create model classes from the offerings file.
     *
     * @return all offerings
     * @author : Hunter Turcin
     * @since : Sun, 29 Mar 2021
     */
    public Collection<Offering> load() {
        final var majors = new ArrayList<>(parser.getHeaderNames());
        majors.remove(COURSE);
        final var offerings = new ArrayList<Offering>();

        for (final var record : parser) {
            final var offering = createOffering(majors, record);
            offerings.add(offering);
        }

        return offerings;
    }

    /**
     * Create an offering for the current record.
     *
     * @param majors list of major codes
     * @param record current record in CSV file
     * @return offering for this record
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    private Offering createOffering(final List<String> majors, final CSVRecord record) {
        final var code = record.get(COURSE);
        final var course = getCourse(code);
        final var offering = new Offering(course);

        for (final var major : majors) {
            final var termId = record.get(major);
            final var term = Term.fromId(termId);

            offering.availability().put(major, term);
        }

        return offering;
    }

    /**
     * Get a course from its code.
     *
     * @param code code for the course
     * @return the course, or null if it doesn't exist
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    private Course getCourse(String code) {
        Course course = null;

        for (final var searchCourse : courses) {
            if (searchCourse.code().equals(code)) {
                course = searchCourse;
                break;
            }
        }

        return course;
    }
}
