package msoe.se2800_2ndGroup.FileIO;

import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.*;
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
 * Class Name: TranscriptIO
 * Description:
 * * Handles the importing and exporting of transcript pdf files
 * The TranscriptIO class is responsible for:
 * * Importing transcript pdf files by loading and parsing them.
 * * Exporting transcript pdf files
 * ImportTranscript Modification Log:
 * * File Created by toohillt on Saturday, 20 March 2021
 * * Modify the readInFile method to return the list of courses directly instead of using a private var and a getter by Grant Fass on Tue, 13 Apr 2021
 * * Add methods to remove ignored words and extract course codes from each input line by Grant Fass on Thu, 15 Apr 2021
 * * Clean up main readInFile method by removing extra data structures, adding comments, and adding methods by Grant Fass on Thu, 15 Apr 2021
 * * Update parsing to skip courses that were withdrawn from or failed by Grant Fass on Thu, 15 Apr 2021
 * * Fix error preventing courses with the word 'organization' in the description from being read by Grant Fass on Thu, 15 Apr 2021
 * * Add logger by Grant Fass on Thu, 15 Apr 2021
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * * Make methods static by Grant Fass on Thu, 22 Apr 2021
 * UnofficialTranscript (Export) Modification Log:
 * * File Created by toohillt on Saturday, 10 April 2021
 * * Add method to get a standard string representation of a course for output to PDF by Grant Fass on Thu, 15 Apr 2021
 * * Implement new lines when outputting to PDF by Grant Fass on Thu, 15 Apr 2021
 * * Break the PDF output method into smaller methods by Grant Fass on Thu, 15 Apr 2021
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * Modification Log:
 * * File Created by Grant on Thursday, 22 April 2021
 * * Merged files from both ImportTranscript.java and UnofficialTranscript.java into a single class by Grant Fass on Thu, 22 Apr 2021
 * * Fix pdf loading not closing files correctly by Grant Fass on Thu, 22 Apr 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 22 April 2021
 */
public class TranscriptIO {
    /**
     * Logging system.
     */
    private final static Logger LOGGER = AdvisingLogger.getLogger();
    /**
     * Words that are ignored from the PDF when extracting text.
     */
    private final static String[] IGNORE_WORDS = new String[]{"Milwaukee School of Engineering", "Unofficial Transcript",
            "ID", "NAME", "SSN", "DATE PRINTED", "Undergraduate Division", "Number",
            "Transfer Work", "Term Totals", "Cumulative Totals", "Total Credits Earned",
            "Quarter", "Page", "Major Totals", "* * *   End of Academic Record * * *", "DEGREE SOUGHT",
            "Qual", "Pts GPA", "Cred", "HrsGrade", "Generated On Date"};

    //region transcript export methods

