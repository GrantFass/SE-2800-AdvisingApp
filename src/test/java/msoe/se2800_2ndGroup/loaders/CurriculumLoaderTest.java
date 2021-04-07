package msoe.se2800_2ndGroup.loaders;

import static org.junit.Assert.assertEquals;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

@RunWith(Parameterized.class)
public class CurriculumLoaderTest {
    private static final Map<String, Course> TEST_COURSES = Map.of();

    private static Collection<Curriculum> curricula;

    private final String code;
    private final Curriculum curriculum;

    public CurriculumLoaderTest(String code, Curriculum curriculum) {
        this.code = code;
        this.curriculum = curriculum;
    }

    @Parameters
    public static Collection<Object[]> data() throws IOException {
        final var reader = new InputStreamReader(CurriculumLoaderTest.class.getResourceAsStream("curriculum_varied.csv"));
        final var loader = new CurriculumLoader(reader, TEST_COURSES.values());
        curricula = loader.load();

        return Arrays.asList(new Object[][] {});
    }

    @Test
    public void testCurriculumEquals() {
        assertEquals(curriculum, getCurriculum(code));
    }

    private static Curriculum getCurriculum(String code) {
        Curriculum result = null;

        for (final var curriculum : curricula) {
            if (curriculum.getMajor().equals(code)) {
                result = curriculum;
                break;
            }
        }

        return result;
    }
}
