package msoe.se2800_2ndGroup.models;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AndPrerequisiteTest {
    Course bi102 = new Course("BI102", 4, new NullPrerequisite(), "Cell Biology and Genetics");
    Course ch200 = new Course("CH200", 4, new NullPrerequisite(), "Chemistry I");
    Course ch222 = new Course("CH222", 3, new SinglePrerequisite(ch200), "Organic Chemistry I");
    Course ch223 = new Course("CH223", 4, new SinglePrerequisite(ch222), "Biochemistry");
    Course bi2020 = new Course("BI2020", 4, new AndPrerequisite(new SinglePrerequisite(bi102), new SinglePrerequisite(ch223)), "Cellular Microbiology");

    @Test
    void testNotSatisfiedWhenOnlyLeftGiven() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of(bi102)));
    }

    @Test
    void testNotSatisfiedWhenOnlyRightGiven() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of(ch223)));
    }

    @Test
    void testNotSatisfiedWhenLeftMissing() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of(ch200, ch222, ch223)));
    }

    @Test
    void testNotSatisfiedWhenRightMissing() {
        assertFalse(bi2020.getPrerequisite().satisfiedBy(List.of(bi102, ch200, ch222)));
    }

    @Test
    void testSatisfiedWhenBothGivenAlone() {
        assertTrue(bi2020.getPrerequisite().satisfiedBy(List.of(bi102, ch223)));
    }

    @Test
    void testSatisfiedWhenBothGivenWithOthers() {
        assertTrue(bi2020.getPrerequisite().satisfiedBy(List.of(bi102, ch200, ch222, ch223)));
    }
}
