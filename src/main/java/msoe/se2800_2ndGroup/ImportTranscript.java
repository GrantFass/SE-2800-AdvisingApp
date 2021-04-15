package msoe.se2800_2ndGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import msoe.se2800_2ndGroup.models.Course;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: magana041group2
 * Class Name: ImportTranscript
 * Description:
 * * <class description here>
 * The ImportTranscript class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by toohillt on Saturday, 20 March 2021
 * * Modify the readInFile method to return the list of courses directly instead of using a private var and a getter by Grant Fass on Tue, 13 Apr 2021
 * * Add methods to remove ignored words and extract course codes from each input line by Grant Fass on Thu, 15 Apr 2021
 * * Clean up main readInFile method by removing extra data structures, adding comments, and adding methods by Grant Fass on Thu, 15 Apr 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 20 March 2021
 */
public class ImportTranscript {
    private final static String[] IGNORE_WORDS = new String[]{"Milwaukee School of Engineering", "Unofficial Transcript",
            "ID", "NAME", "SSN", "DATE PRINTED", "Undergraduate Division", "Course", "Number",
            "Transfer Work", "Organization", "Term Totals", "Cumulative Totals", "Total Credits Earned",
            "Quarter", "Page", "Major Totals", "* * *   End of Academic Record * * *", "DEGREE SOUGHT",
            "Qual", "Pts GPA", "Cred", "HrsGrade"};

    /**
     * This method will check if the current target word contains any of the values in the array of Ignored Words
     *
     * This method iterates through the list of ignored words and compares them against the target word that is passed
     * into the method as a parameter using the .contains method for strings. If an ignored word is found the method
     * will return null, otherwise the method will return the word itself.
     * @param word the String to check for ignored words
     * @return null if an ignored word is found, otherwise return the parameter string
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    private String checkForIgnoredWord(String word) {
        for (String ignore: IGNORE_WORDS) {
            if (word.contains(ignore)) {
                return null;
            }
        }
        return word;
    }

    /**
     * This method checks for the course code in the string and returns it
     *
     * This method first splits the passed in inputLine on each space (' ').
     * This method then checks to see if the each of the words that were separated contain ('.') or ('--') and that they contain a digit
     * If the split words do not contain either of the strings, and they contain a digit then they are added to the output arrayList and returned.
     * The returned list contains only the course codes then since all words containing descriptions or credits are removed
     * @param inputLine the String to split and scan for the course code
     * @return a String containing the course code or null if one was not found.
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    private String checkStringForCourseCode(String inputLine) {
        for (String word : inputLine.split(" ")) {
            if (!word.contains(".") && !word.contains("--") && word.matches(".*\\d.*")) {
                return word;
            }
        }
        return null;
    }

    /**
     * This method reads in the entire pdf then parses out the course codes and returns them as an ArrayList of courses.
     *
     * This method first queries the user for a file location using the standard FileIO.getUserInputFileLocation
     * method. Then the method will use the PDF loader to load the entire PDF into a single String object.
     * The method then splits the pdf String into individual lines.
     * For each line the method removes all ignored words then extracts course codes if they exist.
     * Then each course code is turned into a Course object and returned as part of an ArrayList.
     * @param scanner the scanner object to use to query the user for a file location.
     * @return an ArrayList of Course objects containing the courses from the transcript
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public ArrayList<Course> readInFile(Scanner scanner) {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            //load entire pdf into a single string
            String pathName = FileIO.getUserInputFileLocation("Transcript.pdf", ".pdf", scanner);
            File file = new File(pathName);
            PDDocument doc = PDDocument.load(file);
            String text = new PDFTextStripper().getText(doc);
            //split pdf into individual lines
            String[] inputLines = text.split("\n");
            //remove ignored words from each line then attempt to parse into a course code
            for(String inputLine: inputLines) {
                String s = checkForIgnoredWord(inputLine);
                if (s != null) {
                    String courseCode = checkStringForCourseCode(s);
                    if (courseCode != null) {
                        Course course = new Course(courseCode);
                        courses.add(course);
                        System.out.format("Adding Course: %s\n", course.code());
                    }
                }
            }
            System.out.println("Successfully read in file!");
        } catch (IOException event) {
            event.printStackTrace();
        } catch (Model.InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        //TODO: file errors reading in
        return courses;
    }
}
