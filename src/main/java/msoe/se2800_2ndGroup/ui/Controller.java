package msoe.se2800_2ndGroup.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.ToggleGroup;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import msoe.se2800_2ndGroup.App;
import msoe.se2800_2ndGroup.Data.Data;
import msoe.se2800_2ndGroup.Exceptions.CustomExceptions;
import msoe.se2800_2ndGroup.FileIO.FileIO;
import msoe.se2800_2ndGroup.Model;
import msoe.se2800_2ndGroup.logger.AdvisingLogger;

import java.io.File;
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
 * Class Name: Controller
 * Description:
 * * The abstract class to be inherited by all of the controllers for the different windows.
 * The Controller class is responsible for:
 * * defining and implementing universal actions that all controllers share
 * Modification Log:
 * * File Created by Grant on Saturday, 20 March 2021
 * * Added methods to link models and switch windows by Grant on Saturday, 20 March 2021
 * * Added methods to get file locations for GUI by Grant Fass on Mon, 19 Apr 2021
 * * Added FXML menu option methods by Grant Fass on Mon, 19 Apr 2021
 * * Removed references to Model.java from App.java as it is now a utility class by Grant Fass on
 * Thu, 22 Apr 2021
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Saturday, 20 March 2021
 */
public abstract class Controller {

    /*
     * The course code as a string of the last course clicked
     */
    public static String lastCourseCode = "";
    @FXML
    public CheckMenuItem fallTermSelection;
    @FXML
    public CheckMenuItem winterTermSelection;
    @FXML
    public CheckMenuItem springTermSelection;
    //endregion
    //region FXML vars
    @FXML
    private ToggleGroup majorToggleGroup;
    //region private vars
    /*
     * the main stage of the program
     */
    private Stage stage;
    //endregion

    //region methods to be called from App.java on startup to set local vars

