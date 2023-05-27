package com.example.fis;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.*;

public class AdminOptionsControllerTest extends ApplicationTest {

    private TextField nameAdd;
    private TextField genreAdd;
    private TextField lengthAdd;
    private String imagePath;

    private AdminOptionsController controller;

    @Start
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("adminOptions.fxml"));
        Parent root = loader.load();
        controller = loader.getController();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }



//    @Test
//    public void testAddMovies() {
//        // Simulate an action event
//        ActionEvent event = new ActionEvent();
//
//        // Execute the addMovies method
//        Platform.runLater(() -> {
//            new AdminOptionsController().addMovies(event); // Replace with your controller class and method name
//        });
//
//        // Wait for the addMovies method to finish
//        WaitForAsyncUtils.waitForFxEvents();
//
//        // Assert that the movie was inserted successfully
//        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:identifier.sqlite");
//             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM Film WHERE name = ?")) {
//            statement.setString(1, "Movie Name");
//            ResultSet resultSet = statement.executeQuery();
//            int count = resultSet.getInt(1);
//            Assertions.assertEquals(1, count, "Movie should be inserted into the database.");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testAddMoviesWithoutImageView() {
        TextField nameAdd = lookup("#nameAdd").query();
        TextField genreAdd = lookup("#genreAdd").query();
        TextField lengthAdd = lookup("#lengthAdd").query();
        nameAdd.setText("Movie Name");
        genreAdd.setText("Movie Genre");
        lengthAdd.setText("120");
        clickOn("#applyAdd");
        assertEquals("",controller.getConsoleOutput());

    }

    @Test
    public void testAddMoviesValidInputs() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        System.setOut(printStream);
        TextField nameAdd = lookup("#nameAdd").query();
        TextField genreAdd = lookup("#genreAdd").query();
        TextField lengthAdd = lookup("#lengthAdd").query();
        ImageView imageView = lookup("#imageViewAdd").query();
        nameAdd.setText("Nemo3");
        genreAdd.setText("Cartoon");
        lengthAdd.setText("120");
        Image image = new Image("C:\\Users\\nocti\\IdeaProjects\\FIS\\src\\main\\java\\com\\example\\fis\\resurse\\Finding_Nemo.jpg");
        imageView.setImage(image);
        clickOn("#applyAdd");
        System.setOut(System.out);
        String output = outputStream.toString();
        assertTrue(output.contains("Film inserted successfully."));
    }

    @Test
    public void testAddMoviesWithoutName() {
        TextField nameAdd = lookup("#nameAdd").query();
        TextField genreAdd = lookup("#genreAdd").query();
        TextField lengthAdd = lookup("#lengthAdd").query();
        ImageView imageView = lookup("#imageViewAdd").query();
        genreAdd.setText("Movie Genre");
        lengthAdd.setText("120");
        Image image = new Image("C:\\Users\\nocti\\IdeaProjects\\FIS\\src\\main\\java\\com\\example\\fis\\resurse\\the_dark_knight.jpg");
        imageView.setImage(image);
        clickOn("#applyAdd");

        String expectedOutput = "";
        String actualOutput = controller.getConsoleOutput();

        assertEquals(expectedOutput, actualOutput);
    }
}