    /**
     * Write the unofficial transcript to a PDF.
     * <p>
     * Sensitive information is not stored by the program and will thus not be
     * present on the generated PDF. The PDF is generated in such a way that it
     * can be used as input to this program.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://www.tutorialspoint.com/pdfbox/pdfbox_adding_pages.htm"}</a>: Creating PDF
     * <a href="#{@link}">{@link "https://stackoverflow.com/a/47731904"}</a>: Newlines in output PDF
     *
     * @param courses        the list of courses to output to the PDF
     * @param outputLocation the location to store the output location to, will default to "./out/Unofficial Transcript.pdf"
     * @throws IOException for errors in writing to the PDF file
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public static void writeFile(ArrayList<Course> courses, String outputLocation) throws IOException {
        if (outputLocation == null || outputLocation.isBlank()) {
            outputLocation = "./out/Unofficial Transcript.pdf";
        }
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        PDPageContentStream contentStream = new PDPageContentStream(doc, page);

        setUpPDF(contentStream);
        buildPDFHeader(contentStream);
        buildPDFBody(contentStream, courses);
        closePDF(contentStream);
        // Save the results and ensure that the document is properly closed:
        doc.save(outputLocation);
        doc.close();
    }

    /**
     * closes and finishes all of the operations associated with writing to a PDF file
     *
     * @param contentStream the pdf to finish output to
     * @throws IOException if there is a problem finishing output
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private static void closePDF(PDPageContentStream contentStream) throws IOException {
        contentStream.endText();
        contentStream.close();
    }

    /**
     * sets up the font and initial output line for the pdf
     *
     * @param contentStream the pdf to set up
     * @throws IOException if there is an issue setting up the pdf
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private static void setUpPDF(PDPageContentStream contentStream) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(100, 700);
    }

    /**
     * Method to build the body of the PDF by outputting each course in the standard format on its own line
     *
     * @param contentStream the PDF to output the body on
     * @param courses       the list of courses to output
     * @throws IOException if there is an issue outputting the body
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private static void buildPDFBody(PDPageContentStream contentStream, ArrayList<Course> courses) throws IOException {
        for (Course course : courses) {
            contentStream.showText(getCourseForOutput(course));
            contentStream.newLineAtOffset(0, -15);
        }
    }

    /**
     * method to build a standard header to the top of the output PDF
     *
     * @param contentStream the PDF to output the header on
     * @throws IOException if there is an issue outputting the header
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private static void buildPDFHeader(PDPageContentStream contentStream) throws IOException {
        contentStream.showText("--School Name--");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("--Student Name--");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText("--Degree Sought--");
        contentStream.newLineAtOffset(0, -15);
        contentStream.showText(String.format("--Generated On Date: %tc--", new Date(System.currentTimeMillis())));
        contentStream.newLineAtOffset(0, -15);
        contentStream.newLineAtOffset(0, -15);
    }

    /**
     * method to format a course into a standard format used for outputting to a PDF document
     * <p>
     * There is a listing of invalid chars for output such as \n and \r as per the below source link.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://stackoverflow.com/questions/46644570/pdfbox-u000a-controllf-is-not-available-in-this-font-helvetica-encoding"}</a>: Output string invalid chars
     *
     * @param course the course to format
     * @return the course formatted as a string
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private static String getCourseForOutput(Course course) {
        String output = String.format("Completed Course: %s", course.code());
        AdvisingLogger.getLogger().log(Level.FINER, "processing course: " + course.code());
        return output;
    }

    //endregion

    //region methods for importing transcripts

    /**
     * This method will check if the current target line contains any of the values in the array of Ignored Words
     * <p>
     * This method iterates through the list of ignored words and compares them against the target line that is passed
     * into the method as a parameter using the .contains method for strings. If an ignored line is found the method
     * will return null, otherwise the method will return the line itself.
     * This method will also return null if the line ends in a W or an F which indicates the course was not passed or withdrawn from.
     *
     * @param line the String to check for ignored words
     * @return null if an ignored line is found, otherwise return the parameter string
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    private static String filterForPassedClasses(String line) {
        //can add ' || line.endsWith("WIP")' if Work in progress courses need to be excluded
        if (line.endsWith("W") || line.endsWith("F")) {
            LOGGER.finest(String.format("Input line (%s) ends in W or F which signifies the class was failed or withdrawn from and should be skipped", line));
            return null;
        }
        for (String ignore : IGNORE_WORDS) {
            if (line.contains(ignore)) {
                LOGGER.finest(String.format("Input line (length = %d) contains an ignored word (%s)", line.length(), ignore));
                return null;
            }
        }
        LOGGER.finer("line (" + line + ") is valid");
        return line;
    }

    /**
     * This method checks for the course code in the string and returns it
     * <p>
     * This method first splits the passed in inputLine on each space (' ').
     * This method then checks to see if the each of the words that were separated contain ('.') or ('--') and that they contain a digit
     * If the split words do not contain either of the strings, and they contain a digit then they are added to the output arrayList and returned.
     * The returned list contains only the course codes then since all words containing descriptions or credits are removed
     *
     * @param inputLine the String to split and scan for the course code
     * @return a String containing the course code or null if one was not found.
     * @author : Grant Fass, Teresa T., Hunter Turcin
     * @since : Thu, 15 Apr 2021
     */
    private static String checkStringForCourseCode(String inputLine) {
        for (String word : inputLine.split(" ")) {
            if (!word.contains(".") && !word.contains("--") && word.matches("\\w{2}\\d.*")) {
                LOGGER.finer(String.format("Input Line (%s) contains course code (%s)", inputLine, word));
                return word.equals("SS415AMAmerican") ? "SS415AM" : word;
            }
        }
        LOGGER.finest(String.format("Input Line (%s) does not contain a course code", inputLine));
        return null;
    }

    /**
     * This method reads in the entire pdf then parses out the course codes and returns them as an ArrayList of courses.
     * <p>
     * The method will use the PDF loader to load the entire PDF into a single String object.
     * The method then splits the pdf String into individual lines.
     * For each line the method removes all ignored words then extracts course codes if they exist.
     * Then each course code is turned into a Course object and returned as part of an ArrayList.
     * This method converts both the input text array and output course array list to hash sets to remove duplicates
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://www.programiz.com/java-programming/examples/convert-array-set"}</a>: Help converting arrays to hash sets
     *
     * @param file the file object to use to create the PDDocument object to parse the PDF.
     * @return an ArrayList of Course objects containing the courses from the transcript
     * @throws IOException for issues creating the specified file or reading it
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public static ArrayList<Course> readInFile(File file) throws IOException {
        LOGGER.fine(String.format("Reading in PDF file from location: %s", file));
        ArrayList<Course> courses = new ArrayList<>();
        PDDocument doc = PDDocument.load(file);
        String text = new PDFTextStripper().getText(doc);
        doc.close();
        //split pdf into individual lines
        Set<String> inputLines = new HashSet<>(Arrays.asList(text.replace("\r", "").split("\n")));
        //remove ignored words from each line then attempt to parse into a course code
        for (String inputLine : inputLines) {
            String s = filterForPassedClasses(inputLine);
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
        LOGGER.fine(String.format("Read %d courses from transcript", output.size()));
        return output;
    }

    /**
     * This method reads in the entire pdf then parses out the course codes and returns them as an ArrayList of courses.
     * <p>
     * This method first queries the user for a file location using the standard FileIO.getUserInputFileLocation
     * method. Then the method passes off to the other readInFile(File file) method to create the PDF reading object
     * and parse the courses out.
     *
     * @param scanner the scanner object to use to query the user for a file location.
     * @return an ArrayList of Course objects containing the courses from the transcript
     * @throws IOException                            for issues creating the specified file or reading it
     * @throws CustomExceptions.InvalidInputException for issues verifying the specified file location
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public static ArrayList<Course> readInFile(Scanner scanner) throws IOException, CustomExceptions.InvalidInputException {
        //load entire pdf into a single string
        String pathName = FileIO.getUserInputFileLocation("Transcript.pdf", ".pdf", scanner, System.out);
        File file = new File(pathName);
        return readInFile(file);
    }

    //endregion

}
