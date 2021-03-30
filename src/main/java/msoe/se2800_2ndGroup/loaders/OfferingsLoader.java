package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Offering;
import msoe.se2800_2ndGroup.models.Term;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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
     *
     * @param reader The Reader object created with a FileReader containing the path to the file to be read.
     * @param courses courses to use
     * @throws IOException could not read data
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    public OfferingsLoader(Reader reader, Collection<Course> courses) throws IOException {
        this.parser = CSVFormat.DEFAULT.parse(reader);
        this.courses = courses;
    }

    public Collection<Offering> load() {
        final var majors = parser.getHeaderNames();
        majors.remove(COURSE);
        final var offerings = new ArrayList<Offering>();

        for (final var record : parser) {
            final var code = record.get(COURSE);
            Course course = null;

            for (final var searchCourse : courses) {
                if (searchCourse.getCode().equals(code)) {
                    course = searchCourse;
                    break;
                }
            }

            final var offering = new Offering(course);

            for (final var major : majors) {
                final var termNumber = Integer.parseInt(record.get(major));
                final var term = Term.fromId(termNumber);

                offering.putAvailability(major, term);
            }

            offerings.add(offering);
        }

        return offerings;
    }
}
