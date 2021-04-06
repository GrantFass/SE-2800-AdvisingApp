package msoe.se2800_2ndGroup;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
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
 * <p>
 * Copyright (C): TBD
 *
 * @author : toohillt
 * @since : Saturday, 20 March 2021
 */
public class ImportTranscript {
    public void readInFile(){
        //TODO: remove this since I do not think that we really need to ask the user if they want to read in a file after they have already selected that they would like to do so through the case statement in the CLI
        System.out.println("Do you want to read in a file (\"Y/N\")?");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        while(!(answer.equalsIgnoreCase("Y") || answer.equalsIgnoreCase("N"))){
            System.out.println("Please enter a \"Y\" or \"N\"");
        }
        if(answer.equalsIgnoreCase("Y")) {
            try {
                String pathName = FileIO.getUserInputFileLocation("Transcript.pdf", ".pdf");
                File file = new File(pathName);
                //For future use with a GUI
//                FileChooser fileChooser = new FileChooser();
//                fileChooser.setTitle("Open Unofficial Transcript PDF");
//                fileChooser.getExtensionFilters().addAll(
//                        new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
//                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) {
                    //TODO: remove this, This is already covered by the getUserInputFileLocation method since it throws an error if there are issues validating that the user file exists.
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
                    //TODO: potentially replace with the enhanced for loop.
                    boolean accessed = false;
                    boolean accessable = false;
                    for(int j=0; j<words.length; j++){
                        accessable = false;
                        //TODO: Move interior for loop into method
                        for(int i=0; i<ignoreWords.length; i++) {
                            if (words[j].contains(ignoreWords[i])) {
                                accessable = true;
                            }
                        }
                        if(accessable){
                            words[j] = " ";
                        } else {
                            input.add(words[j]);
                        }
                    }
                    for(int k = 0; k<input.size(); ++k){
                        System.out.println(input.get(k));
                    }

                }
            } catch (IllegalArgumentException notValid) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Not a valid file name.");
                alert.showAndWait();
            } catch (IOException event){
                event.printStackTrace();
            } catch (Model.InvalidInputException e) {
                System.out.println(e.getMessage());
            }
            //TODO: file errors reading in

        }
    }
}
