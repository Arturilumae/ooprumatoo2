module com.example.rumatoo2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.rumatoo2 to javafx.fxml;
    exports com.example.rumatoo2;
}