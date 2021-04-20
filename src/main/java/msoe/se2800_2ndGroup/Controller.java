package msoe.se2800_2ndGroup;

import javafx.fxml.FXML;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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
    /*
     * the main stage of the program
     */
    private Stage stage;

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
     * this method links the stage to use for all of the controllers
     *
     * This method is called from App.java on startup.
     * It is used to ensure that all of the controllers share the same stage.
     *
     * @param stage the stage to be linked
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * This method is used to get the location of a directory by using a DirectoryChooser
     *
     * This method opens up a directory chooser on the stored stage with the specified title at a default location based
     * on the user's OS.
     * @param directoryChooserTitle the title of the window to open
     * @return the File location of the directory
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    private File getDirectoryLocation(String directoryChooserTitle) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(directoryChooserTitle);
        directoryChooser.setInitialDirectory(new File(getInitialDirectoryBasedOnOS()));
        return directoryChooser.showDialog(stage);

    }

    /**
     * This method is used to get the location of a file by using a FileChooser
     *
     * This method is used to query the user for file locations by using a FileChooser that is opened from the main stage.
     * The file chooser uses the passed in extension filters in order to determine what files to accept
     * The file chooser also sets the initial directory based on the OS of the user
     * @param fileChooserTitle the title to use in the FileChooser popup
     * @param extensionFilterDescription the description for the file chooser extension filter to use. EX: "TXT"
     * @param extensionFilterExtension the actual extension to filter files for. EX: ".txt"
     * @return a File location of the selected file
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    private File getFileLocation(String fileChooserTitle, String extensionFilterDescription, String extensionFilterExtension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(extensionFilterDescription, extensionFilterExtension.toLowerCase()));
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.setInitialDirectory(new File(getInitialDirectoryBasedOnOS()));
        return fileChooser.showOpenDialog(stage);
    }

    /**
     * This method is used to get the default file location
     *
     * This method calculates the default file location based on what OS the system is currently using

     * Sources:
     *  <a href="#{@link}">{@link "https://mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/"}</a> Help detecting OS
     *
     * @return a string containing the initial directory
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    private String getInitialDirectoryBasedOnOS() {
        String out;
        String OS = System.getProperty("os.name").toLowerCase();
        if (OS.contains("win")) {
            out = "C:\\\\users\\\\" + System.getProperty("user.name") + "\\\\Documents";
        } else if (OS.contains("mac")) {
            out = "MAC NOT SUPPORTED YET";
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            out = System.getProperty("user.home");
        } else if (OS.contains("sunos")) {
            out = "SOLARIS NOT SUPPORTED";
        } else {
            out = "NOT SUPPORTED";
        }
        return out;
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
