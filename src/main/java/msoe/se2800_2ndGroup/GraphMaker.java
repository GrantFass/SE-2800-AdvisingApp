package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.models.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: GraphMaker
 * Description:
 * * Creates GraphNodes from courses.
 * The GraphNode class is responsible for:
 * * modeling a graph of courses for display
 * Modification Log:
 * * File Created by turcinh on Sunday, 10 April 2021
 * * code cleanup from group feedback by turcinh on Monday, 19 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : turcinh
 * @since : Sunday, 10 April 2021
 */
public class GraphMaker {
    /**
     * Get the graph for a course.
     * 
     * @param root the course at the root of the graph
     * @param courses all loaded courses, with descriptions present
     * @return the graph for the course
     * @author : Hunter Turcin
     * @since : Sat, 10 Apr 2021
     */
    public static GraphNode getGraph(Course root, Collection<Course> courses) {
        return new GraphNode(root, getNodes(root.prerequisite(), courses));
    }

    /**
     * Map the prerequisites of a course for a graph of courses.
     *
     * This is called recursively for OrPrerequisites and AndPrerequisites.
     *
     * @param prerequisite prerequisite to get the graph of
     * @param courses courses to turn into from codes
     * @return the child nodes for this graph
     * @author : Hunter Turcin
     * @since : Sat, 10 Apr 2021
     */
    private static Collection<GraphNode> getNodes(Prerequisite prerequisite, Collection<Course> courses) {
        if (prerequisite instanceof NullPrerequisite) {
            return List.of();
        } else if (prerequisite instanceof SinglePrerequisite single) {
            final var course = getCourse(single.code(), courses);
            //TODO: Hunter
            final var children = course != null ? getNodes(course.prerequisite(), courses) : new ArrayList();
            //TODO: Hunter
//            final var children = getNodes(course.prerequisite(), courses);
            return List.of(new GraphNode(course, children));
        } else if (prerequisite instanceof CompositePrerequisite tree) {
            final var leftNodes = getNodes(tree.left(), courses);
            final var rightNodes = getNodes(tree.right(), courses);
            final var nodes = new ArrayList<>(leftNodes);
            nodes.addAll(rightNodes);
            return nodes;
        } else {
            throw new IllegalArgumentException("unknown type: " + prerequisite.getClass());
        }
    }

    /**
     * Get a course from its course code.
     *
     * @param code code for searching
     * @param courses courses to search
     * @return course or null if not found
     * @author : Hunter Turcin
     * @since : Sat, 10 Apr 2021
     */
    private static Course getCourse(String code, Collection<Course> courses) {
        Course result = null;

        for (final var course : courses) {
            if (course.code().equals(code)) {
                result = course;
                break;
            }
        }

        return result;
    }
}
