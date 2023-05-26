package com.example.fis;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private Text invalidLoginText;




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

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    public void validateLogin(){
        DatabaseConnection connection=new DatabaseConnection();
        Connection connectiondb=connection.geConnection();
        String verify = "select count(1) from UserInfo where username ='"+username.getText()+"' and password='"+encodePassword(username.getText(),password.getText())+"'";

        try{
            Statement statement=connectiondb.createStatement();
            ResultSet resultSet=statement.executeQuery(verify);

            while (resultSet.next()){
                if(resultSet.getInt(1)==1){
                    invalidLoginText.setVisible(false);

                    try{
                        User.setPassword(password.getText());
                        User.setUsername(username.getText());
                        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("client.fxml"));
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stageSign=new Stage();
                        ClientController controller=fxmlLoader.getController();
                        scene.setFill(Color.TRANSPARENT);
                        stageSign.initStyle(StageStyle.TRANSPARENT);
                        stageSign.setScene(scene);
                        controller.setStage(stageSign);
                        stageSign.show();
                        stage.close();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    invalidLoginText.setVisible(true);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void checkEmpty(ActionEvent event){
        if(username.getText().isBlank()==false && password.getText().isBlank()==false){
            validateLogin();
        }
    }

    public void signupForm(){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stageSign=new Stage();
            SignupController controller=fxmlLoader.getController();
            scene.setFill(Color.TRANSPARENT);
            stageSign.initStyle(StageStyle.TRANSPARENT);
            stageSign.setScene(scene);
            controller.setStage(stageSign);
            stageSign.show();
            stage.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void adminLoginMethod(ActionEvent event){
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("adminLogin.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage stageSign=new Stage();
            AdminLoginController controller=fxmlLoader.getController();
            scene.setFill(Color.TRANSPARENT);
            stageSign.initStyle(StageStyle.TRANSPARENT);
            stageSign.setScene(scene);
            controller.setStage(stageSign);
            stageSign.show();
            stage.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
