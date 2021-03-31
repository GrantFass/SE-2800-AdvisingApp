package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.NullPrerequisite;
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
class PrerequisitesLoaderTest {
    static Collection<Course> courses;

    private String code;
    private Course course;

    @Parameters
    static Collection<Object[]> parameters() throws IOException {
        InputStreamReader reader = new InputStreamReader(PrerequisitesLoaderTest.class.getResourceAsStream("prerequisites_varied.csv"));
        PrerequisitesLoader loader = new PrerequisitesLoader(reader);
        courses = loader.load();

        return Arrays.asList(new Object[][] {
                {
                    "TEST1", new Course("TEST1", 0, new NullPrerequisite(), "Test Course")
                }
        });
    }

    @Test
    void testCourseEquals() {
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
