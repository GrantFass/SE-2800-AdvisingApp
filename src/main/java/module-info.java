module msoe.se2800_2ndGroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;

    opens msoe.se2800_2ndGroup to javafx.fxml;
    exports msoe.se2800_2ndGroup;
}