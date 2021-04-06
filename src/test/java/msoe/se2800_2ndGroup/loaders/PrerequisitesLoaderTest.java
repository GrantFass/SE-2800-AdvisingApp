package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.*;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class PrerequisitesLoaderTest {
    private final String code;
    private final Course course;

    private static Collection<Course> courses;

    public PrerequisitesLoaderTest(String code, Course course) {
        this.code = code;
        this.course = course;
    }

    @Parameters
    public static Collection<Object[]> data() throws IOException {
        InputStreamReader reader = new InputStreamReader(PrerequisitesLoaderTest.class.getResourceAsStream("prerequisites_varied.csv"));
        PrerequisitesLoader loader = new PrerequisitesLoader(reader);
        courses = loader.load();

        return Arrays.asList(new Object[][] {
                { "TEST1", new Course("TEST1", 0, new NullPrerequisite(), "Test Course") },
                { "TEST2", new Course("TEST2", 0, new NullPrerequisite(), "Test Course") },
                { "TEST3", new Course("TEST3", 0, new NullPrerequisite(), "Test Course") },
                { "TEST4", new Course("TEST4", 0, new NullPrerequisite(), "Test Course") },
                { "NULL", new Course("NULL", 1, new NullPrerequisite(), "Testing NullPrerequisite") },
                { "SINGLE", new Course("SINGLE", 2, new SinglePrerequisite("NULL"), "Testing SinglePrerequisite") },
                { "AND", new Course("AND", 3, new AndPrerequisite(new SinglePrerequisite("NULL"), new SinglePrerequisite("SINGLE")), "Testing AndPrerequisite") },
                { "OR", new Course("OR", 4, new OrPrerequisite(new SinglePrerequisite("NULL"), new SinglePrerequisite("SINGLE")), "Testing OrPrerequisite") },
                { "NESTED1", new Course("NESTED1", 5, new AndPrerequisite(new OrPrerequisite(new SinglePrerequisite("TEST1"), new SinglePrerequisite("TEST2")), new SinglePrerequisite("TEST3")), "Testing or-and-single nesting") },
                { "NESTED2", new Course("NESTED2", 5, new AndPrerequisite(new SinglePrerequisite("TEST1"), new OrPrerequisite(new SinglePrerequisite("TEST2"), new SinglePrerequisite("TEST3"))), "Testing single-and-or nesting") },
                { "NESTED3", new Course("NESTED3", 5, new AndPrerequisite(new OrPrerequisite(new SinglePrerequisite("TEST1"), new SinglePrerequisite("TEST2")), new OrPrerequisite(new SinglePrerequisite("TEST3"), new SinglePrerequisite("TEST4"))), "Testing or-and-or nesting") },
                { "NESTED4", new Course("NESTED4", 5, new AndPrerequisite(new AndPrerequisite(new SinglePrerequisite("TEST1"), new SinglePrerequisite("TEST2")), new SinglePrerequisite("TEST3")), "Testing and-and-single nesting") },
                { "NESTED5", new Course("NESTED5", 5, new AndPrerequisite(new AndPrerequisite(new SinglePrerequisite("TEST1"), new OrPrerequisite(new SinglePrerequisite("TEST2"), new SinglePrerequisite("TEST3"))), new SinglePrerequisite("TEST4")), "Testing single-and-or-and-single nesting") },
                { "NESTED6", new Course("NESTED6", 5, new OrPrerequisite(new OrPrerequisite(new SinglePrerequisite("TEST1"), new SinglePrerequisite("TEST2")), new SinglePrerequisite("TEST3")), "Testing or-or-single nesting") },
                { "NESTED7", new Course("NESTED7", 5, new OrPrerequisite(new OrPrerequisite(new OrPrerequisite(new SinglePrerequisite("TEST1"), new SinglePrerequisite("TEST2")), new SinglePrerequisite("TEST3")), new SinglePrerequisite("TEST4")), "Testing or-or-or nesting") },
                { "NESTED8", new Course("NESTED8", 5, new AndPrerequisite(new AndPrerequisite(new AndPrerequisite(new SinglePrerequisite("TEST1"), new SinglePrerequisite("TEST2")), new SinglePrerequisite("TEST3")), new SinglePrerequisite("TEST4")), "Testing and-and-and nesting") },
        });
    }

    @Test
    public void testCourseEquals() {
        assertEquals(course, getCourse(code));
    }

    private static Course getCourse(String code) {
        Course result = null;

        for (Course course : courses) {
            if (course.getCode().equals(code)) {
                result = course;
                break;
            }
        }

        return result;
    }
}