    /**
     * display an alert with the specified format and values
     *
     * @param alertType the type of alert to display
     * @param title     the title of the alert
     * @param header    the header text to display in the alert
     * @param content   the content text to display in the alert
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    public static void displayAlert(Alert.AlertType alertType, String title, String header,
                                    String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    //endregion

    //region file and directory location methods

    /**
     * this method links the stage to use for all of the controllers
     * <p>
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
     * <p>
     * This method opens up a directory chooser on the stored stage with the specified title at a
     * default location based
     * on the user's OS.
     *
     * @param directoryChooserTitle the title of the window to open
     * @return the File location of the directory
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    protected File getDirectoryLocation(String directoryChooserTitle) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(directoryChooserTitle);
        directoryChooser.setInitialDirectory(new File(getInitialDirectoryBasedOnOS()));
        File out = directoryChooser.showDialog(stage);
        return out == null ? new File("") : out;
    }

    /**
     * This method is used to get the location of a file by using a FileChooser
     * <p>
     * This method is used to query the user for file locations by using a FileChooser that is
     * opened from the main stage.
     * The file chooser uses the passed in extension filters in order to determine what files to
     * accept
     * The file chooser also sets the initial directory based on the OS of the user
     *
     * @param fileChooserTitle           the title to use in the FileChooser popup
     * @param extensionFilterDescription the description for the file chooser extension filter to
     *                                  use. EX: "TXT"
     * @param extensionFilterExtension   the actual extension to filter files for. EX: ".txt"
     * @return a File location of the selected file
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    protected File getFileLocation(String fileChooserTitle, String extensionFilterDescription,
                                   String extensionFilterExtension) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters()
                .addAll(new FileChooser.ExtensionFilter(extensionFilterDescription, "*" +
                                                                                    extensionFilterExtension
                                                                                            .toLowerCase()),
                        new FileChooser.ExtensionFilter("ALL FILES", "*.*"));
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.setInitialDirectory(new File(getInitialDirectoryBasedOnOS()));
        File out = fileChooser.showOpenDialog(stage);
        return out == null ? new File("") : out;
    }
    //endregion

    /**
     * This method is used to get the default file location
     * <p>
     * This method calculates the default file location based on what OS the system is currently
     * using
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://mkyong.com/java/how-to-detect-os-in-java
     * -systemgetpropertyosname/"}</a> Help detecting OS
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
    //endregion

    //region FXML methods for FILE menu

    //region Help Menu methods
    @FXML
    private void displayHelp() {
        String message = """
                         1.) To load a transcript, go to File -> Load Transcript.
                         Choose the PDF file from the File Chooser window.
                         2.) To save a course data files, go to File -> Load Course Data.
                         Choose the CSV file from the File Chooser window.
                         3.) To save a copy of the transcript, got to File -> Store Transcript.
                         Chose a folder to store the filer in in the File Chooser window.
                         4.) To select your major, go to Major Selection -> Choose Between Software Engineering
                         and Computer Science.
                         5.) To choose a term, go to Term Selection -> Select Either Fall, Winter, or Sprint.
                         6.) To view course offerings, go to Data Manipulation -> View Course Offerings.
                         7.) To get course recommendations, go to Data Manipulation -> View Course Recommendations.
                         8.) To enter course code and view graph version of the course offerings,
                         go to Switch to Graph Window.
                         9.) To exit view graph version of the course offerings, go to Switch to Data Window.
                         10.) To exit the application, go to File -> Exit.
                         """;
        displayAlert(Alert.AlertType.INFORMATION, "Program Help", null, message);
        AdvisingLogger.getLogger().info("displaying help alert");
    }

    /**
     * method used to shut down and exit the program
     * <p>
     * This method is always run on the FX thread and uses the exit program method from model
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    private void close() {
        Model.ensureFXThread(Model::exitProgram);
    }

    /**
     * method used to load transcripts
     * <p>
     * This method is always run on the FX thread and uses commands from Model.java
     * This method will query the user for a file location using a FileChooser
     * This method will then validate the file location
     * This method will display an alert if file validation fails
     * This method will load transcript pdf file from the specified location
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    private void loadTranscript() {
        File transcriptLocation = getFileLocation("Select Transcript.PDF", "PDF", ".pdf");
        boolean passedValidation =
                FileIO.validateFileLocation(transcriptLocation.getAbsolutePath(), ".pdf");
        if (!transcriptLocation.toString().equals("")) {
            if (passedValidation) {
                Model.ensureFXThread(() -> {
                    try {
                        Model.loadUnofficialTranscript(transcriptLocation);
                        displayAlert(Alert.AlertType.INFORMATION, "Success", "File Load",
                                     "Successfully read in file from: " + transcriptLocation);
                        AdvisingLogger.getLogger()
                                .info("Successfully read in file from: " + transcriptLocation +
                                      "\n");
                    } catch (IOException e) {
                        displayAlert(Alert.AlertType.ERROR, "IOException", "Exception",
                                     "IOException occurred reading in file from: " +
                                     transcriptLocation);
                        AdvisingLogger.getLogger().warning(
                                "IOException occurred reading in file from: " + transcriptLocation +
                                "\n" + Arrays.toString(e.getStackTrace()));
                    }
                });
            } else {
                displayAlert(Alert.AlertType.WARNING, "Warning", "Failed Validation",
                             "File " + transcriptLocation + " failed validation");
                AdvisingLogger.getLogger()
                        .warning("File " + transcriptLocation + " failed validation");
            }
        }
    }

    /**
     * method used to store transcripts
     * <p>
     * This method is always run on the FX thread and uses commands from Model.java
     * This method will query the user for a file location using a DirectoryChooser
     * This method will then store the transcript to the specified directory
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    private void storeTranscript() {
        File outputLocation = getDirectoryLocation("Select Location to Store Transcript.PDF");
        if (!outputLocation.toString().equals("")) {
            Model.ensureFXThread(() -> {
                try {
                    Model.storeUnofficialTranscript(outputLocation.getAbsolutePath());
                    displayAlert(Alert.AlertType.INFORMATION, "Success", "File Write",
                                 "Successfully wrote file to: " + outputLocation);
                    AdvisingLogger.getLogger()
                            .info("Successfully wrote file to: " + outputLocation + "\n");
                } catch (IOException e) {
                    displayAlert(Alert.AlertType.ERROR, "IOException", "Exception",
                                 "IOException occurred writing file to: " + outputLocation);
                    AdvisingLogger.getLogger().warning(
                            "IOException occurred reading writing file to: " + outputLocation +
                            "\n" + Arrays.toString(e.getStackTrace()));
                }
            });
        }
    }
    //endregion

    //region FXML methods for Major Selection menu

    /**
     * method used to load course data
     * <p>
     * This method is always run on the FX thread and uses commands from Model.java
     * This method will query the user for a file location using a FileChooser
     * This method will then validate the file location
     * This method will display an alert if file validation fails
     * This method will load course data csv files from the specified location
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    private void loadCourseData() {
        File curriculumLocation = getFileLocation("Select Curriculum.CSV", "CSV", ".csv");
        File offeringsLocation = getFileLocation("Select Offerings.CSV", "CSV", ".csv");
        File prerequisitesLocation = getFileLocation("Select Prerequisites.CSV", "CSV", ".csv");
        boolean curriculumPassedValidation =
                FileIO.validateFileLocation(curriculumLocation.getAbsolutePath(), ".csv");
        boolean offeringsPassedValidation =
                FileIO.validateFileLocation(offeringsLocation.getAbsolutePath(), ".csv");
        boolean prerequisitesPassedValidation =
                FileIO.validateFileLocation(prerequisitesLocation.getAbsolutePath(), ".csv");
        if (!(curriculumLocation.toString().equals("") || offeringsLocation.toString().equals("") ||
              prerequisitesLocation.toString().equals(""))) {
            if (!curriculumPassedValidation || !offeringsPassedValidation ||
                !prerequisitesPassedValidation) {
                String message = String.format(
                        "One or more files has failed validation:\n%s: %s\n%s: %s\n%s: %s\n",
                        curriculumLocation, curriculumPassedValidation, offeringsLocation,
                        offeringsPassedValidation, prerequisitesLocation,
                        prerequisitesPassedValidation);
                displayAlert(Alert.AlertType.WARNING, "Warning", "Failed Validation", message);
                AdvisingLogger.getLogger().warning(message);
            } else {
                Model.ensureFXThread(() -> {
                    try {
                        String message = Model.loadCoursesFromSpecifiedLocations(
                                curriculumLocation.getAbsolutePath(),
                                offeringsLocation.getAbsolutePath(),
                                prerequisitesLocation.getAbsolutePath());

                        displayAlert(Alert.AlertType.INFORMATION, "Success", "File Load", message);
                        AdvisingLogger.getLogger().info(message);
                    } catch (IOException e) {
                        String message = String.format("""
                                                       IOException occurred reading in course data files from:
                                                       %s
                                                       %s
                                                       %s
                                                       """, curriculumLocation, offeringsLocation,
                                                       prerequisitesLocation);
                        displayAlert(Alert.AlertType.ERROR, "IOException", "Exception", message);
                        AdvisingLogger.getLogger()
                                .warning(message + Arrays.toString(e.getStackTrace()));
                    }
                });
            }
        }
    }

    //endregion

    //region GUI alert methods

    /**
     * Method used to store the major in the program using a radio toggle group in the menu bar
     * Method runs each time a radio button is selected
     *
     * @author : Grant Fass
     * @since : Mon, 19 Apr 2021
     */
    @FXML
    private void storeMajor() {
        String majorText = ((RadioMenuItem) majorToggleGroup.getSelectedToggle()).getText();
        if (majorText.startsWith("CS")) {
            Model.ensureFXThread(() -> {
                try {
                    Data.storeMajor("CS");
                } catch (CustomExceptions.InvalidInputException e) {
                    String message = String.format(
                            " Invalid Input Exception occurred while " + "storing major: %s\n%s",
                            majorText, e.getMessage());
                    displayAlert(Alert.AlertType.ERROR, "InvalidInputException", "Exception",
                                 message);
                    AdvisingLogger.getLogger()
                            .warning(message + Arrays.toString(e.getStackTrace()));
                }
            });
        } else if (majorText.startsWith("SE")) {
            Model.ensureFXThread(() -> {
                try {
                    Data.storeMajor("SE");
                } catch (CustomExceptions.InvalidInputException e) {
                    String message = String.format(
                            " Invalid Input Exception occurred while " + "storing major: %s\n%s",
                            majorText, e.getMessage());
                    displayAlert(Alert.AlertType.ERROR, "InvalidInputException", "Exception",
                                 message);
                    AdvisingLogger.getLogger()
                            .warning(message + Arrays.toString(e.getStackTrace()));
                }
            });
        }
    }

    //endregion

    //region FXML window switching methods

    /**
     * Method used to switch what controller and FXML resource is displayed in the GUI
     * <p>
     * Method switches the displayed controller and FXML by calling App.setRoot with the associated FXML resource of the window to switch to.
     * <p>
     * Sources:
     * <a href="#{@link}">{@link "https://openjfx.io/openjfx-docs/#maven"}</a> Help setting up FXML loading with Maven
     *
     * @param fxml the fxml resource to switch to, excludes the .fxml from the name.
     * @throws IOException this is thrown when there is an issue in loading the fxml resource
     * @author : Grant Fass
     * @since : Sat, 20 Mar 2021
     */
    public void switchTo(String fxml) throws IOException {
        App.setRoot(fxml);
    }
    //endregion
}
