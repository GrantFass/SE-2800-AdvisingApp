package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.models.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: magana041group2
 * Class Name: ImportTranscriptTest
 * Description:
 * * <class description here>
 * The ImportTranscriptTest class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by toohillt on Saturday, 10 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 10 April 2021
 */

public class ImportTranscriptTest {
    private ArrayList<Course> course = new ArrayList<>();

    private void testedArrayList(){
        Course course1 = new Course("CH200");
        course.add(course1);
        Course course2 = new Course("SE2030");
        course.add(course2);
        Course course3 = new Course("GS1001");
        course.add(course3);
        Course course4 = new Course("MA2310");
        course.add(course4);
        Course course5 = new Course("OR402");
        course.add(course5);
        Course course6 = new Course("CH200");
        course.add(course6);
        Course course7 = new Course("SE2030");
        course.add(course7);
        Course course8 = new Course("GS1001");
        course.add(course8);
        Course course9 = new Course("MA2310");
        course.add(course9);
        Course course10 = new Course("OR402");
        course.add(course10);
        Course course11 = new Course("CH200");
        course.add(course11);
        Course course12 = new Course("SE2030");
        course.add(course12);
        Course course13 = new Course("GS1001");
        course.add(course13);
        Course course14 = new Course("MA2310");
        course.add(course14);
        Course course15 = new Course("OR402");
        course.add(course15);
        Course course16 = new Course("CH200");
        course.add(course16);
        Course course17 = new Course("SE2030");
        course.add(course17);
        Course course18 = new Course("GS1001");
        course.add(course18);
    }

    /**
     * TODO
     *
     * @author : First Last
     * @since : Tue, 20 Apr 2021
     */
    @BeforeEach
    void setUp() {
        //probably import sample files
    }

//    /**
//     * TODO
//     *
//     * @author : First last
//     * @since : Tue, 20 Apr 2021
//     */
//    @Test
//    void checkLineForIgnoredWordsAndFailedClassesAndWithdrawnClasses() throws NoSuchMethodException {
//        //TODO: changed method name, should be updated once merged
//        Method checkString = ImportTranscript.class.getDeclaredMethod("checkLineForIgnoredWordsAndFailedClassesAndWithdrawnClasses", String.class);
//        checkString.setAccessible(true);
//    };
//
//    /**
//     * TODO
//     *
//     * @author : First last
//     * @since : Tue, 20 Apr 2021
//     */
//    @Test
//    void checkStringForCourseCode() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        Method checkString = ImportTranscript.class.getDeclaredMethod("checkStringForCourseCode", String.class);
//        checkString.setAccessible(true);
//        //region Contains course code
//        Assertions.assertEquals("CS3860", checkString.invoke(importer, "CS3860"));
//        Assertions.assertEquals("CS-3860", checkString.invoke(importer, "CS-3860"));
//        //endregion
//
//        //region Does not contain course code
//        Assertions.assertNull(checkString.invoke(importer, "CS.3860"));
//        Assertions.assertNull(checkString.invoke(importer, "CS--3860"));
//        Assertions.assertNull(checkString.invoke(importer, "CS"));
//        //endregion
//
//    }

    /**
     * TODO
     *
     * @author : First last
     * @since : Tue, 20 Apr 2021
     */
    @Test
    void validateReadInFile(){
        // TODO: figure out how to test overloaded methods :(
        String path = "Documents/transcriptTeresaToohill.pdf";
        File file = new File(path);
        String absolutePath = file.getAbsolutePath();

        assertTrue(absolutePath.endsWith("Documents/transcriptTeresaToohill.pdf"));
        testedArrayList();
        ImportTranscript importTranscript;
//        ArrayList<Course> courseList = importTranscript.readInFile(file);
//        assertEquals("Correct List", course, courseList);
    }
}