package msoe.se2800_2ndGroup;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: PrimaryController
 * Description:
 * * This is the controller for the primary window in the program
 * * This window is the first one launched on startup
 * The PrimaryController class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 *
 * @since : Saturday, 20 March 2021
 * @author : Grant
 *
 * Copyright (C): TBD
 */
public class PrimaryController extends Controller {

    //region FXML vars
    @FXML
    Label mainLabel;
    @FXML
    ListView<String> mainListView;
    //endregion


    public PrimaryController() {
        super();
    }


    //region FXML methods for Data Manipulation menu

    /**
     * method to generate the course offerings
     *
     * This method is always run on the FX thread and uses commands from Model.java
     * This method first creates a String array by getting the course offerings as string and splitting by '\n'
     * Then this method adds all the offering items in the array to the list view
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    public void viewCourseOfferings() {
        App.getModel().ensureFXThread(() -> {
            try {
                String[] offerings = App.getModel().getCourseOfferingsAsString(fallTermSelection.isSelected(),
                        winterTermSelection.isSelected(), springTermSelection.isSelected()).split("\n");
                ObservableList<String> items = FXCollections.observableArrayList();
                items.addAll(Arrays.asList(offerings));
                mainListView.setItems(items);
                mainLabel.setText(String.format("Course offerings for: %s%s%s",
                        fallTermSelection.isSelected() ? "Fall, " : "",
                        winterTermSelection.isSelected() ? "Winter, " : "",
                        springTermSelection.isSelected() ? "Spring, " : ""));
            } catch (Model.InvalidInputException e) {
                String message = String.format(" Invalid Input Exception occurred while " +
                        "generating course offerings\n%s", e.getMessage());
                displayAlert(Alert.AlertType.ERROR, "InvalidInputException", "Exception", message);
                AdvisingLogger.getLogger().warning(message + Arrays.toString(e.getStackTrace()));
            }
        });
    }

    /**
     * method to generate the course recommendations
     *
     * This method is always run on the FX thread and uses commands from Model.java
     * This method first creates a String array by getting the course recommendations as string and splitting by '\n'
     * Then this method adds all the recommended items in the array to the list view
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    public void getCourseRecommendations() {
        App.getModel().ensureFXThread(() -> {
            try {
                String[] recommendations = App.getModel().getCourseRecommendation(fallTermSelection.isSelected(),
                        winterTermSelection.isSelected(), springTermSelection.isSelected()).split("\n");
                ObservableList<String> items = FXCollections.observableArrayList();
                items.addAll(Arrays.asList(recommendations));
                mainListView.setItems(items);
                mainLabel.setText(String.format("Course recommendations for: %s%s%s",
                        fallTermSelection.isSelected() ? "Fall, " : "",
                        winterTermSelection.isSelected() ? "Winter, " : "",
                        springTermSelection.isSelected() ? "Spring, " : ""));
            } catch (Model.InvalidInputException e) {
                String message = String.format(" Invalid Input Exception occurred while " +
                        "generating course recommendations\n%s", e.getMessage());
                displayAlert(Alert.AlertType.ERROR, "InvalidInputException", "Exception", message);
                AdvisingLogger.getLogger().warning(message + Arrays.toString(e.getStackTrace()));
            }
        });
    }
    //endregion

    /**
     * TODO: Clean this up later when FXML is updated. Want to better use superclass
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
    private void switchToSecondary() throws IOException {
        super.switchTo("secondary");
    }

}
