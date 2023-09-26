module com.example.harrypottermaze {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.harrypottermaze to javafx.fxml;
    exports com.example.harrypottermaze;
}