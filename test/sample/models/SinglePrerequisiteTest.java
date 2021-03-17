package sample.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SinglePrerequisiteTest {
    Course bi1010 = new Course("BI1010", 3, new NullPrerequisite(), "Human Anatomy and Physiology I");
    Course bi1020 = new Course("BI1020", 4, new SinglePrerequisite(bi1010), "Human Anatomy and Physiology II");
    Course bi1030 = new Course("BI1030", 4, new SinglePrerequisite(bi1010), "Human Anatomy and Physiology III");

    @Test
    void testCourseDoesNotSatisfyOwnPrerequisite() {
        assertFalse(bi1020.getPrerequisite().satisfiedBy(List.of(bi1020)));
    }

    @Test
    void testNotSatisfiedByBlankList() {
        assertFalse(bi1020.getPrerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testSatisfiedBySingleCourseList() {
        assertTrue(bi1020.getPrerequisite().satisfiedBy(List.of(bi1010)));
    }

    @Test
    void testSatisfiedByMultipleCourseList() {
        assertTrue(bi1020.getPrerequisite().satisfiedBy(List.of(bi1030, bi1010)));
    }
}
