module com.example.fis {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.jfoenix;


    opens com.example.fis to javafx.fxml;
    exports com.example.fis;
}