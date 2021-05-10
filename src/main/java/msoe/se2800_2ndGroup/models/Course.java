package msoe.se2800_2ndGroup.models;

/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: Course
 * Creation Date: Tuesday, 16 March 2021
 * Original Author: Hunter Turcin
 * Description: A course that may or may not depend on other courses.
 * The Course class is responsible for:
 *     - associating a course code with a set of prerequisites
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-03-16
 *     - additional overridden Object methods added by Hunter Turcin on 2021-04-04
 *     - code cleanup using JDK 16 features done by Hunter Turcin on 2021-04-07
 *     - code cleanup from group feedback by Hunter Turcin on 2021-04-18
 *     - Override comparison by Grant Fass on Sun, 9 May 2021
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 16 Mar 2021
 */
public record Course(String code, int credits, Prerequisite prerequisite, String description)
        implements CurriculumItem {
    /**
     * Create a course where only the code is known.
     *
     * @param code the course code
     * @author : Hunter Turcin
     * @since : Thu, 8 Apr 2021
     */
    public Course(String code) {
        this(code, 0, new NullPrerequisite(), "");
    }

    /**
     * Check if this course has been taken yet.
     *
     * @param course course to check
     * @return true if the course matches
     */
    @Override
    public boolean satisfiedBy(Course course) {
        return code.equals(course.code);
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * This method overrides the default comparison. if the item being compared is an elective
     * than it is automatically lesser, otherwise the value fo the comparison of the codes is
     * returned.
     * <p>
     * Sources:
     * *  <a href="#{@link}">{@link "https://www.geeksforgeeks
     * *  .org/how-to-override-compareto-method-in-java/"}</a>: Help overriding comparison
     * *
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     * @author : Grant Fass
     * @since : Sun, 9 May 2021
     */
    @Override
    public int compareTo(CurriculumItem o) {
        if (o instanceof Elective) {
            //o is Lesser
            return -1;
        } else {
            return this.code.compareTo(((Course) o).code);
        }
    }
}
