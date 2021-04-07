package msoe.se2800_2ndGroup.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NullPrerequisiteTest {
    Course ba1220 = new Course("BA1220", 3, new NullPrerequisite(), "Microeconomics");

    @Test
    void testSatisfiedByNothing() {
        assertTrue(ba1220.prerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testSatisfiedBySomething() {
        assertTrue(ba1220.prerequisite().satisfiedBy(List.of("BI102")));
    }

    @Test
    void testEquals() {
        assertEquals(new NullPrerequisite(), ba1220.prerequisite());
    }

    @Test
    void testNotEquals() {
        assertNotEquals(new SinglePrerequisite("invalid"), ba1220.prerequisite());
    }
}
