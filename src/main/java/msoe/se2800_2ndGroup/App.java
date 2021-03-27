package msoe.se2800_2ndGroup;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
 *
 * @since : Saturday, 20 March 2021
 * @author : Grant
 *
 * Copyright (C): TBD
 */
public class App extends Application {

    /**
     * Scene linked to the Stage during startup for displaying content and switching windows dynamically
     */
    public static Scene scene;

    /*
     * The model linked to the GUI and CLI to make sure they both use the same information
     */
    private static final Model model = new Model();

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
        PrimaryController primaryController = new PrimaryController();
        primaryController.setModel(model);
        SecondaryController secondaryController = new SecondaryController();
        secondaryController.setModel(model);

        //Link FXML to scene
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();

        //Add CLI
        CLI cli = new CLI(model);
        Thread cliThread = new Thread(cli::processCommandLine);
        cliThread.setDaemon(true);
        cliThread.start();
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
    static void setRoot(String fxml) throws IOException {
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
        launch();
    }

}