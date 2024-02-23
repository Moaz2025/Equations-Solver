module com.example.numerical {
    requires javafx.controls;
    requires javafx.fxml;
    requires exp4j;


    opens com.example.numerical to javafx.fxml;
    exports com.example.numerical;
}