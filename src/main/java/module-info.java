module com.example.lab5 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;

    opens com.example.lab5 to javafx.fxml;
    exports com.example.lab5;
}