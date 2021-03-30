package msoe.se2800_2ndGroup.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AndPrerequisiteTest {
    Course bi2020 = new Course("BI2020", 4, new AndPrerequisite(new SinglePrerequisite("BI102"), new SinglePrerequisite("CH223")), "Cellular Microbiology");

    @Test
    void testNotSatisfiedWhenOnlyLeftGiven() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of("BI102")));
    }

    @Test
    void testNotSatisfiedWhenOnlyRightGiven() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of("CH223")));
    }

    @Test
    void testNotSatisfiedWhenLeftMissing() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of("CH200", "CH222", "CH223")));
    }

    @Test
    void testNotSatisfiedWhenRightMissing() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of("BI102", "CH200", "CH222")));
    }

    @Test
    void testSatisfiedWhenBothGivenAlone() {
        assertTrue(bi2020.getPrerequisite().satisfiedBy(List.of("BI102", "CH223")));
    }

    @Test
    void testSatisfiedWhenBothGivenWithOthers() {
        assertTrue(bi2020.getPrerequisite().satisfiedBy(List.of("BI102", "CH200", "CH222", "CH223")));
    }
}
