package msoe.se2800_2ndGroup;

import javafx.scene.control.Alert;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 20 March 2021
 */
public class ImportTranscript {

    public ArrayList<Course> readInFile(Scanner scanner) {
        ArrayList<Course> courses = new ArrayList<>();
        try {
            //TODO: Verify this is fixed after feature 16 is merged in
            String pathName = FileIO.getUserInputFileLocation("Transcript.pdf", ".pdf", scanner);
            File file = new File(pathName);
            //For future use with a GUI
//                FileChooser fileChooser = new FileChooser();
//                fileChooser.setTitle("Open Unofficial Transcript PDF");
//                fileChooser.getExtensionFilters().addAll(
//                        new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
//                file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "No file was chosen.");
                alert.showAndWait();
            } else {
                PDDocument doc = PDDocument.load(file);
                String text = new PDFTextStripper().getText(doc);
                String[] words = text.split("\n");
                String[] ignoreWords = new String[]{"Milwaukee School of Engineering", "Unofficial Transcript",
                        "ID", "NAME", "SSN", "DATE PRINTED", "Undergraduate Division", "Course", "Number",
                        "Transfer Work", "Organization", "Term Totals", "Cumulative Totals", "Total Credits Earned",
                        "Quarter", "Page", "Major Totals", "* * *   End of Academic Record * * *", "DEGREE SOUGHT",
                        "Qual", "Pts GPA", "Cred", "HrsGrade"};
                ArrayList<String> input = new ArrayList<>();
                ArrayList<String> findRemovableWords = new ArrayList<>();
                //TODO: potentially replace with the enhanced for loop.
                boolean accessed = false;
                boolean accessable = false;
                for (int j = 0; j < words.length; j++) {
                    accessable = false;
                    //TODO: Move interior for loop into method
                    for (int i = 0; i < ignoreWords.length; i++) {
                        if (words[j].contains(ignoreWords[i])) {
                            accessable = true;
                        }
                    }
                    if (accessable) {
                        words[j] = " ";
                    } else {
                        input.add(words[j]);
                    }
                }
                words = new String[0];
                for (int x = 0; x < input.size(); x++) {
                    String[] removableWords = input.get(x).split(" ");
                    for (int y = 0; y < removableWords.length; y++) {
                        if (!removableWords[y].contains(".") && !removableWords[y].contains("--")) {
                            findRemovableWords.add(removableWords[y]);
                        }
                    }
                }
                //input cleared to avoid storing data
                input.clear();
                //HERE'S JUST COURSE CODES!!!
                ArrayList<String> courseCodes = new ArrayList<>();
                for (int x = 0; x < findRemovableWords.size(); x++) {
                    if (findRemovableWords.get(x).matches(".*\\d.*")) {
                        courseCodes.add(findRemovableWords.get(x));
                    }
                }

                //Course objects created
                for (int i = 0; i < courseCodes.size(); i++){
                    Course course = new Course(courseCodes.get(i));
                    courses.add(course);
                }

                for (int k = 0; k < findRemovableWords.size(); ++k) {
                    System.out.println(findRemovableWords.get(k));
                }
                System.out.println("Successfully read in file!");
            }
        } catch (IllegalArgumentException notValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Not a valid file name.");
            alert.showAndWait();
        } catch (IOException event) {
            event.printStackTrace();
        } catch (Model.InvalidInputException e) {
            System.out.println(e.getMessage());
        }
        //TODO: file errors reading in
        return courses;
    }
}
