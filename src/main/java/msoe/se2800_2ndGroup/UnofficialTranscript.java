package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: magana041group2
 * Class Name: UnofficialTranscript
 * Description:
 * * Represent an unofficial transcript in the form of a PDF.
 * The UnofficialTranscript class is responsible for:
 * * Outputting PDF files
 * Modification Log:
 * * File Created by toohillt on Saturday, 10 April 2021
 * * Add method to get a standard string representation of a course for output to PDF by Grant Fass on Thu, 15 Apr 2021
 * * Implement new lines when outputting to PDF by Grant Fass on Thu, 15 Apr 2021
 * * Break the PDF output method into smaller methods by Grant Fass on Thu, 15 Apr 2021
 * * code cleanup from group feedback by turcinh on Tuesday, 20 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 10 April 2021
 */
public class UnofficialTranscript {

    /**
     * Write the unofficial transcript to a PDF.
     *
     * Sensitive information is not stored by the program and will thus not be
     * present on the generated PDF. The PDF is generated in such a way that it
     * can be used as input to this program.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.tutorialspoint.com/pdfbox/pdfbox_adding_pages.htm"}</a>: Creating PDF
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/47731904"}</a>: Newlines in output PDF
     *
     * @param courses the list of courses to output to the PDF
     * @param outputLocation the location to store the output location to, will default to "./out/Unofficial Transcript.pdf"
     * @throws IOException for errors in writing to the PDF file
     * @author : Grant Fass, Teresa T.
     * @since : Thu, 15 Apr 2021
     */
    public void writeFile(ArrayList<Course> courses, String outputLocation) throws IOException {
        if(outputLocation == null || outputLocation.isBlank()) {
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
     * @param contentStream the pdf to finish output to
     * @throws IOException if there is a problem finishing output
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private void closePDF(PDPageContentStream contentStream) throws IOException {
        contentStream.endText();
        contentStream.close();
    }

    /**
     * sets up the font and initial output line for the pdf
     * @param contentStream the pdf to set up
     * @throws IOException if there is an issue setting up the pdf
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private void setUpPDF(PDPageContentStream contentStream) throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(100, 700);
    }

    /**
     * Method to build the body of the PDF by outputting each course in the standard format on its own line
     * @param contentStream the PDF to output the body on
     * @param courses the list of courses to output
     * @throws IOException if there is an issue outputting the body
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private void buildPDFBody(PDPageContentStream contentStream, ArrayList<Course> courses) throws IOException {
        for (Course course : courses) {
            contentStream.showText(getCourseForOutput(course));
            contentStream.newLineAtOffset(0, -15);
        }
    }

    /**
     * method to build a standard header to the top of the output PDF
     * @param contentStream the PDF to output the header on
     * @throws IOException if there is an issue outputting the header
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private void buildPDFHeader(PDPageContentStream contentStream) throws IOException {
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
     *
     * There is a listing of invalid chars for output such as \n and \r as per the below source link.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/questions/46644570/pdfbox-u000a-controllf-is-not-available-in-this-font-helvetica-encoding"}</a>: Output string invalid chars
     *
     * @param course the course to format
     * @return the course formatted as a string
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    private String getCourseForOutput(Course course) {
        String output = String.format("Completed Course: %s", course.code());
        AdvisingLogger.getLogger().log(Level.FINER, "processing course: " + course.code());
        return output;
    }
}
