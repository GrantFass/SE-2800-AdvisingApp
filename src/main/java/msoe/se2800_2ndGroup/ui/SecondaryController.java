package msoe.se2800_2ndGroup.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import msoe.se2800_2ndGroup.Data.GraduationPlanCompiler;
import msoe.se2800_2ndGroup.Data.Manipulators;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.FileIO.TranscriptIO;
import msoe.se2800_2ndGroup.Model;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.AcademicTerm;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static javafx.scene.control.Alert.AlertType.ERROR;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: SecondaryController
 * Description:
 * * This is the controller for the second window in the program
 * The SecondaryController class is responsible for:
 * * Managing the actions exclusive to the graph window
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added methods to control text version of Prerequisite graphs by Grant Fass on Wed, 21 Apr 2021
 * * Removed references to Model.java from App.java as it is now a utility class by Grant Fass on
 * Thu, 22 Apr 2021
 *
 * @author : Grant
 * <p>
 * Copyright (C): TBD
 * @since : Saturday, 20 March 2021
 */
public class SecondaryController extends Controller {

    @FXML
    private TextField mainSearchBar;
    @FXML
    private Label mainLabel;
    @FXML
    private TextArea mainTextArea;

    public SecondaryController() {
        super();
    }

    public void initialize() {
        updateSearchBar();
    }

    /**
     * method to update the search bar with the last course code clicked on between stages when
     * this one is loaded
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    private void updateSearchBar() {
        mainSearchBar.setText(Manipulators.standardizeCourse(lastCourseCode));
    }


    /**
     * This method always runs on the FX thread
     * This method first will get the course code and standardize it from the text field search bar
     * This method will then get the prerequisite graph for the course and output it to the text
     * area
     * TODO: change this to use a canvas instead or something in the future
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    public void viewPrerequisiteGraph() {
        Model.ensureFXThread(() -> {
            mainLabel.setText("Viewing Prerequisite Graph:");
            String code = Manipulators.standardizeCourse(mainSearchBar.getText());
            try {
                final var graph = Model.getCourseGraph(code);
                final var text = graph + "\n\n" +
                                 "A course depends on the indented courses underneath it.\n" +
                                 "All & courses must be taken to take the parent course.\n" +
                                 "Only one | course must be taken to take the parent course.\n" +
                                 "There is nothing special about + courses.";
                mainTextArea.setText(text);
            } catch (CustomExceptions.InvalidInputException e) {
                displayAlert(ERROR, "Unknown course", "Unknown course",
                             "There is no course for this code");
            }
        });
    }

    public void viewGraduationPlan() {
        Model.ensureFXThread(() -> {
            mainLabel.setText("Viewing Graduation Plan:");
            int target = 16;
            int tolerance = 2;
            try {
                String[] s = mainSearchBar.getText().split(" ");
                target = Integer.parseInt(s[0]);
                tolerance = Integer.parseInt(s[1]);
            } catch (Exception ignored) { }
            try {
                AdvisingLogger.getLogger()
                        .fine(String.format("Generating graduation plan for %d +-%d " + "credits",
                                            target, tolerance));
                List<AcademicTerm> graduationPlan =
                        GraduationPlanCompiler.generateGraduationPlanVersion2(target, tolerance);
                mainTextArea.setText(String.format("""
                                                   Graduation Plan for %d - %d credit target per term:
                                                   %s
                                                   """, target - tolerance, target + tolerance,
                                                   Manipulators.getGraduationPlanAsString(
                                                           graduationPlan)));
            } catch (CustomExceptions.InvalidInputException e) {
                displayAlert(ERROR, "Data Not Loaded", "Missing Data", "Missing major");
            }
        });
    }

    /**
     * Method used to switch what controller and FXML resource is displayed in the GUI
     * <p>
     * Method switches the displayed controller and FXML by calling App.setRoot with the
     * associated FXML resource of the window to switch to.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up
     * FXML loading with Maven
     *
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    @FXML
    private void switchToPrimary() throws IOException {
        super.switchTo("primary");
    }

    /**
     * method used to output the projected course enrollment to the main text area in the graph
     * window.
     *
     * @author : Grant Fass
     * @since : Mon, 10 May 2021
     */
    public void viewProjectedCourseEnrollment() {
        File directory = getDirectoryLocation("Select Transcript PDF Directory");
        mainLabel.setText("Viewing Projected Course Enrollment");
        try {
            AdvisingLogger.getLogger().info("Generating course enrollment for PDF Transcript " +
                                            "files in the following directory:\n\t" +
                                            directory.getAbsolutePath());
            mainTextArea.setText(String.format(
                    "Course Code : Number of Transcripts Missing Course For " +
                    "Specified Major\n%s", Manipulators.outputHashSet(
                            TranscriptIO.readMultiplePDFs(directory.getAbsolutePath()))));
        } catch (IOException | CustomExceptions.InvalidInputException e) {
            AdvisingLogger.getLogger().warning(e.getMessage());
            displayAlert(Alert.AlertType.ERROR, "IOException", null, e.getMessage());
        }
    }
}