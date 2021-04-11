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

    private static Collection<GraphNode> getNodes(Prerequisite prerequisite, Collection<Course> courses) {
        if (prerequisite instanceof NullPrerequisite) {
            return null;
        } else if (prerequisite instanceof SinglePrerequisite single) {
            final var course = getCourse(single.code(), courses);
            return List.of(new GraphNode(course));
        } else if (prerequisite instanceof AndPrerequisite and) {
            final var leftNodes = getNodes(and.left(), courses);
            final var rightNodes = getNodes(and.right(), courses);
            final var nodes = new ArrayList<>(leftNodes);
            nodes.addAll(rightNodes);
            return nodes;
        } else if (prerequisite instanceof OrPrerequisite or) {
            final var leftNodes = getNodes(or.left(), courses);
            final var rightNodes = getNodes(or.right(), courses);
            final var nodes = new ArrayList<>(leftNodes);
            nodes.addAll(rightNodes);
            return nodes;
        } else {
            throw new IllegalArgumentException("unknown type: " + prerequisite.getClass());
        }
    }

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
