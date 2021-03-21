/*
 * Course: SE2800 - 021
 * Spring 2020-21
 * Lab 1 - User Stories
 * Name: Hunter Turcin
 * Created: 2021-03-16
 */
package msoe.se2800_2ndGroup.models;

/**
 * Which term a course is for.
 */
public class Term {
    /**
     * The fall term.
     */
    public static final Term FALL = new Term(1, "Fall");

    /**
     * The winter term.
     */
    public static final Term WINTER = new Term(2, "Winter");

    /**
     * The spring term.
     */
    public static final Term SPRING = new Term(3, "Spring");

    private final int id;
    private final String season;

    private Term(int id, String season) {
        this.id = id;
        this.season = season;
    }

    /**
     * Get a term from its id.
     *
     * @param id the id of the term
     * @return the term
     */
    public static Term fromId(int id) {
        return switch (id) {
            case 1 -> FALL;
            case 2 -> WINTER;
            case 3 -> SPRING;
            default -> throw new IllegalArgumentException("unknown term id: " + id);
        };
    }

    public int getId() {
        return id;
    }

    public String getSeason() {
        return season;
    }
}
