package msoe.se2800_2ndGroup;

import java.util.Scanner;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: CLI
 * Description:
 * * This class runs all of the CLI input commands for the program
 * The CLI class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added method to run and a method to exit the program as an example by Grant on Saturday, 20 March 2021
 *
 * @since : Saturday, 20 March 2021
 * @author : Grant
 *
 * Copyright (C): TBD
 */
public class CLI {

    /*
     * Model used for execution of tasks
     */
    private final Model model;

    /**
     * Constructor for the class
     *
     * This constructor links a model to this instance so that it operates with the same information as the GUI
     *
     * @param model the model to link
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public CLI(Model model) {
        this.model = model;
    }

    /**
     * This method runs all of the CLI operations for the project
     *
     * This method runs inside of a task launched by App.java during startup
     * It runs all of the CLI operations for the project inside a loop.
     * Will stop executing if the input is quit.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/21962055"}</a> Help exiting program through if statement
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void processCommandLine() {
        try (Scanner in = new Scanner(System.in)) {
            while (true) {
                String input = in.next().trim().toLowerCase();
                System.out.format("Your input: %s\n", input);
                if (input.equals("quit")) {
                    model.exitProgram();
                }
            }
        }
    }

}
