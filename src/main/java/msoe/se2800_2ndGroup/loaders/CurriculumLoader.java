package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.CurriculumItem;
import msoe.se2800_2ndGroup.models.Elective;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileReader;
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
 * Class Name: CurriculumLoader
 * Description:
 * * Creates model objects from a curriculum CSV document.
 * The Curriculum class is responsible for:
 * * loading a curriculum CSV
 * Modification Log:
 * * File Created by turcinh on Sunday, 29 March 2021
 * * Modify javadoc for constructor slightly by Grant Fass on Tue, 30 Mar 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : turcinh
 * @since : Sunday, 29 March 2021
 */
public class CurriculumLoader {
    private final CSVParser parser;
    private final Collection<Course> courses;

    /**
     * Create a new curriculum CSV loader.
     *
     * @param reader The Reader object created with a FileReader containing the path to the file to be read.
     * @param courses courses to use
     * @author : First last
     * @since : Mon, 29 Mar 2021
     */
    public CurriculumLoader(Reader reader, Collection<Course> courses) throws IOException {
        this.parser = CSVFormat.DEFAULT.parse(reader);
        this.courses = courses;
    }

    public Collection<Curriculum> load() {
        // major to curriculum items
        final var map = new HashMap<String, ArrayList<CurriculumItem>>();

        for (final var header : parser.getHeaderNames()) {
            map.put(header, new ArrayList<>());
        }

        final var majors = map.keySet();

        for (final var record : parser) {
            for (final var major : majors) {
                final var code = record.get(major);
                CurriculumItem item = null;

                for (final var course : courses) {
                    if (course.getCode().equals(code)) {
                        item = course;
                        break;
                    }
                }

                if (item == null) {
                    item = new Elective(code);
                }

                map.get(major).add(item);
            }
        }

        final var curriculums = new ArrayList<Curriculum>();

        for (final var entry : map.entrySet()) {
            final var major = entry.getKey();
            final var items = entry.getValue();
            final var curriculum = new Curriculum(major, items);

            curriculums.add(curriculum);
        }

        return curriculums;
    }
}
