package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;



public class AdminOptionsController implements Initializable {

    @FXML
    private JFXButton addButton;

    @FXML
    private JFXButton customersButton;

    @FXML
    private TableColumn<DataClass, String> dateT;

    @FXML
    private JFXButton deleteButton;

    @FXML
    private JFXButton editButton;

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
    void EditMovies(ActionEvent event) {

    }

    @FXML
    void addMovies(ActionEvent event) {

    }

    @FXML
    void closeWindow(ActionEvent event) {
        stage.close();
    }

    @FXML
    void deleteMovies(ActionEvent event) {

    }

    @FXML
    void searchMovie(ActionEvent event) {

    }

    @FXML
    void seeCustomers(ActionEvent event) {

    }

    private double x=0,y=0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            tabel.setVisible(true);
            showMovies();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        pane.setBackground(Background.EMPTY);
        closeButton.setRipplerFill(Color.TRANSPARENT);



        pane.setOnMousePressed(mouseEvent -> {
            x= mouseEvent.getSceneX();
            y= mouseEvent.getSceneY();
        });

        pane.setOnMouseDragged(mouseEvent -> {
            stage.setX(mouseEvent.getScreenX()-x);
            stage.setY(mouseEvent.getScreenY()-y);
        });


    }

//    private void showMovies(){
//        try {
//            // create a new JDBC connection
//            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
//
//            // create a new JDBC statement
//            Statement statement = connection.createStatement();
//
//            // execute the SQL statements and retrieve the results
//            ResultSet nameResult = statement.executeQuery("SELECT Film.name FROM Film JOIN Reservations R ON Film.id_film = R.id_film;");
//            ResultSet startResult = statement.executeQuery("SELECT start FROM Reservations;");
//            ResultSet stopResult = statement.executeQuery("SELECT stop FROM Reservations;");
//            ResultSet dateResult = statement.executeQuery("SELECT date FROM Reservations;");
//            ResultSet roomResult = statement.executeQuery("SELECT type_room FROM Reservations;");
//
//            // process the results
//            while (nameResult.next()) {
//                String name = nameResult.getString("name");
//                title.setCellFactory(tc -> new TableCell<DataClass,String>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                title.setCellValueFactory(new PropertyValueFactory<>(name));
//                tabel.getColumns().add(title);
//                System.out.println(title.getText());
//            }
//            while (startResult.next()) {
//                String start = startResult.getString("start");
//
//                startT.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(Integer item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                startT.setCellValueFactory(new PropertyValueFactory<>(start));
//                startT.getColumns().add(startT);
//            }
//            while (stopResult.next()) {
//                String stop = stopResult.getString("stop");
//
//                finish.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(Integer item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                finish.setCellValueFactory(new PropertyValueFactory<>(stop));
//                finish.getColumns().add(finish);
//            }
//            while (dateResult.next()) {
//                String date = dateResult.getString("date");
//
//                dateT.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                dateT.setCellValueFactory(new PropertyValueFactory<>(date));
//                dateT.getColumns().add(dateT);
//            }
//            while (roomResult.next()) {
//                String room = roomResult.getString("type_room");
//
//
//                roomT.setCellFactory(tc -> new TableCell<>() {
//                    @Override
//                    protected void updateItem(String item, boolean empty) {
//                        super.updateItem(item, empty);
//                        setText(empty ? null : getString());
//                    }
//
//                    private String getString() {
//                        return getItem() == null ? "" : getItem().toString();
//                    }
//                });
//                roomT.setCellValueFactory(new PropertyValueFactory<>(room));
//                roomT.getColumns().add(roomT);
//            }
//
//            // close the JDBC objects
//            roomResult.close();
//            dateResult.close();
//            stopResult.close();
//            startResult.close();
//            nameResult.close();
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            // handle the exception
//        }
//    }

    int index=0;
    public void showMovies() throws SQLException {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");

            // create a new JDBC statement
            Statement statement = connection.createStatement();
            ResultSet rs=statement.executeQuery("SELECT start,stop,type_room,date,name from Reservations join Film F on F.id_film = Reservations.id_film");


        // Loop through the result set and add each row to the observable list
        while (rs.next()) {
            String name = rs.getString("name");
            int stop = rs.getInt("stop");
            int start = rs.getInt("start");
            String type_room = rs.getString("type_room");
            String date=rs.getString("date");
            data.add(new DataClass(name,start,stop,date,type_room));
            //System.out.println(data.get(index).getTitle()+data.get(index).getRoomT()+data.get(index).getDateT());
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
}