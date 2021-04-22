module msoe.se2800_2ndGroup {
    requires javafx.controls;
    requires javafx.fxml;
    requires pdfbox;
    requires commons.csv;
    requires java.logging;

    opens msoe.se2800_2ndGroup to javafx.fxml;
    exports msoe.se2800_2ndGroup;
    exports msoe.se2800_2ndGroup.UI;
    opens msoe.se2800_2ndGroup.UI to javafx.fxml;
    exports msoe.se2800_2ndGroup.FileIO;
    opens msoe.se2800_2ndGroup.FileIO to javafx.fxml;
    exports msoe.se2800_2ndGroup.Graphing;
    opens msoe.se2800_2ndGroup.Graphing to javafx.fxml;
}