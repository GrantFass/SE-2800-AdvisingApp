package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.logger.AdvisingLogger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;

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
 * * Processing opperations for the program through the command line
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
 * * Clean up multiple calls to outputHyphenLine() by Grant Fass on Thu, 15 Apr 2021
 * * Update so exceptions are no longer fatal by Grant Fass on Thu, 15 Apr 2021
 * * Implement logger by Grant Fass on Thu, 15 Apr 2021
 * * Add CLI option to store unofficial transcripts
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
        AdvisingLogger.getLogger().info("Starting CLI");
        //Load default course data on startup
        long startTime = System.nanoTime();
        try {
            final int nanosecondsToMilliseconds = 1000000;
            AdvisingLogger.getLogger().info(String.format("%s in %d milliseconds\n", model.loadDefaultCourseData(), (System.nanoTime() - startTime) / nanosecondsToMilliseconds).replace("\n", "\\n"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //Loop for CLI operations
        Scanner in = new Scanner(System.in);
        while (true) {
            try {
                outputHyphenLine();
                String input = in.nextLine().trim().toLowerCase();
                AdvisingLogger.getLogger().log(Level.FINER, String.format("Your input: %s\n", input).replace("\n", "\\n"));
                switch (input) {
                    case "exit", "quit" -> {
                        AdvisingLogger.getLogger().info("Shutting down program");
                        model.exitProgram();
                    }
                    case "store major" -> {
                        System.out.print("Enter Major Abbreviation: ");
                        String major = in.next().trim().toLowerCase();
                        AdvisingLogger.getLogger().log(Level.FINER, "Attempting to store major: " + major);
                        model.storeMajor(major);
                        System.out.println("Major Stored");
                        AdvisingLogger.getLogger().log(Level.FINER, "Major Stored");
                    }
                    case "get course recommendation" -> {
                        HashMap<String, Boolean> terms = getTerms(in);
                        AdvisingLogger.getLogger().log(Level.FINER, "Getting Course Recommendations");
                        String output = model.getCourseRecommendation(terms.get("fall"), terms.get("winter"), terms.get("spring"));
                        System.out.println(output);
                        AdvisingLogger.getLogger().log(Level.FINER, "Output Course Recommendations: \n" + output, output);
                    }
                    case "load course data" -> {
                        final int nanosecondsToMilliseconds = 1000000;
                        String output = String.format("%s in %d milliseconds\n", model.loadCourseData(in), (System.nanoTime() - startTime) / nanosecondsToMilliseconds);
                        AdvisingLogger.getLogger().log(Level.FINER, "Course data loaded utilizing standard scanner: \n" + output.replace("\n", "\\n"), output);
                    }
                    case "load pdf" -> {
                        AdvisingLogger.getLogger().log(Level.FINER, "Loading unofficial transcript using standard scanner");
                        model.loadUnofficialTranscript(in);
                    }
                    case "store pdf" -> {
                        AdvisingLogger.getLogger().log(Level.FINER, "Storing unofficial transcript");
                        model.storeUnofficialTranscript();
                    }
                    case "view course offerings" -> {
                        HashMap<String, Boolean> terms = getTerms(in);
                        AdvisingLogger.getLogger().log(Level.FINER, "Getting Course Offerings");
                        String output = model.getCourseOfferingsAsString(terms.get("fall"), terms.get("winter"), terms.get("spring"));
                        System.out.println(output);
                        AdvisingLogger.getLogger().log(Level.FINER, "Output Course Offerings", output);
                    }
                    case "view prerequisite graph" -> {
                        System.out.print("Course code: ");
                        final var code = in.next().trim();
                        AdvisingLogger.getLogger().log(Level.FINER, "Generating Prerequisite Graph For Course Code: " + code);
                        try {
                            final var graph = model.getCourseGraph(code);
                            System.out.println(graph);
                            AdvisingLogger.getLogger().log(Level.FINER, "Prerequisite Graph Generated: \n" + graph, graph);
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                            AdvisingLogger.getLogger().log(Level.WARNING, "Error Generating Prerequisite Graph: \n" + e.getMessage(), e);
                        }
                    }
                }
            } catch (Model.InvalidInputException | IOException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                AdvisingLogger.getLogger().log(Level.WARNING, "Exception thrown by method in CLI", e);
            }
        }
    }

    /**
     * This method queries the user for which terms they would like to display data for
     * @param in the scanner used to query the user
     * @return a HashMap containing the keys 'fall', 'winter', 'spring' and boolean values associated with the key
     * @author : Grant Fass
     * @since : Thu, 13 Apr 2021
     */
    private HashMap<String, Boolean> getTerms(Scanner in) {
        AdvisingLogger.getLogger().log(Level.FINER, "Getting Terms");
        boolean fall = false;
        boolean winter = false;
        boolean spring = false;
        System.out.print("Would you like to display fall courses? (y/n): ");
        if (in.next().trim().equalsIgnoreCase("y")) {
            fall = true;
        }
        System.out.print("Would you like to display winter courses? (y/n): ");
        if (in.next().trim().equalsIgnoreCase("y")) {
            winter = true;
        }
        System.out.print("Would you like to display spring courses? (y/n): ");
        if (in.next().trim().equalsIgnoreCase("y")) {
            spring = true;
        }
        HashMap<String, Boolean> hashMap = new HashMap<>();
        hashMap.put("fall", fall);
        hashMap.put("winter", winter);
        hashMap.put("spring", spring);
        AdvisingLogger.getLogger().log(Level.FINER, String.format("Terms Stored: Fall(%s), Winter(%s), Spring(%s)", fall, winter, spring), hashMap);
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
        AdvisingLogger.getLogger().log(Level.FINER, "\n------------------------------");
    }

}
