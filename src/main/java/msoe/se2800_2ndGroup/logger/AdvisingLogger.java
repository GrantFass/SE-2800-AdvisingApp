package msoe.se2800_2ndGroup.logger;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

/**
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: AdvisingApp
 * Class Name: AdvisingLogger
 * Description:
 * * <class description here>
 * The AdvisingLogger class is responsible for:
 * * Managing the universal logger for the AdvisingApp project
 * Modification Log:
 * * File Created by Grant on Thursday, 15 April 2021
 * * Implement logger and default configuration
 * <p>
 * Copyright (C): TBD
 *
 * @author : Grant
 * @since : Thursday, 15 April 2021
 */
public class AdvisingLogger {

    /*
    The logger to use. Creates a static logger with the specified name
     */
    private static final Logger LOGGER = Logger.getLogger("AdvisingApp");

    /**
     * method to properly setup the logger. Called at program startup in App.main()
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://examples.javacodegeeks.com/core-java/util/logging/java-util-logging-example/"}</a>: Help setting up the logger defaults
     *  <a href="#{@link}">{@link "https://www.journaldev.com/977/logger-in-java-logging-example"}</a>: Help adding a custom formatter for fileHandler
     *  <a href="#{@link}">{@link "https://stackoverflow.com/a/2533250"}</a>: Stop echoing to console twice
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static void setupLogger() {
        try {
            //create handlers
            Handler consoleHandler = new ConsoleHandler();
            Handler fileHandler = new FileHandler("./out/AdvisingApp.log");
            //add handlers to logger
            LOGGER.addHandler(consoleHandler);
            LOGGER.addHandler(fileHandler);
            //Setting levels to handlers and LOGGER
            fileHandler.setLevel(Level.ALL);
            consoleHandler.setLevel(Level.INFO);
            LOGGER.setLevel(Level.ALL);
            //set format
            consoleHandler.setFormatter(new MyFormatter());
            fileHandler.setFormatter(new MyFormatter());
            //stop fileHandler echoing to console as well
            LOGGER.setUseParentHandlers(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used to access the same logger from anywhere
     * @return the logger object
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static Logger getLogger() {
        return LOGGER;
    }

    /**
     * A formatter class used as a custom format for the File Handler
     *
     * Sources:
     *  <a href="#{@link}">{@link "https://www.journaldev.com/977/logger-in-java-logging-example"}</a>: Help adding a custom formatter for fileHandler
     *
     * @author : Grant Fass
     * @since : Thu, 15 Apr 2021
     */
    public static class MyFormatter extends Formatter {

        @Override
        public String format(LogRecord record) {
            return String.format("[%s]-(%tc): %s.%s(...) | \"%s\"\n", record.getLevel(), new Date(record.getMillis()), record.getSourceClassName(), record.getSourceMethodName(), record.getMessage());
        }
    }

}
