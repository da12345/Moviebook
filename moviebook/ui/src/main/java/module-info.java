module moviebook.ui {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires moviebook.core;
    requires moviebook.persistence;

    opens ui to javafx.graphics, javafx.fxml;
}
