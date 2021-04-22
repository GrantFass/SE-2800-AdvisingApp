package msoe.se2800_2ndGroup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import msoe.se2800_2ndGroup.Data.Model;
import msoe.se2800_2ndGroup.UI.CLI;
import msoe.se2800_2ndGroup.UI.PrimaryController;
import msoe.se2800_2ndGroup.UI.SecondaryController;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: App
 * Description:
 * * This is the Main class for the program that launches everything.
 * * This class starts up a CLI and a GUI at the same time
 * * This class also allows the GUI window to be switched dynamically
 * * This class uses JavaFX through Maven
 * * NOTE: if the package is refactored it must be manually changed in module-info.java
 * The App class is responsible for:
 * * Launching the main GUI window for the program on startup
 * * Launching the thread for the CLI on startup
 * * Launching the program
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added CLI setup by Grant on Saturday, 20 March 2021
 * * Implement Logger by Grant Fass on Thu, 15 Apr 2021
 * * Make Controllers static by Grant Fass on Wed, 21 Apr 2021
 * * Code cleanup by Hunter T on Tue, 20 Apr 2021
 * @since : Saturday, 20 March 2021
 * @author : Grant
 *
 * Copyright (C): TBD
 */
public class App extends Application {
    /**
     * Logging system.
     */
    private static final Logger LOGGER = AdvisingLogger.getLogger();

    /**
     * Scene linked to the Stage during startup for displaying content and switching windows dynamically
     */
    public static Scene scene;

    /*
     * The model linked to the GUI and CLI to make sure they both use the same information
     */
    private static final Model model = new Model();

    /*
     * The primary window controller. controller for the data manipulation window
     */
    private static final PrimaryController primaryController = new PrimaryController();

    /*
     * The secondary window controller. controller for the graph manipulation window
     */
    private static final SecondaryController secondaryController = new SecondaryController();

    /**
     * This method starts the FX program window and the program GUI
     *
     * This method operates by creating controllers and linking the models to them.
     * It then creates the initial scene and the primary CLI thread.
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/49676738"}</a> Help setting up CLI
     *  <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *
     * @param stage the stage displayed
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    @Override
    public void start(Stage stage) throws IOException {
        //Link models to controllers
        LOGGER.finer("Linking model to primaryController");
        primaryController.setStage(stage);
        LOGGER.finer("Linking model to secondaryController");
        secondaryController.setStage(stage);
        //Link FXML to scene
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("SE2800 Advising App: Group 2");
        stage.show();
        LOGGER.finer("Showing Stage");

        //Add CLI
        CLI cli = new CLI(model);
        LOGGER.finer("Linking model to CLI");
        Thread cliThread = new Thread(cli::processCommandLine);
        cliThread.setDaemon(true);
        cliThread.start();
    }

    /**
     * method to return the model to anywhere else in the program
     *
     * Used to sync up the same model to the CLI and all controllers.
     * Have to do it this way since FXML controllers can not have constructors and since passing values set them to null.
     * @return the model for the program
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    public static Model getModel() {
        return model;
    }

    /**
     * This method switches the displayed FXML page
     *
     * This method operates by switching the scene root to a different parent.
     * This is called from the controllers themselves
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *
     * @param fxml the name of the resource excluding .fxml
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static void setRoot(String fxml) throws IOException {
        LOGGER.finer("setting scene root");
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * This method loads the given FXML resource to return the associated Parent
     *
     * This method operates by loading the given FXML resource. It then creates and returns the associated Parent
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *
     * @param fxml the name of the resource excluding .fxml
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @return the Parent associated with the input fxml resouce name
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        LOGGER.log(Level.FINE, "Loading FXML", fxmlLoader);
        return fxmlLoader.load();
    }

    /**
     * the main method of the program
     *
     * calls the launch() method from FXML
     *
     * @param args the argument passed in for mains
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public static void main(String[] args) {
        AdvisingLogger.setupLogger();
        LOGGER.info("Launching Program");
        launch();
    }

}