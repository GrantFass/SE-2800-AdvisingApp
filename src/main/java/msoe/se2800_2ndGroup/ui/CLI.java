package msoe.se2800_2ndGroup.ui;

import msoe.se2800_2ndGroup.Data.GraduationPlanCompiler;
import msoe.se2800_2ndGroup.Data.Manipulators;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.FileIO.TranscriptIO;
import msoe.se2800_2ndGroup.Model;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.AcademicTerm;
import msoe.se2800_2ndGroup.models.Course;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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
 * * Processing operations for the program through the command line
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added method to run and a method to exit the program as an example by Grant on Saturday, 20
 * March 2021
 * * Added case to load the course data CSV files by Grant Fass on Fri, 26 Mar 2021
 * * Added exception to catch block since new exception is thrown by loading course data by Grant
 * Fass on Tue, 30 Mar 2021
 * * Load course data is now passed a scanner by Grant Fass on Tue, 30 Mar 2021
 * * Load course data on program start by Grant Fass on Tue, 6 Apr 2021
 * * Implement CLI entry to view course offerings by term by Grant Fass on Wed, 7 Apr 2021
 * * Move the call to ImportTranscript.readInFile to Model.java by Grant Fass on Tue, 13 Apr 2021
 * * Separate the query for terms to a separate method to enforce DRY by Grant Fass on Tue, 13
 * Apr 2021
 * * Clean up multiple calls to outputHyphenLine() by Grant Fass on Thu, 15 Apr 2021
 * * Update so exceptions are no longer fatal by Grant Fass on Thu, 15 Apr 2021
 * * Implement logger by Grant Fass on Thu, 15 Apr 2021
 * * Add CLI option to store unofficial transcripts
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * * Removed references to Model.java as it is now a utility class by Grant Fass on Thu, 22 Apr 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Saturday, 20 March 2021
 */
