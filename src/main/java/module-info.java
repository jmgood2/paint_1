module com.example.paint_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires javafx.graphics;
    requires java.desktop;

    opens com.example.paint_1 to javafx.fxml;
    exports com.example.paint_1;
}