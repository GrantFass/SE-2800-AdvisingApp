package msoe.se2800_2ndGroup.loaders;

import static org.junit.Assert.assertEquals;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.NullPrerequisite;
import msoe.se2800_2ndGroup.models.Offering;
import msoe.se2800_2ndGroup.models.Term;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@RunWith(Parameterized.class)
public class OfferingsLoaderTest {
    // Fake course data solely to give something to the loader.
    private static final Map<String, Course> TEST_COURSES = Map.of(
            "SAME", new Course("SAME", 0, new NullPrerequisite(), ""),
            "DIFFERENT", new Course("DIFFERENT", 0, new NullPrerequisite(), ""),
            "BLANK1", new Course("BLANK1", 0, new NullPrerequisite(), ""),
            "BLANK2", new Course("BLANK2", 0, new NullPrerequisite(), ""),
            "BLANK3", new Course("BLANK3", 0, new NullPrerequisite(), ""),
            "2BLANK1", new Course("2BLANK1", 0, new NullPrerequisite(), ""),
            "2BLANK2", new Course("2BLANK2", 0, new NullPrerequisite(), ""),
            "2BLANK3", new Course("2BLANK3", 0, new NullPrerequisite(), ""),
            "3BLANK", new Course("3BLANK", 0, new NullPrerequisite(), "")
    );

    private static Collection<Offering> offerings;

    private final String code;
    private final Offering offering;

    public OfferingsLoaderTest(String code, Offering offering) {
        this.code = code;
        this.offering = offering;
    }

    @Parameters
    public static Collection<Object[]> data() throws IOException {
        final var reader = new InputStreamReader(OfferingsLoaderTest.class.getResourceAsStream("offerings_varied.csv"));
        final var loader = new OfferingsLoader(reader, TEST_COURSES.values());
        offerings = loader.load();

        return Arrays.asList(new Object[][] {
                { "SAME", new Offering(TEST_COURSES.get("SAME"), Map.of("MAJOR", Term.FALL, "MINOR", Term.FALL, "HAS SPACE", Term.FALL)) },
                { "DIFFERENT", new Offering(TEST_COURSES.get("DIFFERENT"), Map.of("MAJOR", Term.FALL, "MINOR", Term.WINTER, "HAS SPACE", Term.SPRING)) },
                { "BLANK1", new Offering(TEST_COURSES.get("BLANK1"), Map.of("MAJOR", Term.NEVER, "MINOR", Term.WINTER, "HAS SPACE", Term.SPRING)) },
                { "BLANK2", new Offering(TEST_COURSES.get("BLANK2"), Map.of("MAJOR", Term.FALL, "MINOR", Term.NEVER, "HAS SPACE", Term.SPRING)) },
                { "BLANK3", new Offering(TEST_COURSES.get("BLANK3"), Map.of("MAJOR", Term.FALL, "MINOR", Term.WINTER, "HAS SPACE", Term.NEVER)) },
                { "2BLANK1", new Offering(TEST_COURSES.get("2BLANK1"), Map.of("MAJOR", Term.FALL, "MINOR", Term.NEVER, "HAS SPACE", Term.NEVER)) },
                { "2BLANK2", new Offering(TEST_COURSES.get("2BLANK2"), Map.of("MAJOR", Term.NEVER, "MINOR", Term.WINTER, "HAS SPACE", Term.NEVER)) },
                { "2BLANK3", new Offering(TEST_COURSES.get("2BLANK3"), Map.of("MAJOR", Term.NEVER, "MINOR", Term.NEVER, "HAS SPACE", Term.SPRING)) },
                { "3BLANK", new Offering(TEST_COURSES.get("3BLANK"), Map.of("MAJOR", Term.NEVER, "MINOR", Term.NEVER, "HAS SPACE", Term.NEVER)) },
        });
    }

    @Test
    public void testOfferingEquals() {
        assertEquals(offering, getOffering(code));
    }

    private static Offering getOffering(String code) {
        Offering result = null;

        for (final var offering : offerings) {
            if (offering.getCourse().code().equals(code)) {
                result = offering;
                break;
            }
        }

        return result;
    }
}
