module com.example.rumatoo2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.rumatoo2 to javafx.fxml;
    exports com.example.rumatoo2;
}