package msoe.se2800_2ndGroup.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NullPrerequisiteTest {
    Course ba1220 = new Course("BA1220", 3, new NullPrerequisite(), "Microeconomics");

    @Test
    void testSatisfiedByNothing() {
        assertTrue(ba1220.getPrerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testSatisfiedBySomething() {
        assertTrue(ba1220.getPrerequisite().satisfiedBy(List.of("BI102")));
    }
}
