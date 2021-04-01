package msoe.se2800_2ndGroup.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SinglePrerequisiteTest {
    Course bi1020 = new Course("BI1020", 4, new SinglePrerequisite("BI1010"), "Human Anatomy and Physiology II");

    @Test
    void testCourseDoesNotSatisfyOwnPrerequisite() {
        assertFalse(bi1020.getPrerequisite().satisfiedBy(List.of("BI1020")));
    }

    @Test
    void testNotSatisfiedByBlankList() {
        assertFalse(bi1020.getPrerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testSatisfiedBySingleCourseList() {
        assertTrue(bi1020.getPrerequisite().satisfiedBy(List.of("BI1010")));
    }

    @Test
    void testSatisfiedByMultipleCourseList() {
        assertTrue(bi1020.getPrerequisite().satisfiedBy(List.of("BI1030", "BI1010")));
    }

    @Test
    void testEquals() {
        assertEquals(new SinglePrerequisite("BI1010"), bi1020.getPrerequisite());
    }

    @Test
    void testNotEquals() {
        assertNotEquals(new SinglePrerequisite("CH200"), bi1020.getPrerequisite());
    }
}
