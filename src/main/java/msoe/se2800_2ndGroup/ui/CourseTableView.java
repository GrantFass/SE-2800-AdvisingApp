package msoe.se2800_2ndGroup.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import msoe.se2800_2ndGroup.models.Course;

/*
 * Project Authors: Fass, Grant; Poptile, Claudia; Toohill, Teresa; Turcin, Hunter;
 * Class: SE 2800 041
 * Group: 2
 * Term: Spring 2020 - 2021
 * Instructor: Dr. Magaña
 * Affiliation: Milwaukee School of Engineering (MSOE)
 * Project Name: Advising App
 * Class Name: CourseTableView
 * Creation Date: Tuesday, 27 April 2021
 * Original Author: Hunter Turcin
 * Description: A JavaFX TableView for displaying courses.
 * The CourseTableView class is responsible for:
 *     - mapping a collection of courses to the format needed for a TableView
 * Modification Log:
 *     - File Created by Hunter Turcin on 2021-04-27
 * Copyright (C): 2021
 *
 * @author : Hunter Turcin
 * @since : Tue, 27 Apr 2021
 */
public class CourseTableView extends TableView<Course> {
    /**
     * Create a new course table view with no courses yet.
     *
     * @author : Hunter Turcin
     * @since : Tue, 27 Apr 2021
     */
    public CourseTableView() {
        super();
        setupColumns();
    }

    /**
     * Create a new course table view with the specified courses.
     *
     * @param items courses to display
     * @author : Hunter Turcin
     * @since : Tue, 27 Apr 2021
     */
    public CourseTableView(ObservableList<Course> items) {
        this();
        setItems(items);
    }

    /**
     * Setup the columns for the course table view.
     *
     * This allows Course to be displayed as a TableView despite not being made
     * using JavaFX properties.
     *
     * @author : Hunter Turcin
     * @since : Tue, 27 Apr 2021
     */
    private void setupColumns() {
        final var checkColumn = new TableColumn<Course, Boolean>("✔");
        final var codeColumn = new TableColumn<Course, String>("Code");
        final var creditsColumn = new TableColumn<Course, Integer>("Credits");
        final var prerequisiteColumn = new TableColumn<Course, String>("Prerequisite");
        final var descriptionColumn = new TableColumn<Course, String>("Description");

        checkColumn.setCellValueFactory(
                cell -> new SimpleBooleanProperty(false));
        checkColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkColumn));
        codeColumn.setCellValueFactory(
                cell -> new ReadOnlyObjectWrapper<>(cell.getValue().code()));
        creditsColumn.setCellValueFactory(
                cell -> new ReadOnlyObjectWrapper<>(cell.getValue().credits()));
        prerequisiteColumn.setCellValueFactory(
                cell -> new ReadOnlyObjectWrapper<>(cell.getValue().prerequisite().toString()));
        descriptionColumn.setCellValueFactory(
                cell -> new ReadOnlyObjectWrapper<>(cell.getValue().description()));

        setColumnResizePolicy(CONSTRAINED_RESIZE_POLICY);
        setEditable(true); // only allows checking, not editing course

        // There doesn't seem to be a "fit to content" option for a column.
        checkColumn.setMaxWidth(30.0);
        checkColumn.setMinWidth(30.0);
        codeColumn.setMaxWidth(60.0);
        codeColumn.setMinWidth(60.0);
        creditsColumn.setMaxWidth(60.0);
        creditsColumn.setMinWidth(60.0);

        getColumns().setAll(checkColumn, codeColumn, creditsColumn, prerequisiteColumn, descriptionColumn);
    }
}
