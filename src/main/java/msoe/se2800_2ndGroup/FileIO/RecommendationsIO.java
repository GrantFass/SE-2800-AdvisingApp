package msoe.se2800_2ndGroup.FileIO;

import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.CurriculumItem;
import msoe.se2800_2ndGroup.models.Elective;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: RecommendationsIO
 * Description:
 * * Handles the exporting of recommendations pdf files
 * The TranscriptIO class is responsible for:
 * * Exporting recommendations pdf files
 * ImportTranscript Modification Log:
 * * File Created by Hunter Turcin on Saturday, 9 May 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : Hunter Turcin
 * @since : Sunday, 9 May 2021
 */
public class RecommendationsIO {
    /**
     * Logging system.
     */
    private final static Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * Write recommendations to a PDF.
     *
     * @param items    recommendation items to write
     * @param location location to write
     * @throws IOException could not write PDF
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    public static void write(List<CurriculumItem> items, String location) throws IOException {
        if (location == null || location.isBlank()) {
            location = "./out/Course Recommendations.pdf";
        }

        final var document = new PDDocument();
        final var page = new PDPage();
        document.addPage(page);
        final var content = new PDPageContentStream(document, page);
        setup(content);
        writeHeader(content);
        writeBody(content, items);
        close(document, content, location);
    }

    /**
     * Begin writing the PDF.
     *
     * @param content stream to write to
     * @throws IOException could not write PDF
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    private static void setup(PDPageContentStream content) throws IOException {
        final var font = PDType1Font.HELVETICA;
        content.beginText();
        content.setFont(font, 12);
        content.newLineAtOffset(100, 700);
    }

    /**
     * Write the PDF header.
     *
     * @param content stream to write to
     * @throws IOException could not write PDF
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    private static void writeHeader(PDPageContentStream content) throws IOException {
        content.showText("--School Name--");
        content.newLineAtOffset(0, -15);
        content.showText("--Student Name--");
        content.newLineAtOffset(0, -15);
        content.showText("--Degree Sought--");
        content.newLineAtOffset(0, -15);
        content.showText(
                String.format("--Generated On Date: %tc", new Date(System.currentTimeMillis())));
        content.newLineAtOffset(0, -15);
        content.newLineAtOffset(0, -15);
    }

    /**
     * Write the PDF body.
     *
     * @param content stream to write to
     * @param items   recommendations to write
     * @throws IOException could not write PDF
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    private static void writeBody(PDPageContentStream content, List<CurriculumItem> items)
    throws IOException {
        for (final var item : items) {
            content.showText(getRecommendationLine(item));
            content.newLineAtOffset(0, -15);
        }
    }

    /**
     * Finish writing the PDF.
     *
     * @param document document to save
     * @param content  stream to write to
     * @param location location to write to
     * @throws IOException could not write PDF
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    private static void close(PDDocument document, PDPageContentStream content, String location)
    throws IOException {
        content.endText();
        content.close();
        document.save(location);
        document.close();
    }

    /**
     * Generate a human-friendly line for this recommendation.
     *
     * @param recommendation recommendation to make line of
     * @return line for recommendation
     * @author : Hunter Turcin
     * @since : Sun, 9 May 2021
     */
    private static String getRecommendationLine(CurriculumItem recommendation) {
        final String line;

        if (recommendation instanceof final Course course) {
            line = String.format("%s - %s", course.code(), course.description());
        } else if (recommendation instanceof final Elective elective) {
            line = String.format("%s - %s", elective.getCode(), elective.getDescription());
        } else {
            throw new IllegalStateException("unknown subclass: " + recommendation.getClass());
        }

        return line;
    }
}
