module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires org.junit.jupiter.api;
    opens org.example to javafx.fxml, org.junit.platform.commons;
    exports org.example;
}
