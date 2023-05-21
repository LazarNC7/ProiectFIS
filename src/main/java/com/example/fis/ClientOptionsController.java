package com.example.fis;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ClientOptionsController extends SelectMovieController{

    @FXML
    private AnchorPane pane;

    private double x=0,y=0;

    private Stage stage;



    @FXML
    private DatePicker datePicker;

    @FXML
    private ChoiceBox<Integer> oraChoice;

    @FXML
    private ChoiceBox<String> saliChoice;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.setOnMousePressed(mouseEvent -> {
            x= mouseEvent.getSceneX();
            y= mouseEvent.getSceneY();
        });



        pane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });

        LocalDate today = LocalDate.now();
        LocalDate weekLater = today.plusDays(7);
        datePicker.setValue(today);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(today) || date.isAfter(weekLater));
            }
        });

        for (int i = 10; i <= 22; i++) {

            oraChoice.getItems().add(i);
        }

        saliChoice.getItems().addAll("2D", "3D", "IMAX", "4D");

    }




    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public void goToShowMovies(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("showMovie.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ShowMovieController controller = fxmlLoader.getController();


            stage.setScene(scene);
            controller.setStage(stage);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