public class CLI {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * This method runs all of the CLI operations for the project
     * <p>
     * This method runs inside of a task launched by App.java during startup
     * It runs all of the CLI operations for the project inside a loop.
     * Will stop executing if the input is quit.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up
     * FXML loading with Maven
     * <a href="#{@link}">{@link "https://stackoverflow.com/a/21962055"}</a> Help exiting program
     * through if statement
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void processCommandLine() {
        LOGGER.info("Starting CLI");
        //Load default course data on startup
        long startTime = System.nanoTime();
        try {
            final int nanosecondsToMilliseconds = 1000000;
            LOGGER.info(String.format("%s in %d milliseconds\n", Model.loadDefaultCourseData(),
                                      (System.nanoTime() - startTime) / nanosecondsToMilliseconds)
                                .replace("\n", "\\n"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        //Loop for CLI operations
        Scanner in = new Scanner(System.in);
        boolean run = true;
        while (run) {
            try {
                outputHyphenLine();
                String input = in.nextLine().trim().toLowerCase();
                LOGGER.fine(String.format("Your input: %s\n", input).replace("\n", "\\n"));
                switch (input) {
                    case "exit", "quit" -> {
                        LOGGER.info("Shutting down program");
                        run = false;
                    }
                    case "store major" -> {
                        System.out.print("Enter Major Abbreviation: ");
                        String major = in.next().trim().toLowerCase();
                        LOGGER.fine("Attempting to store major: " + major);
                        Model.storeMajor(major);
                        System.out.println("Major Stored");
                        LOGGER.fine("Major Stored");
                    }
                    case "get course recommendation" -> {
                        HashMap<String, Boolean> terms = getTerms(in);
                        LOGGER.fine("Getting Course Recommendations");
                        String output = Model.getCourseRecommendationAsString(terms.get("fall"),
                                                                              terms.get("winter"),
                                                                              terms.get("spring"));
                        System.out.println(output);
                        LOGGER.log(Level.FINE, "Output Course Recommendations: \n" + output,
                                   output);
                    }
                    case "load course data" -> {
                        final int nanosecondsToMilliseconds = 1000000;
                        String output =
                                String.format("%s in %d milliseconds\n", Model.loadCourseData(in),
                                              (System.nanoTime() - startTime) /
                                              nanosecondsToMilliseconds);
                        LOGGER.log(Level.FINE, "Course data loaded utilizing standard scanner: \n" +
                                               output.replace("\n", "\\n"), output);
                    }
                    case "load pdf" -> {
                        LOGGER.fine("Loading unofficial transcript using standard scanner");
                        Model.loadUnofficialTranscript(in);
                    }
                    case "view prerequisites" -> {
                        System.out.println("Enter course: ");
                        String course = in.nextLine();
                        String prerequisites = Model.viewPrerequisiteCourses(course);
                        System.out.println(prerequisites);
                        LOGGER.fine(String.format(
                                "The prerequisites for the course of code: %s are: %s", course,
                                prerequisites));
                    }
                    case "store pdf" -> {
                        LOGGER.fine(
                                "Storing unofficial transcript to application output directory");
                        Model.storeUnofficialTranscript();
                    }
                    case "store custom pdf" -> {
                        LOGGER.fine("Storing custom unofficial transcript");
                        System.out.println(
                                "Enter course codes to output separated by spaces (EX: BA1220 " +
                                "BA3444 SE2800): ");
                        String[] strings = in.nextLine().split("\\W");
                        ArrayList<Course> output = new ArrayList<>();
                        for (String s : strings) {
                            output.add(new Course(Manipulators.standardizeCourse(s)));
                        }
                        Model.storeCustomUnofficialTranscript("./out", output);
                        LOGGER.fine("Stored custom unofficial transcript of " + output.size() +
                                    " courses to application output directory");
                    }
                    case "view course offerings" -> {
                        HashMap<String, Boolean> terms = getTerms(in);
                        LOGGER.fine("Getting Course Offerings");
                        String output = Model.getCourseOfferingsAsString(terms.get("fall"),
                                                                         terms.get("winter"),
                                                                         terms.get("spring"));
                        System.out.println(output);
                        LOGGER.log(Level.FINE, "Output Course Offerings", output);
                    }
                    case "view prerequisite graph" -> {
                        System.out.print("Course code: ");
                        final var code = in.next().trim();
                        LOGGER.fine("Generating Prerequisite Graph For Course Code: " + code);
                        final var graph = Model.getCourseGraph(code);
                        System.out.println(graph);
                        LOGGER.log(Level.FINE, "Prerequisite Graph Generated: \n" + graph, graph);
                    }
                    case "future course enrollment" -> {
                        System.out.println("Enter a directory location: ");
                        final var directory = in.nextLine().trim();
                        LOGGER.fine("Loading all transcripts using standard scanner");
                        outputHyphenLine();
                        System.out.print(Manipulators.outputHashSet(
                                TranscriptIO.readMultiplePDFs(directory)));
                        LOGGER.fine("Enrollment Display Done!");
                    }
                    case "view graduation plan" -> {
                        System.out.println("Enter the target number of credits and the tolerance " +
                                           "to generate the graduation plan with. IE: '16 2' will" +
                                           " generate a graduation plan for 14 to 18 credits per " +
                                           "Academic term.");
                        String[] s = in.nextLine().split(" ");
                        int target = Integer.parseInt(s[0]);
                        int tolerance = Integer.parseInt(s[1]);
                        LOGGER.fine(
                                String.format("Generating graduation plan for %d +-%d " + "credits",
                                              target, tolerance));
                        List<AcademicTerm> graduationPlan = GraduationPlanCompiler
                                .generateGraduationPlanVersion2(target, tolerance);
                        System.out.format("Graduation Plan for %d - %d credit target per term:\n",
                                          target - tolerance, target + tolerance);
                        System.out.println(Manipulators.getGraduationPlanAsString(graduationPlan));
                        LOGGER.info("Graduation plan generated");
                    }
                }
            } catch (CustomExceptions.InvalidInputException | IOException | NullPointerException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
                LOGGER.log(Level.WARNING, "Exception thrown by method in CLI", e);
            }
        }
    }

    /**
     * This method queries the user for which terms they would like to display data for
     *
     * @param in the scanner used to query the user
     * @return a HashMap containing the keys 'fall', 'winter', 'spring' and boolean values
     * associated with the key
     * @author : Grant Fass, Hunter Turcin
     * @since : Thu, 13 Apr 2021
     */
    private HashMap<String, Boolean> getTerms(Scanner in) {
        LOGGER.fine("Getting Terms");

        HashMap<String, Boolean> hashMap = new HashMap<>();

        for (final var season : new String[]{"fall", "winter", "spring"}) {
            final var result =
                    askBinary(in, String.format("Would you like to display %s courses?", season));
            hashMap.put(season, result);
        }

        LOGGER.log(Level.FINE, String.format("Terms Stored: %s", hashMap), hashMap);
        return hashMap;
    }

    /**
     * Ask the user a yes-or-no question.
     * <p>
     * Instructions are appended to the end of the prompt.
     *
     * @param in     input source
     * @param prompt question to ask
     * @return true if yes, false if no
     * @author : Hunter Turcin
     * @since : Tue, 20 Apr 2021
     */
    private boolean askBinary(Scanner in, String prompt) {
        System.out.printf("%s (y/n): ", prompt);
        return in.next().trim().equalsIgnoreCase("y");
    }

    /**
     * outputs a line of hyphens
     * <p>
     * sends a line of hyphens to the standard output
     *
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void outputHyphenLine() {
        System.out.println("------------------------------");
        LOGGER.finer("\n------------------------------");
    }
}