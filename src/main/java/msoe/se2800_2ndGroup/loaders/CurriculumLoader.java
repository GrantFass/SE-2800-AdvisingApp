package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.CurriculumItem;
import msoe.se2800_2ndGroup.models.Elective;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
 * * code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 * * code cleanup from group feedback by turcinh on Monday, 19 April 2021
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
     * <p>
     * Courses must be loaded from {@see PrerequisitesLoader} first.
     *
     * @param reader  The Reader object created with a FileReader containing the path to the file
     *               to be read.
     * @param courses courses to use
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    public CurriculumLoader(Reader reader, Collection<Course> courses) throws IOException {
        this.parser = CSVFormat.DEFAULT.withHeader().parse(reader);
        this.courses = courses;
    }

    /**
     * Create model classes from a curriculum file.
     *
     * @return all curricula
     * @author : Hunter Turcin
     * @since : Mon, 29 Mar 2021
     */
    public Collection<Curriculum> load() {
        // major to curriculum items
        final var map = new HashMap<String, ArrayList<CurriculumItem>>();

        for (final var header : parser.getHeaderNames()) {
            map.put(header, new ArrayList<>());
        }

        for (final var record : parser) {
            parseRecord(record, map);
        }

        final var curricula = new ArrayList<Curriculum>();

        for (final var entry : map.entrySet()) {
            final var major = entry.getKey();
            final var items = entry.getValue();
            final var curriculum = new Curriculum(major, items);

            curricula.add(curriculum);
        }

        return curricula;
    }

    /**
     * Use a record to update curriculum item lists.
     *
     * @param record current record
     * @param map    map of major code to curriculum items
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    private void parseRecord(CSVRecord record, Map<String, ArrayList<CurriculumItem>> map) {
        final var majors = map.keySet();

        for (final var major : majors) {
            final var code = record.get(major);
            final var item = getCurriculumItem(code);

            if (item != null) {
                map.get(major).add(item);
            }
        }
    }

    /**
     * Get a course or elective from a code.
     *
     * @param code code of the course or elective
     * @return the course or elective, or null if not found
     * @author : Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    private CurriculumItem getCurriculumItem(String code) {
        CurriculumItem item = null;

        for (final var course : courses) {
            if (course.code().equals(code)) {
                item = course;
                break;
            }
        }

        if (item == null && !code.isEmpty()) {
            item = new Elective(code);
        }

        return item;
    }
}
