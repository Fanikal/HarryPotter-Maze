module com.example.harrypottermaze {
    requires javafx.controls;
    requires javafx.fxml;
    requires sphinx4.core;


    opens com.example.harrypottermaze to javafx.fxml;
    exports com.example.harrypottermaze;
}