package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class ClientController {

    @FXML
    private AnchorPane anchorVisible1;

    @FXML
    private JFXButton bookMovieButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<?, ?> cardNumber;

    @FXML
    private JFXButton closeButton;

    @FXML
    private TableColumn<?, ?> dateT;

    @FXML
    private TableColumn<?, ?> email;

    @FXML
    private TableColumn<?, ?> fName;

    @FXML
    private TableColumn<?, ?> finish;

    @FXML
    private TableColumn<?, ?> lName;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<?, ?> phoneNumber;

    @FXML
    private JFXButton profileButton;

    @FXML
    private TableColumn<?, ?> roomT;

    @FXML
    private AnchorPane seatPane;

    @FXML
    private JFXButton seeMoviesButton;

    @FXML
    private TableColumn<?, ?> startT;

    @FXML
    private TableView<?> tabel;

    @FXML
    private TableView<?> tableMovies;

    @FXML
    private TableView<?> tableUserInfo;

    @FXML
    private TableColumn<?, ?> title;

    @FXML
    private TableColumn<?, ?> username;

    @FXML
    void closeWindow(ActionEvent event) {

    }

    @FXML
    void showMovies(ActionEvent event) {

    }

    @FXML
    void showProfile(ActionEvent event) {

    }

    @FXML
    void showSeatChoice(ActionEvent event) {

    }

}
