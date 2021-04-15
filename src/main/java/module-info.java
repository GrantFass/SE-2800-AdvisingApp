module msoe.se2800_2ndGroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires pdfbox;
    requires commons.csv;
    requires java.logging;

    opens msoe.se2800_2ndGroup to javafx.fxml;
    exports msoe.se2800_2ndGroup;
}