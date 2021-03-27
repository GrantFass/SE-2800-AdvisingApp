package msoe.se2800_2ndGroup;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: Controller
 * Description:
 * * The abstract class to be inherited by all of the controllers for the different windows.
 * The Controller class is responsible for:
 * * <...>
 * * <...>
 * * <...>
 * * <...>
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added methods to link models and switch windows by Grant on Saturday, 20 March 2021
 *
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Saturday, 20 March 2021
 */
public abstract class Controller {

    /*
     * Model used for execution of tasks
     */
    private Model model;

    /**
     * This method links the model between the CLI and GUI
     *
     * This method is called from App.java on startup.
     * It is used to ensure that all controllers and the CLI share the same model.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/49676738"}</a> Help setting up CLI
     *
     * @param model the model to be linked
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void setModel(Model model) {
        this.model = model;
    }

    /**
     * TODO: Clean this up later when FXML is updated. Want to better use superclass
     * Method used to switch what controller and FXML resource is displayed in the GUI
     *
     * Method switches the displayed controller and FXML by calling App.setRoot with the associated FXML resource of the window to switch to.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *
     * @param fxml the fxml resource to switch to, excludes the .fxml from the name.
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void switchTo(String fxml) throws IOException {
        App.setRoot(fxml);
    }

}
