package sample.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class OrPrerequisiteTest {
    Course ba1220 = new Course("BA1220", 3, new NullPrerequisite(), "Microeconomics");
    Course se1011 = new Course("SE1011", 4, new NullPrerequisite(), "Software Development I");
    Course cs1011 = new Course("CS1011", 4, new NullPrerequisite(), "Software Development I");
    Course cs2711 = new Course("CS2711", 4, new OrPrerequisite(new SinglePrerequisite(cs1011), new SinglePrerequisite(se1011)), "Computer Organization");

    @Test
    void testSatisfiedWhenOnlyLeftGiven() {
        assertTrue(cs2711.getPrerequisite().satisfiedBy(List.of(cs1011)));
    }

    @Test
    void testSatisfiedWhenOnlyRightGiven() {
        assertTrue(cs2711.getPrerequisite().satisfiedBy(List.of(se1011)));
    }

    @Test
    void testSatisfiedWhenLeftGivenWithOthers() {
        assertTrue(cs2711.getPrerequisite().satisfiedBy(List.of(ba1220, cs1011)));
    }

    @Test
    void testSatisfiedWhenRightGivenWithOthers() {
        assertTrue(cs2711.getPrerequisite().satisfiedBy(List.of(ba1220, se1011)));
    }

    @Test
    void testSatisfiedWhenBothGiven() {
        assertTrue(cs2711.getPrerequisite().satisfiedBy(List.of(cs1011, se1011)));
    }

    @Test
    void testSatisfiedWhenBothGivenWithOthers() {
        assertTrue(cs2711.getPrerequisite().satisfiedBy(List.of(ba1220, cs1011, se1011)));
    }

    @Test
    void testNotSatisfiedWhenBothMissing() {
        assertFalse(cs2711.getPrerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testNotSatisfiedWhenBothMissingWithOthers() {
        assertFalse(cs2711.getPrerequisite().satisfiedBy(List.of(ba1220)));
    }
}
