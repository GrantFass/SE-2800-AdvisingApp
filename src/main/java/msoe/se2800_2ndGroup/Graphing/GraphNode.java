package msoe.se2800_2ndGroup.Graphing;

import msoe.se2800_2ndGroup.models.*;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: GraphNode
 * Description:
 * * Node on a graph of courses.
 * The Curriculum class is responsible for:
 * * modeling a graph of courses for display
 * Modification Log:
 * * File Created by turcinh on Sunday, 10 April 2021
 * * code cleanup from group feedback by turcinh on Monday, 19 April 2021
 * * choose symbol based on prerequisite by turcinh on Sunday, 25 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : turcinh
 * @since : Sunday, 10 April 2021
 */
public record GraphNode(Course course, Collection<GraphNode> children) {
    /**
     * The amount of indentation per graph level.
     */
    private static final String INDENTATION = "  ";

    /**
     * Create a new node with no initial children.
     *
     * @param course the course for this node
     */
    public GraphNode(Course course) {
        this(course, new ArrayList<>());
    }

    /**
     * Get the string representation of this graph.
     * <p>
     * This node's course's code and description are printed, followed by
     * all its children, using spaces to show the current level of depth
     * for a node in the graph.
     *
     * @return the string representation of this graph
     * @author : Hunter Turcin
     * @since : Sat, 10 Apr 2021
     */
    public String getStringGraph() {
        return getStringGraph(0, '+');
    }

    /**
     * Print the graph for this node and its children.
     * <p>
     * Each level of children has higher indentation.
     *
     * @param depth  amount of indentation to have before printing
     * @param symbol what symbol to use for graphing
     * @return string of graph
     * @author : Hunter Turcin
     * @since : Sat, 10 Apr 2021
     */
    private String getStringGraph(int depth, char symbol) {
        final var builder = new StringBuilder();
        builder.append(INDENTATION.repeat(depth));
        builder.append(symbol);
        builder.append(' ');
        builder.append(getStringNode());

        for (final var child : children) {
            builder.append("\n");
            builder.append(child.getStringGraph(depth + 1, getSymbol()));
        }

        return builder.toString();
    }

    /**
     * Get the string representation of this node.
     *
     * @return the string representation of this node
     * @author : Hunter Turcin
     * @since : Sat, 10 Apr 2021
     */
    private String getStringNode() {
        return String.format("%s (%s)", course.code(), course.description());
    }

    /**
     * Get the symbol for this node.
     *
     * @return the symbol for this node
     * @author : Hunter Turcin
     * @since : Sun, 25 Apr 2021
     */
    private char getSymbol() {
        final char symbol;
        final var prerequisite = course.prerequisite();

        if (prerequisite instanceof SinglePrerequisite) {
            symbol = '+';
        } else if (prerequisite instanceof NullPrerequisite) {
            symbol = '+';
        } else if (prerequisite instanceof AndPrerequisite) {
            symbol = '&';
        } else if (prerequisite instanceof OrPrerequisite) {
            symbol = '|';
        } else {
            throw new IllegalStateException("no symbol for: " + prerequisite.getClass());
        }

        return symbol;
    }
}
