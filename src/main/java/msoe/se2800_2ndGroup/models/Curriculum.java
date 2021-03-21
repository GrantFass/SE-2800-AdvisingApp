/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package msoe.se2800_2ndGroup.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * A plan for graduation.
 */
public class Curriculum {
    private final List<CurriculumItem> items;

    /**
     * Create a new curriculum.
     *
     * @param items courses and electives in this curriculum, in order
     */
    public Curriculum(List<CurriculumItem> items) {
        this.items = items;
    }

    /**
     * Determine which curriculum items are not yet satisfied.
     *
     * @param courses courses that have been taken already
     * @return unsatisfied items in curriculum order
     */
    public List<CurriculumItem> getUnsatisfiedItems(Collection<Course> courses) {
        // key is item, value is completion status
        var completed = new HashMap<CurriculumItem, Boolean>();

        for (var item : items) {
            completed.put(item, false);
        }

        for (var course : courses) {
            for (var item : items) {
                if (!completed.get(item) && item.satisfiedBy(course)) {
                    completed.put(item, true);
                    break; // don't double-count courses
                }
            }
        }

        // return the unsatisfied items in curriculum order
        var unsatisfied = new ArrayList<CurriculumItem>();

        for (var item : items) {
            if (!completed.get(item)) {
                unsatisfied.add(item);
            }
        }

        return unsatisfied;
    }
}
