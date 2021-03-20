package org.example.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class NullPrerequisiteTest {
    Course ba1220 = new Course("BA1220", 3, new NullPrerequisite(), "Microeconomics");
    Course bi102 = new Course("BI102", 4, new NullPrerequisite(), "Cell Biology and Genetics");

    @Test
    void testSatisfiedByNothing() {
        assertTrue(ba1220.getPrerequisite().satisfiedBy(List.of()));
    }

    @Test
    void testSatisfiedBySomething() {
        assertTrue(ba1220.getPrerequisite().satisfiedBy(List.of(bi102)));
    }
}
