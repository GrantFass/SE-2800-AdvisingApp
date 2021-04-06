package msoe.se2800_2ndGroup;

import java.io.IOException;
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
 * * Added case to load the course data CSV files by Grant Fass on Fri, 26 Mar 2021
 * * Added exception to catch block since new exception is thrown by loading course data by Grant Fass on Tue, 30 Mar 2021
 * * Load course data is now passed a scanner
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

        boolean debug = false;
        try (Scanner in = new Scanner(System.in)) {
            //Load default course data on startup
            long startTime = System.nanoTime();
            System.out.format("%s in %d milliseconds\n", model.loadDefaultCourseData(), (System.nanoTime() - startTime) / 1000000); //divide by 1000000 to get milliseconds
            while (true) {
                String input = in.nextLine().trim().toLowerCase();
                if (debug) {
                    System.out.format("Your input: %s\n", input);
                }

                switch (input) {
                    case "exit", "quit" -> model.exitProgram();
                    case "enable debug" -> {
                        System.out.println("Debug Enabled");
                        debug = true;
                    }
                    case "disable debug" -> debug = false;
                    case "store major" -> {
                        outputHyphenLine();
                        System.out.print("Enter Major: ");
                        String major = in.next().trim().toLowerCase();
                        model.storeMajor(major);
                        System.out.println("Major Stored");
                        outputHyphenLine();
                    }
                    case "get course recommendation" -> {
                        outputHyphenLine();
                        String recommendations = model.getCourseRecommendation();
                        System.out.println(recommendations);
                        outputHyphenLine();
                    }
                    case "load course data" -> {
                        startTime = System.nanoTime();
                        outputHyphenLine();
                        System.out.format("%s in %d milliseconds\n", model.loadCourseData(in), (System.nanoTime() - startTime) / 1000000); //divide by 1000000 to get milliseconds
                        outputHyphenLine();
                    }
                    case "load pdf" -> {
                        ImportTranscript importTranscript = new ImportTranscript();
                        importTranscript.readInFile();
                    }
                }
            }
        } catch (Model.InvalidInputException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * outputs a line of hyphens
     *
     * sends a line of hyphens to the standard output
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void outputHyphenLine() {
        System.out.println("------------------------------");
    }

}
