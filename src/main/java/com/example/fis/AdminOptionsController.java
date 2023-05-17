package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;

import static javafx.application.Application.launch;


public class AdminOptionsController implements Initializable {
    public JFXButton editScreening;
    public JFXButton deleteMovieButton;
    @FXML
    private AnchorPane anchorVisible;

    @FXML
    private JFXButton applyAdd;


    @FXML
    private DatePicker dateAdd;


    @FXML
    private TextField genreAdd;

    @FXML
    private TextField lengthAdd;

    @FXML
    private TextField nameAdd;

    @FXML
    private ComboBox<String> roomAdd;

    @FXML
    private ComboBox<Integer> startAdd;

    @FXML
    private ComboBox<Integer> stopAdd;


    @FXML
    void showAdd(ActionEvent event) {
        tabel.setVisible(false);
        anchorVisible.setVisible(true);
    }

    @FXML
    private Text fillFields;

    @FXML
    private JFXButton addButton;



    @FXML
    private TableColumn<DataClass, String> dateT;


    @FXML
    private JFXButton closeButton;

    @FXML
    private TableColumn<DataClass, Integer> finish;

    @FXML
    private TableColumn<DataClass, String> roomT;

    @FXML
    private TextField search;
    @FXML
    private AnchorPane pane;

    @FXML
    TableColumn<DataClass, Integer> startT;

    ObservableList<DataClass> data = FXCollections.observableArrayList();
    @FXML
    TableView<DataClass> tabel = new TableView<DataClass>(data);

    @FXML
    TableColumn<DataClass, String> title;



    @FXML
    void addMovies(ActionEvent event) {
       try{
           if(nameAdd.getText()!="" && genreAdd.getText()!=""&&lengthAdd.getText()!=""&&
           dateAdd.getValue()!=null&& startAdd.getValue()!=null&&stopAdd.getValue()!=null&&
           roomAdd.getValue()!=null){
               Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
               String insertQuery = "INSERT INTO DeletedFilms (name,  id_user) VALUES (?, ?)";
              //nu stiu cum sa fac. daca sa adaug numai filmu sau si rezervari
               PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
           }else{
               fillFields.setVisible(true);
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    @FXML
    void closeWindow(ActionEvent event) {
        stage.close();
    }

    @FXML
    void deleteReservation(ActionEvent event) {
        DataClass selectedFilm = tabel.getSelectionModel().getSelectedItem();

        if (selectedFilm != null) {
            try {
                // Move the selected film to the DeletedFilms table in the database
                Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
                String insertQuery = "INSERT INTO DeletedFilms (name,  id_user) VALUES (?, ?)";
                PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
                String insertQuery2 = "delete from Reservations where Reservations.id_reservation=" + selectedFilm.getId_reservation();
                PreparedStatement insertStatement2 = connection.prepareStatement(insertQuery2);

                // Set the values for the insert statement
                insertStatement.setString(1, selectedFilm.getTitle());
                insertStatement.setInt(2, selectedFilm.getId_user()); // Assuming getUserId() returns the appropriate id_user value

                // Execute the insert statement
                insertStatement.executeUpdate();
                insertStatement2.executeUpdate();

                // Remove the selected film from the TableView
                tabel.getItems().remove(selectedFilm);
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle any database errors
            }
        }
    }




    private double x = 0, y = 0;

    ObservableList<DataAdd> dataAdd = FXCollections.observableArrayList();




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            showMovies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        pane.setBackground(Background.EMPTY);
        closeButton.setRipplerFill(Color.TRANSPARENT);


        pane.setOnMousePressed(mouseEvent -> {
            x = mouseEvent.getSceneX();
            y = mouseEvent.getSceneY();
        });

        pane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX() - x);
            stage.setY(mouseEvent.getScreenY() - y);
        });



    }

    private DateCell createDateCell(DatePicker picker) {
        LocalDate today = LocalDate.now();
        LocalDate weekLater = today.plusDays(7);
        dateAdd.setValue(today);
        return new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(today) || date.isAfter(weekLater));

            }
        };
    }





    int index=0;
    public void showMovies() throws SQLException {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            // create a new JDBC statement
            Statement statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT start,stop,type_room,date,name,F.image,Reservations.id_user,Reservations.id_reservation from Reservations join Film F on F.id_film = Reservations.id_film");


        // Loop through the result set and add each row to the observable list
        while (rs.next()) {
            String name = rs.getString("name");
            int stop = rs.getInt("stop");
            int start = rs.getInt("start");
            String type_room = rs.getString("type_room");
            String date=rs.getString("date");
            int id_user= rs.getInt("id_user");
            int id_reservation= rs.getInt("id_reservation");
            data.add(new DataClass(name,start,stop,date,type_room,id_user,id_reservation));
            //System.out.println(data.get(index).getTitle()+data.get(index).getRoomT()+data.get(index).getDateT()+data.get(index).getId_user());
            index++;
        }


        // Define the columns for the TableView

        title.setCellValueFactory(new PropertyValueFactory<DataClass, String>("title"));
        startT.setCellValueFactory(new PropertyValueFactory<DataClass,Integer>("startT"));
        finish.setCellValueFactory(new PropertyValueFactory<DataClass, Integer>("finish"));
        roomT.setCellValueFactory(new PropertyValueFactory<DataClass, String>("roomT"));
        dateT.setCellValueFactory(new PropertyValueFactory<DataClass, String>("dateT"));
        tabel.setItems(data);


    }


    private Stage stage=new Stage();
    public void setStage(Stage stage) {
        this.stage=stage;
    }


    public void showDelete(ActionEvent event) {
    }

    public void showEdit(ActionEvent event) {
    }

    public void deleteMovies(ActionEvent event) {
    }
}