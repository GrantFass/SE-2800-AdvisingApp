package msoe.se2800_2ndGroup;

import static org.junit.Assert.assertEquals;

import msoe.se2800_2ndGroup.Graphing.GraphMaker;
import msoe.se2800_2ndGroup.Graphing.GraphNode;
import msoe.se2800_2ndGroup.models.AndPrerequisite;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.NullPrerequisite;
import msoe.se2800_2ndGroup.models.OrPrerequisite;
import msoe.se2800_2ndGroup.models.SinglePrerequisite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(Parameterized.class)
public class GraphMakerTest {
    private static final Map<String, Course> TEST_COURSES = Map.of(
            "A", new Course("A", 1, new AndPrerequisite(new SinglePrerequisite("B"), new SinglePrerequisite("C")), "First"),
            "B", new Course("B", 2, new OrPrerequisite(new SinglePrerequisite("D"), new SinglePrerequisite("E")), "Second"),
            "C", new Course("C", 3, new SinglePrerequisite("F"), "Third"),
            "D", new Course("D", 4, new NullPrerequisite(), "Fourth"),
            "E", new Course("E", 5, new NullPrerequisite(), "Fifth"),
            "F", new Course("F", 6, new NullPrerequisite(), "Sixth")
    );

    private final Course course;
    private final GraphNode expected;

    public GraphMakerTest(Course course, GraphNode expected) {
        this.course = course;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // can make a single-node graph
                {
                        TEST_COURSES.get("F"),
                        new GraphNode(TEST_COURSES.get("F")),
                },
                // can make a graph with one child
                {
                        TEST_COURSES.get("C"),
                        new GraphNode(
                                TEST_COURSES.get("C"),
                                List.of(new GraphNode(TEST_COURSES.get("F")))
                        ),
                },
                // can make a graph with two children
                {
                        TEST_COURSES.get("B"),
                        new GraphNode(
                                TEST_COURSES.get("B"),
                                List.of(
                                        new GraphNode(TEST_COURSES.get("D")),
                                        new GraphNode(TEST_COURSES.get("E"))
                                )
                        ),
                },
                // can make a graph with nested children
                {
                        TEST_COURSES.get("A"),
                        new GraphNode(
                                TEST_COURSES.get("A"),
                                List.of(
                                        new GraphNode(
                                                TEST_COURSES.get("B"),
                                                List.of(
                                                        new GraphNode(TEST_COURSES.get("D")),
                                                        new GraphNode(TEST_COURSES.get("E"))
                                                )
                                        ),
                                        new GraphNode(
                                                TEST_COURSES.get("C"),
                                                List.of(new GraphNode(TEST_COURSES.get("F")))
                                        )
                                )
                        ),
                },
        });
    }

    @Test
    public void testEquals() {
        final var node = GraphMaker.getGraph(course, TEST_COURSES.values());

        assertEquals(expected, node);
    }
}
