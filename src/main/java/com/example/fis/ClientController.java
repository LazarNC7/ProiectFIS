package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    @FXML
    private AnchorPane anchorVisible1;

    @FXML
    private JFXButton bookMovieButton;

    @FXML
    private BorderPane borderPane;

    @FXML
    private TableColumn<UserData, String> cardNumber;

    @FXML
    private JFXButton closeButton;

    @FXML
    private TableColumn<DataTableClient, String> dateT;

    @FXML
    private TableColumn<UserData, String> email;

    @FXML
    private TableColumn<UserData, String> fName;

    @FXML
    private TableColumn<DataTableClient, Integer> finish;

    @FXML
    private TableColumn<UserData, String> lName;

    @FXML
    private TableColumn<FilmsData, String> name;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableColumn<UserData, String> phoneNumber;

    @FXML
    private JFXButton profileButton;

    @FXML
    private TableColumn<DataTableClient, String> roomT;

    @FXML
    private AnchorPane seatPane;

    @FXML
    private JFXButton seeMoviesButton;

    @FXML
    private TableColumn<DataTableClient, Integer> startT;

    @FXML
    private TableView<DataTableClient> tabel;

    @FXML
    private TableView<?> tableMovies;

    @FXML
    private TableView<UserData> tableUserInfo;

    @FXML
    private TableColumn<DataTableClient, String> title;

    @FXML
    private TableColumn<UserData, String> username;

    @FXML
    void closeWindow(ActionEvent event) {
        stage.close();
    }

    ObservableList<DataTableClient> data = FXCollections.observableArrayList();
    private int index=0;
    @FXML
    void showMovies(){
        tabel.setVisible(true);
        anchorVisible1.setVisible(false);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            // create a new JDBC statement
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select start,stop,type_room,date,name from Reservations join Film F on Reservations.id_film = F.id_film");


            // Loop through the result set and add each row to the observable list
            while (rs.next()) {
                String name = rs.getString("name");
                int stop = rs.getInt("stop");
                int start = rs.getInt("start");
                String type_room = rs.getString("type_room");
                String date = rs.getString("date");

                data.add(new DataTableClient(name,start,stop,date,type_room));
                //System.out.println(data.get(index).getTitle()+data.get(index).getRoomT()+data.get(index).getDateT()+data.get(index).getId_user());
                index++;
            }


            // Define the columns for the TableView

            title.setCellValueFactory(new PropertyValueFactory<>("title"));
            startT.setCellValueFactory(new PropertyValueFactory<>("startT"));
            finish.setCellValueFactory(new PropertyValueFactory<>("finish"));
            roomT.setCellValueFactory(new PropertyValueFactory<>("roomT"));
            dateT.setCellValueFactory(new PropertyValueFactory<>("dateT"));
            statement.close();
            connection.close();
            tabel.setItems(data);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    ObservableList<UserData> dataProfile = FXCollections.observableArrayList();
    ObservableList<FilmsData> dataFilmsDeleted = FXCollections.observableArrayList();
    @FXML
    void showProfile(ActionEvent event) {
        tabel.setVisible(false);
        anchorVisible1.setVisible(true);
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            // create a new JDBC statement
            Statement statement = connection.createStatement();

            ResultSet rs = statement.executeQuery("SELECT first_name, last_name, e_mail, username, card_number, phone_number FROM UserInfo WHERE username = '" + User.getUsername() + "'");


            // Loop through the result set and add each row to the observable list
            while (rs.next()) {
                String fName = rs.getString("first_name");
                String lName = rs.getString("last_name");
                String email = rs.getString("e_mail");
                String username = rs.getString("username");
                String cardNumber= rs.getString("card_number");
                String phoneNumber= rs.getString("phone_number");


                dataProfile.add(new UserData(fName,lName,email,username,cardNumber,phoneNumber));


            }


            // Define the columns for the TableView


            email.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
            username.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
            cardNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCardNumber()));
            phoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
            fName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFName()));
            lName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLName()));
            tableUserInfo.setItems(dataProfile);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    void showSeatChoice(ActionEvent event) {
//        borderPane.setVisible(false);
//        seatPane.setVisible(true);
    }

    private Stage stage;
    private double x=0,y=0;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        pane.setBackground(Background.EMPTY);
        borderPane.setBackground(Background.EMPTY);
        seatPane.setBackground(Background.EMPTY);
        closeButton.setRipplerFill(Color.TRANSPARENT);
        tabel.setVisible(true);
        anchorVisible1.setVisible(false);
        showMovies();

        pane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        pane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });

}

}
