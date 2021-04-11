package msoe.se2800_2ndGroup;

import static org.junit.Assert.assertEquals;

import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.NullPrerequisite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class GraphNodeTest {
    private static final List<Course> TEST_COURSES = List.of(
            new Course("ROOT", 0, new NullPrerequisite(), "Root Course"),
            new Course("1-1", 0, new NullPrerequisite(), "Course 1-1"),
            new Course("1-2", 0, new NullPrerequisite(), "Course 1-2"),
            new Course("1-3", 0, new NullPrerequisite(), "Course 1-3"),
            new Course("2-1", 0, new NullPrerequisite(), "Course 2-1"),
            new Course("2-2", 0, new NullPrerequisite(), "Course 2-2"),
            new Course("2-3", 0, new NullPrerequisite(), "Course 2-3"),
            new Course("3-1", 0, new NullPrerequisite(), "Course 3-1"),
            new Course("3-2", 0, new NullPrerequisite(), "Course 3-2"),
            new Course("3-3", 0, new NullPrerequisite(), "Course 3-3")
    );

    private final Course course;
    private final Collection<GraphNode> children;
    private final String expected;

    public GraphNodeTest(Course course, Collection<GraphNode> children, String expected) {
        this.course = course;
        this.children = children;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // can graph a single node
                {
                        getCourse("ROOT"),
                        List.of(),
                        "+ ROOT (Root Course)"
                },
                // can graph a node with one child
                {
                        getCourse("ROOT"),
                        List.of(new GraphNode(getCourse("1-1"))),
                        """
                        + ROOT (Root Course)
                         + 1-1 (Course 1-1)"""
                },
                // can graph several children with no children
                {
                        getCourse("ROOT"),
                        List.of(
                                new GraphNode(getCourse("1-1")),
                                new GraphNode(getCourse("1-2")),
                                new GraphNode(getCourse("1-3"))
                        ),
                        """
                        + ROOT (Root Course)
                         + 1-1 (Course 1-1)
                         + 1-2 (Course 1-2)
                         + 1-3 (Course 1-3)"""
                },
                // can graph nested children
                {
                        getCourse("ROOT"),
                        List.of(
                                new GraphNode(
                                        getCourse("1-1"),
                                        List.of(
                                                new GraphNode(
                                                        getCourse("2-1"),
                                                        List.of(
                                                                new GraphNode(getCourse("3-1"))
                                                        )
                                                )
                                        )
                                )
                        ),
                        """
                        + ROOT (Root Course)
                         + 1-1 (Course 1-1)
                          + 2-1 (Course 2-1)
                           + 3-1 (Course 3-1)"""
                },
                // combination of above tests
                {
                        getCourse("ROOT"),
                        List.of(
                                new GraphNode(
                                        getCourse("1-1"),
                                        List.of(
                                                new GraphNode(getCourse("2-1")),
                                                new GraphNode(
                                                        getCourse("2-2"),
                                                        List.of(
                                                                new GraphNode(getCourse("3-1")),
                                                                new GraphNode(getCourse("3-2")),
                                                                new GraphNode(getCourse("3-3"))
                                                        )
                                                ),
                                                new GraphNode(getCourse("2-3"))
                                        )
                                ),
                                new GraphNode(getCourse("1-2")),
                                new GraphNode(
                                        getCourse("1-3"),
                                        List.of(
                                                new GraphNode(getCourse("2-1")),
                                                new GraphNode(getCourse("2-2"))
                                        )
                                )
                        ),
                        """
                        + ROOT (Root Course)
                         + 1-1 (Course 1-1)
                          + 2-1 (Course 2-1)
                          + 2-2 (Course 2-2)
                           + 3-1 (Course 3-1)
                           + 3-2 (Course 3-2)
                           + 3-3 (Course 3-3)
                          + 2-3 (Course 2-3)
                         + 1-2 (Course 1-2)
                         + 1-3 (Course 1-3)
                          + 2-1 (Course 2-1)
                          + 2-2 (Course 2-2)"""
                }
        });
    }

    @Test
    public void testOutput() {
        final var node = new GraphNode(course, children);
        final var output = node.getStringGraph();

        assertEquals(expected, output);
    }

    private static Course getCourse(String code) {
        Course result = null;

        for (final var course : TEST_COURSES) {
            if (course.code().equals(code)) {
                result = course;
                break;
            }
        }

        return result;
    }
}
