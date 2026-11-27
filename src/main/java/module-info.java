module org.calculadoraII {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires org.junit.jupiter.api;
    opens org.calculadoraII to javafx.fxml, org.junit.platform.commons;
    exports org.calculadoraII;
}
