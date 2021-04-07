package msoe.se2800_2ndGroup.loaders;

import static org.junit.Assert.assertEquals;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Curriculum;
import msoe.se2800_2ndGroup.models.Elective;
import msoe.se2800_2ndGroup.models.NullPrerequisite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RunWith(Parameterized.class)
public class CurriculumLoaderTest {
    private static final Map<String, Course> TEST_COURSES;

    static {
        // Too many arguments for Map.of()
        TEST_COURSES = new HashMap<>();
        TEST_COURSES.put("TEST1", new Course("TEST1", 1, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST2", new Course("TEST2", 2, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST3", new Course("TEST3", 3, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST4", new Course("TEST4", 1, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST5", new Course("TEST5", 2, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST6", new Course("TEST6", 3, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST7", new Course("TEST7", 1, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST8", new Course("TEST8", 2, new NullPrerequisite(), ""));
        TEST_COURSES.put("TEST9", new Course("TEST9", 2, new NullPrerequisite(), ""));
        TEST_COURSES.put("TESTA", new Course("TESTA", 2, new NullPrerequisite(), ""));
        TEST_COURSES.put("TESTB", new Course("TESTB", 2, new NullPrerequisite(), ""));
        TEST_COURSES.put("TESTC", new Course("TESTC", 3, new NullPrerequisite(), ""));
    }

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

        return Arrays.asList(new Object[][] {
                {
                        "ONE",
                        new Curriculum("ONE", List.of(
                                TEST_COURSES.get("TEST1"),
                                TEST_COURSES.get("TEST2"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST4"),
                                new Elective("FREE"),
                                new Elective("FREE"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST7"),
                                TEST_COURSES.get("TESTA")
                        )),
                },
                {
                        "TWO",
                        new Curriculum("TWO", List.of(
                                TEST_COURSES.get("TEST1"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST3"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST5"),
                                new Elective("FREE"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST8")
                        )),
                },
                {
                        "THREE",
                        new Curriculum("THREE", List.of(
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST2"),
                                TEST_COURSES.get("TEST3"),
                                new Elective("FREE"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST6"),
                                new Elective("FREE"),
                                TEST_COURSES.get("TEST9"),
                                TEST_COURSES.get("TESTB"),
                                TEST_COURSES.get("TESTC")
                        )),
                },
        });
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
