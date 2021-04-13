package msoe.se2800_2ndGroup;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
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
 * * Load course data is now passed a scanner by Grant Fass on Tue, 30 Mar 2021
 * * Load course data on program start by Grant Fass on Tue, 6 Apr 2021
 * * Implement CLI entry to view course offerings by term by Grant Fass on Wed, 7 Apr 2021
 * * Move the call to ImportTranscript.readInFile to Model.java by Grant Fass on Tue, 13 Apr 2021
 * * Separate the query for terms to a separate method to enforce DRY by Grant Fass on Tue, 13 Apr 2021
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
                        System.out.print("Enter Major Abbreviation: ");
                        String major = in.next().trim().toLowerCase();
                        model.storeMajor(major);
                        System.out.println("Major Stored");
                        outputHyphenLine();
                    }
                    case "get course recommendation" -> {
                        outputHyphenLine();
                        HashMap<String, Boolean> terms = getTerms(in);
                        System.out.println(model.getCourseRecommendation(terms.get("fall"), terms.get("winter"), terms.get("spring")));
                        outputHyphenLine();
                    }
                    case "load course data" -> {
                        startTime = System.nanoTime();
                        outputHyphenLine();
                        System.out.format("%s in %d milliseconds\n", model.loadCourseData(in), (System.nanoTime() - startTime) / 1000000); //divide by 1000000 to get milliseconds
                        outputHyphenLine();
                    }
                    case "load pdf" -> {
                        model.loadUnofficialTranscript(in);
                    }
                    case "view course offerings" -> {
                        outputHyphenLine();
                        HashMap<String, Boolean> terms = getTerms(in);
                        System.out.println(model.getCourseOfferingsAsString(terms.get("fall"), terms.get("winter"), terms.get("spring")));
                        outputHyphenLine();
                    }
                }
            }
        } catch (Model.InvalidInputException | IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private HashMap<String, Boolean> getTerms(Scanner in) {
        boolean fall = false;
        boolean winter = false;
        boolean spring = false;
        System.out.print("Would you like to display fall courses? (y/n): ");
        if (in.next().trim().toLowerCase().equalsIgnoreCase("y")) {
            fall = true;
        }
        System.out.print("Would you like to display winter courses? (y/n): ");
        if (in.next().trim().toLowerCase().equalsIgnoreCase("y")) {
            winter = true;
        }
        System.out.print("Would you like to display spring courses? (y/n): ");
        if (in.next().trim().toLowerCase().equalsIgnoreCase("y")) {
            spring = true;
        }
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put("fall", fall);
        hashMap.put("winter", winter);
        hashMap.put("spring", spring);
        return hashMap;
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
