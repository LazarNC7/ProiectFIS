package com.example.fis;


import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShowMovieController implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ImageView imageView;

    @FXML
    private JFXButton nextButton;

    @FXML
    private ChoiceBox<Integer> oraChoice;


    @FXML
    private ChoiceBox<String> salaChoice;

    @FXML
    private JFXButton prevButton;

    @FXML
    private Text titleMovie;

    @FXML
    private JFXButton validareOptiune;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    void applyChanges(ActionEvent event) {
        DatabaseConnection connection = new DatabaseConnection();
        Connection connectiondb = connection.geConnection();

        String verify = "SELECT count(1) FROM Film WHERE date1 = ? AND sala = ? AND time_begin >= ?";
        String titleverify = "SELECT nume_film FROM Film WHERE date1 = ? AND sala = ? AND time_begin >= ?";
        String imageverify = "SELECT image FROM Film WHERE date1 = ? AND sala = ? AND time_begin >= ?";

        try (PreparedStatement verifyStmt = connectiondb.prepareStatement(verify);
             PreparedStatement titleStmt = connectiondb.prepareStatement(titleverify);
             PreparedStatement imageStmt = connectiondb.prepareStatement(imageverify)) {
            verifyStmt.setString(1, datePicker.getValue().toString());
            verifyStmt.setString(2, salaChoice.getValue());
            verifyStmt.setString(3, Integer.toString(oraChoice.getValue()));

            ResultSet resultSet = verifyStmt.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                if (count > 0) {
                    titleStmt.setString(1, datePicker.getValue().toString());
                    titleStmt.setString(2, salaChoice.getValue());
                    titleStmt.setString(3, Integer.toString(oraChoice.getValue()));
                    ResultSet resultSet1 = titleStmt.executeQuery();
                    if (resultSet1.next()) {
                        titleMovie.setText(resultSet1.getString("nume_film"));
                    }

                    imageStmt.setString(1, datePicker.getValue().toString());
                    imageStmt.setString(2, salaChoice.getValue());
                    imageStmt.setString(3, Integer.toString(oraChoice.getValue()));
                    ResultSet resultSet2 = imageStmt.executeQuery();
                    if (resultSet2.next() && resultSet2.getString("image") != null) {
                        ImageView imageView1 = new ImageView(resultSet2.getString("image"));
                        if (imageView1 != null) {
                            imageView = imageView1;
                        }
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connectiondb != null) {
                    connectiondb.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    }

    @FXML
    void nextMovie(ActionEvent event) {

    }

    @FXML
    void prevMovie(ActionEvent event) {

    }

    private int indexFilm=1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


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

        salaChoice.getItems().addAll("2D", "3D", "IMAX", "4D");
    }
}
