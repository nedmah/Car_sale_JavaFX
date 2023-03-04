package com.example.kyrsach;

import com.example.kyrsach.Animations.Shake;
import com.example.kyrsach.assets.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField newlogin;

    @FXML
    private TextField newpassword;

    @FXML
    private Button registrnew;

    @FXML
    private TextField user_lastname;

    @FXML
    private TextField user_name;

    @FXML
    private ImageView image1;

    @FXML
    void initialize() {
        Image image = new Image("C:\\Users\\Admin\\IdeaProjects\\Kyrsach\\src\\main\\java\\com\\example\\kyrsach\\assets\\new-car.png");
        image1.setImage(image);
        image1.setCache(true);
            registrnew.setOnAction(event -> {
            signUpNewUser();

        });
    }

    private void signUpNewUser(){
        DatabaseHandler databaseHandler = new DatabaseHandler();
        String firstName = user_name.getText();
        String lastName = user_lastname.getText();
        String login = newlogin.getText();
        String password = newpassword.getText();
        if(firstName.equals("") || lastName.equals("") || login.equals("") || password.equals("")){
            user_name.setStyle("-fx-border-color: red;");
            user_name.setStyle("-fx-background-color: red;");
            user_lastname.setStyle("-fx-border-color: red;");
            user_lastname.setStyle("-fx-background-color: red;");
            newlogin.setStyle("-fx-border-color: red;");
            newlogin.setStyle("-fx-background-color: red;");
            newpassword.setStyle("-fx-border-color: red;");
            newpassword.setStyle("-fx-background-color: red;");
            Shake userLoginAnim = new Shake(newlogin);
            Shake userPasswordAnim = new Shake(newpassword);
            Shake userNameAnim = new Shake(user_name);
            Shake userLastNameAnim = new Shake(user_lastname);
            userLoginAnim.playAnim();
            userPasswordAnim.playAnim();
            userNameAnim.playAnim();
            userLastNameAnim.playAnim();
            String musicFile = "C:\\Users\\Admin\\Downloads\\bip.mp3";     // For example
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        }else {
            User user = new User(firstName, lastName, login, password);
            databaseHandler.signUpUser(user);
            Stage stage = (Stage) registrnew.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void clearField(ActionEvent actionEvent){
        user_name.setStyle("-fx-background-color: white;");
        user_name.setStyle("-fx-border-color: black;");
        user_name.clear();
        user_lastname.setStyle("-fx-background-color: white;");
        user_lastname.setStyle("-fx-border-color: black;");
        user_lastname.clear();
        newlogin.setStyle("-fx-background-color: white;");
        newlogin.setStyle("-fx-border-color: black;");
        newlogin.clear();
        newpassword.setStyle("-fx-background-color: white;");
        newpassword.setStyle("-fx-border-color: black;");
        newpassword.clear();
    }


}
