package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ResourceBundle;

public class SignupController implements Initializable {

    @FXML
    private AnchorPane bottomPane;

    @FXML
    private TextField cardNumber;

    @FXML
    private JFXButton close;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField password;

    @FXML
    private TextField phoneNumber;

    @FXML
    private JFXButton signup;

    @FXML
    private AnchorPane topPane;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane mainpane;

    private Stage stage;
    private double x=0,y=0;
    @FXML
    void closeWindow(MouseEvent event) {
        stage.close();
    }

    @FXML
    void signupMethod(ActionEvent event) {

    }


    public void setStage(Stage stage) {
        this.stage=stage;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mainpane.setBackground(Background.EMPTY);
        close.setRipplerFill(Color.TRANSPARENT);

        topPane.setOnMousePressed(mouseEvent -> {
            x= mouseEvent.getSceneX();
            y= mouseEvent.getSceneY();
        });

        bottomPane.setOnMousePressed(mouseEvent -> {
            x= mouseEvent.getSceneX();
            y= mouseEvent.getSceneY();
        });

        mainpane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });
    }
}