package msoe.se2800_2ndGroup;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import msoe.se2800_2ndGroup.logger.AdvisingLogger;
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
 * * Loading and parsing unofficial transcript PDF files into ArrayList of Course objects
 * Modification Log:
 * * File Created by toohillt on Saturday, 20 March 2021
 * * Modify the readInFile method to return the list of courses directly instead of using a private var and a getter by Grant Fass on Tue, 13 Apr 2021
 * * Add methods to remove ignored words and extract course codes from each input line by Grant Fass on Thu, 15 Apr 2021
 * * Clean up main readInFile method by removing extra data structures, adding comments, and adding methods by Grant Fass on Thu, 15 Apr 2021
 * * Update parsing to skip courses that were withdrawn from or failed by Grant Fass on Thu, 15 Apr 2021
 * * Fix error preventing courses with the word 'organization' in the description from being read by Grant Fass on Thu, 15 Apr 2021
 * * Add logger by Grant Fass on Thu, 15 Apr 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 20 March 2021
 */
public class ImportTranscript {
    private final static String[] IGNORE_WORDS = new String[]{"Milwaukee School of Engineering", "Unofficial Transcript",
            "ID", "NAME", "SSN", "DATE PRINTED", "Undergraduate Division", "Course", "Number",
            "Transfer Work", "Term Totals", "Cumulative Totals", "Total Credits Earned",
            "Quarter", "Page", "Major Totals", "* * *   End of Academic Record * * *", "DEGREE SOUGHT",
            "Qual", "Pts GPA", "Cred", "HrsGrade"};

    /**
     * This method will check if the current target line contains any of the values in the array of Ignored Words
     *
     * This method iterates through the list of ignored words and compares them against the target line that is passed
     * into the method as a parameter using the .contains method for strings. If an ignored line is found the method
     * will return null, otherwise the method will return the line itself.
     * This method will also return null if the line ends in a W or an F which indicates the course was not passed or withdrawn from.
     * @param line the String to check for ignored words
     * @return null if an ignored line is found, otherwise return the parameter string
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    private String checkLineForIgnoredWordsAndFailedClassesAndWithdrawnClasses(String line) {
        if (line.endsWith("W") || line.endsWith("F")) {
            AdvisingLogger.getLogger().log(Level.FINEST, String.format("Input line (%s) ends in W or F which signifies the class was failed or withdrawn from and should be skipped", line));
            return null;
        }
        for (String ignore: IGNORE_WORDS) {
            if (line.contains(ignore)) {
                AdvisingLogger.getLogger().log(Level.FINEST, String.format("Input line (length = %d) contains an ignored word (%s)", line.length(), ignore));
                return null;
            }
        }
        AdvisingLogger.getLogger().log(Level.FINER, "line (" + line + ") is valid");
        return line;
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
                AdvisingLogger.getLogger().log(Level.FINER, String.format("Input Line (%s) contains course code (%s)", inputLine, word));
                return word.equals("SS415AMAmerican") ? "SS415AM" : word;
            }
        }
        AdvisingLogger.getLogger().log(Level.FINEST, String.format("Input Line (%s) does not contain a course code", inputLine));
        return null;
    }

    /**
     * This method reads in the entire pdf then parses out the course codes and returns them as an ArrayList of courses.
     *
     * The method will use the PDF loader to load the entire PDF into a single String object.
     * The method then splits the pdf String into individual lines.
     * For each line the method removes all ignored words then extracts course codes if they exist.
     * Then each course code is turned into a Course object and returned as part of an ArrayList.
     * This method converts both the input text array and output course array list to hash sets to remove duplicates
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.programiz.com/java-programming/examples/convert-array-set"}</a>: Help converting arrays to hash sets
     *
     * @param file the file object to use to create the PDDocument object to parse the PDF.
     * @return an ArrayList of Course objects containing the courses from the transcript
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public ArrayList<Course> readInFile(File file) throws IOException {
        AdvisingLogger.getLogger().log(Level.FINE, String.format("Reading in PDF file from location: %s", file));
        ArrayList<Course> courses = new ArrayList<>();
        PDDocument doc = PDDocument.load(file);
        String text = new PDFTextStripper().getText(doc);
        //split pdf into individual lines
        Set<String> inputLines = new HashSet<>(Arrays.asList(text.replace("\r", "").split("\n")));
        //remove ignored words from each line then attempt to parse into a course code
        for(String inputLine: inputLines) {
            String s = checkLineForIgnoredWordsAndFailedClassesAndWithdrawnClasses(inputLine);
            if (s != null) {
                String courseCode = checkStringForCourseCode(s);
                if (courseCode != null) {
                    Course course = new Course(courseCode);
                    courses.add(course);
                    System.out.format("Adding Course: %s\n", course.code());
                }
            }
        }
        ArrayList<Course> output = new ArrayList<>(new HashSet<>(courses));
        AdvisingLogger.getLogger().log(Level.FINE, String.format("Read %d courses from transcript", output.size()));
        return output;
    }

    /**
     * This method reads in the entire pdf then parses out the course codes and returns them as an ArrayList of courses.
     *
     * This method first queries the user for a file location using the standard FileIO.getUserInputFileLocation
     * method. Then the method passes off to the other readInFile(File file) method to create the PDF reading object
     * and parse the courses out.
     * @param scanner the scanner object to use to query the user for a file location.
     * @return an ArrayList of Course objects containing the courses from the transcript
     * @throws IOException for issues creating the specified file or reading it
     * @throws Model.InvalidInputException for issues verifying the specified file location
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public ArrayList<Course> readInFile(Scanner scanner) throws IOException, Model.InvalidInputException {
        //load entire pdf into a single string
        String pathName = FileIO.getUserInputFileLocation("Transcript.pdf", ".pdf", scanner);
        File file = new File(pathName);
        return readInFile(file);
    }
}
