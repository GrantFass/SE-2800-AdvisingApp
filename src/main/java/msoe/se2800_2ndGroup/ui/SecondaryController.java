package msoe.se2800_2ndGroup.ui;

import static javafx.scene.control.Alert.AlertType.ERROR;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import msoe.se2800_2ndGroup.Data.Manipulators;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.Model;

import java.io.IOException;

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
 * * Removed references to Model.java from App.java as it is now a utility class by Grant Fass on Thu, 22 Apr 2021
 *
 * @since : Saturday, 20 March 2021
 * @author : Grant
 *
 * Copyright (C): TBD
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
     * method to update the search bar with the last course code clicked on between stages when this one is loaded
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    private void updateSearchBar() {
        mainSearchBar.setText(Manipulators.standardizeCourse(lastCourseCode));
    }


    /**
     * This method always runs on the FX thread
     * This method first will get the course code and standardize it from the text field search bar
     * This method will then get the prerequisite graph for the course and output it to the text area
     * TODO: change this to use a canvas instead or something in the future
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
                final var text = graph + "\n\n"
                                 + "A course depends on the indented courses underneath it.\n"
                                 + "All & courses must be taken to take the parent course.\n"
                                 + "Only one | course must be taken to take the parent course.\n"
                                 + "There is nothing special about + courses.";
                mainTextArea.setText(text);
            } catch (CustomExceptions.InvalidInputException e) {
                displayAlert(ERROR, "Unknown course", "Unknown course", "There is no course for this code");
            }
        });
    }

    /**
     * Method used to switch what controller and FXML resource is displayed in the GUI
     *
     * Method switches the displayed controller and FXML by calling App.setRoot with the associated FXML resource of the window to switch to.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    @FXML
    private void switchToPrimary() throws IOException {
        super.switchTo("primary");
    }

}