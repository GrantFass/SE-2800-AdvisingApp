package msoe.se2800_2ndGroup.ui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.Model;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;
import msoe.se2800_2ndGroup.models.Course;
import msoe.se2800_2ndGroup.models.Elective;
import msoe.se2800_2ndGroup.models.NullPrerequisite;

import java.io.IOException;
import java.util.Arrays;

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
 * * Controlling functions exclusive to the Primary data manipulation window
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added methods to manipulate data by Grant Fass on Wed, 21 Apr 2021
 * * Removed references to Model.java from App.java as it is now a utility class by Grant Fass on Thu, 22 Apr 2021
 * * Use CourseTableView instead of ListView to allow checking by Hunter Turcin on Tue, 27 Apr 2021
 *
 * @author : Grant
 * <p>
 * Copyright (C): TBD
 * @since : Saturday, 20 March 2021
 */
public class PrimaryController extends Controller {

    //region FXML vars
    @FXML
    Label mainLabel;
    @FXML
    CourseTableView courseTableView;
    //endregion


    public PrimaryController() {
        super();
    }


    //region FXML methods for Data Manipulation menu

    /**
     * Display course offerings.
     *
     * This method runs on the FX thread.
     *
     * @author : Grant Fass, Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    public void viewCourseOfferings() {
        Model.ensureFXThread(() -> {
            try {
                final var fall = fallTermSelection.isSelected();
                final var winter = winterTermSelection.isSelected();
                final var spring = springTermSelection.isSelected();
                final var offerings = Model.getCourseOfferings(fall, winter, spring);
                final var courses = FXCollections.<Course>observableArrayList();

                for (final var offering : offerings) {
                    courses.add(offering.course());
                }

                courseTableView.setItems(courses);
                courseTableView.getSelectionModel().selectedItemProperty().addListener(getCourseListener());
            } catch (CustomExceptions.InvalidInputException e) {
                String message = String.format(" Invalid Input Exception occurred while " +
                        "generating course offerings\n%s", e.getMessage());
                displayAlert(Alert.AlertType.ERROR, "InvalidInputException", "Exception", message);
                AdvisingLogger.getLogger().warning(message + Arrays.toString(e.getStackTrace()));
            }
        });
    }

    /**
     * Display course recommendations.
     *
     * This method runs on the FX thread.
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    public void getCourseRecommendations() {
        Model.ensureFXThread(() -> {
            try {
                final var fall = fallTermSelection.isSelected();
                final var winter = winterTermSelection.isSelected();
                final var spring = springTermSelection.isSelected();
                final var recommendations = Model.getCourseRecommendation(fall, winter, spring);
                final var courses = FXCollections.<Course>observableArrayList();

                for (final var recommendation : recommendations) {
                    if (recommendation instanceof final Course course) {
                        courses.add(course);
                    } else if (recommendation instanceof final Elective elective) {
                        final var course = new Course(elective.getCode(), 0, new NullPrerequisite(), "Elective");
                        courses.add(course);
                    }
                }

                courseTableView.setItems(courses);
                courseTableView.getSelectionModel().selectedItemProperty().addListener(getCourseListener());
            } catch (CustomExceptions.InvalidInputException e) {
                String message = String.format(" Invalid Input Exception occurred while " +
                        "generating course recommendations\n%s", e.getMessage());
                displayAlert(Alert.AlertType.ERROR, "InvalidInputException", "Exception", message);
                AdvisingLogger.getLogger().warning(message + Arrays.toString(e.getStackTrace()));
            }
        });
    }

    /**
     * Method to generate and get the listener to get course codes from the List View
     *
     * @return new ChangeListener that is formatted correctly
     * @author : Grant Fass, Hunter Turcin
     * @since : Mon, 19 Apr 2021
     */
    private ChangeListener<Course> getCourseListener() {
        return new ChangeListener<>() {
            final int listViewStartSize = courseTableView.getItems().size();

            @Override
            public void changed(ObservableValue<? extends Course> observableValue, Course oldValue, Course newValue) {
                if (listViewStartSize != courseTableView.getItems().size()) {
                    courseTableView.getSelectionModel().selectedItemProperty().removeListener(this);
                } else if (newValue != null) {
                    lastCourseCode = courseTableView.getSelectionModel().getSelectedItem().code();
                }
            }
        };
    }
    //endregion

    /**
     * TODO: Clean this up later when FXML is updated. Want to better use superclass
     * Method used to switch what controller and FXML resource is displayed in the GUI
     * <p>
     * Method switches the displayed controller and FXML by calling App.setRoot with the associated FXML resource of the window to switch to.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
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
