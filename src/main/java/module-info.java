module org.example.finalbank {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.jfoenix;
    requires java.sql;

    opens org.example.finalbank to javafx.fxml;
    exports org.example.finalbank;
}