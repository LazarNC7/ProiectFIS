package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private JFXButton exitButton;

    @FXML
    private AnchorPane mainpane;

    @FXML
    private AnchorPane paneleft;

    @FXML
    private AnchorPane paneright;

    private Stage stage;

    @FXML
    void closeWindow(MouseEvent event) {
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private double x=0,y=0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainpane.setBackground(Background.EMPTY);
        exitButton.setRipplerFill(Color.TRANSPARENT);

        paneright.setOnMousePressed(mouseEvent -> {
            x= mouseEvent.getSceneX();
            y= mouseEvent.getSceneY();
        });

        paneleft.setOnMousePressed(mouseEvent -> {
            x= mouseEvent.getSceneX();
            y= mouseEvent.getSceneY();
        });

        mainpane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });
    }
}
