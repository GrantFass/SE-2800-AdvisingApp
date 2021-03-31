package msoe.se2800_2ndGroup.loaders;

import msoe.se2800_2ndGroup.models.Course;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

class PrerequisitesLoaderTest {
    static Collection<Course> courses;

    @BeforeClass
    public static void setUp() throws IOException {
        InputStreamReader reader = new InputStreamReader(PrerequisitesLoaderTest.class.getResourceAsStream("prerequisites_varied.csv"));
        PrerequisitesLoader loader = new PrerequisitesLoader(reader);
        courses = loader.load();
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
