package msoe.se2800_2ndGroup;

import msoe.se2800_2ndGroup.models.Course;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import java.io.IOException;
import java.util.ArrayList;

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
 * * <class description here>
 * The UnofficialTranscript class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by toohillt on Saturday, 10 April 2021
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 10 April 2021
 */
public class UnofficialTranscript {
    public void writeFile(ArrayList<Course> courses) {
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            PDFont font = PDType1Font.HELVETICA;
            PDPageContentStream contentStream = new PDPageContentStream(doc, page);
            contentStream.beginText();
            contentStream.setFont(font, 12);
            contentStream.newLineAtOffset(100, 700);
            for(int i = 0; i < courses.size(); i++){
                contentStream.showText(courses.get(i).toString());
            }
            contentStream.showText("Hello World");
            contentStream.endText();
            contentStream.close();

            // Save the results and ensure that the document is properly closed:
            doc.save("Unofficial Transcript.pdf");
            doc.close();
        } catch(IOException ioException){
            System.out.println("Error reading in file.");
        }
    }
}
