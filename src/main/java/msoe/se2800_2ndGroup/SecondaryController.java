package msoe.se2800_2ndGroup;

import java.io.IOException;
import javafx.fxml.FXML;

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
public class SecondaryController extends Controller {

    public SecondaryController() {
        super();
    }


//    @FXML
//    public void viewPrerequisiteGraph() {
//        //TODO: should display in secondary window
//    }

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