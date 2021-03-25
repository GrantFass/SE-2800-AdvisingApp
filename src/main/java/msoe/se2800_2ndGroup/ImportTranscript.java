package msoe.se2800_2ndGroup;

import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
//import org.apache.pdfbox.tools.ImportFDF;
//import org.apache.pdfbox.pdmodel.PDDocument;


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
        System.out.println("Do you want to read in a file (\"Y/N\")?");
        Scanner scanner = new Scanner(System.in);
        while(!(scanner.nextLine().equalsIgnoreCase("Y") && scanner.nextLine().equalsIgnoreCase("N"))){
            System.out.println("Please enter a \"Y\" or \"N\"");
        }
        if(scanner.nextLine().equalsIgnoreCase("Y")) {
            File file;
            try {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Unofficial Transcript PDF");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
                file = fileChooser.showOpenDialog(new Stage());
                if (file == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No file was chosen.");
                    alert.showAndWait();
                } else {
                    //PDDocument doc = PDDocument.load(file);
                    //String text = new PDFTextStripper().getText(doc);
                }
            } catch (IllegalArgumentException notValid) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Not a valid file name.");
                alert.showAndWait();
            } catch (IOException event){
                event.printStackTrace();
            }

    }
    }
}
