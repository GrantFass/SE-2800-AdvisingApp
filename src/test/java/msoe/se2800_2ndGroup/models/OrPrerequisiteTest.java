package msoe.se2800_2ndGroup.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OrPrerequisiteTest {
    Course cs2711 = new Course("CS2711", 4, new OrPrerequisite(new SinglePrerequisite("CS1011"), new SinglePrerequisite("SE1011")), "Computer Organization");

    @Test
    void testSatisfiedWhenOnlyLeftGiven() {
        assertTrue(cs2711.prerequisite().satisfiedBy(List.of("CS1011")));
    }

    @Test
    void testSatisfiedWhenOnlyRightGiven() {
        assertTrue(cs2711.prerequisite().satisfiedBy(List.of("SE1011")));
    }

    @Test
    void testSatisfiedWhenLeftGivenWithOthers() {
        assertTrue(cs2711.prerequisite().satisfiedBy(List.of("BA1220", "CS1011")));
    }

    @Test
    void testSatisfiedWhenRightGivenWithOthers() {
        assertTrue(cs2711.prerequisite().satisfiedBy(List.of("BA1220", "SE1011")));
    }

    @Test
    void testSatisfiedWhenBothGiven() {
        assertTrue(cs2711.prerequisite().satisfiedBy(List.of("CS1011", "SE1011")));
    }

    @Test
    void testSatisfiedWhenBothGivenWithOthers() {
        assertTrue(cs2711.prerequisite().satisfiedBy(List.of("BA1220", "CS1011", "SE1011")));
    }

    @Test
    void testNotSatisfiedWhenBothMissing() {
        assertFalse(cs2711.prerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testNotSatisfiedWhenBothMissingWithOthers() {
        assertFalse(cs2711.prerequisite().satisfiedBy(List.of("BA1220")));
    }

    @Test
    void testEquals() {
        OrPrerequisite expected = new OrPrerequisite(new SinglePrerequisite("CS1011"), new SinglePrerequisite("SE1011"));
        assertEquals(expected, cs2711.prerequisite());
    }

    @Test
    void testNotEquals() {
        OrPrerequisite unexpected = new OrPrerequisite(new SinglePrerequisite("CS1011"), new NullPrerequisite());
        assertNotEquals(unexpected, cs2711.prerequisite());
    }
}
