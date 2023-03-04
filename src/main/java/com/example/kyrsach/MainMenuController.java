package com.example.kyrsach;

import com.example.kyrsach.Animations.Shake;
import com.example.kyrsach.assets.DatabaseHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

public class MainMenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button registr;

    @FXML
    private Button vhod;

    @FXML
    private ImageView image1;

    @FXML
    void initialize() {
        Image image = new Image("C:\\Users\\Admin\\IdeaProjects\\Kyrsach\\src\\main\\java\\com\\example\\kyrsach\\assets\\new-car.png");
        image1.setImage(image);
        image1.setCache(true);
        vhod.setOnAction(event -> {
            String loginText = login.getText().trim();
            String loginPassword = password.getText().trim();
            if(!loginText.equals("") && !loginPassword.equals("")){
                try {
                    loginUser(loginText,loginPassword);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Сообщение об ошибке");
                alert.setContentText("Вы ничего не ввели!");
                alert.showAndWait();
            }
        });
    }

    @FXML
    public void registration(ActionEvent actionEvent) throws IOException {
        Stage stage1 = new Stage();
        stage1.setTitle("Регистрация");
        Parent root = FXMLLoader.load(getClass().getResource("Registration.fxml"));
        Scene scene1 = new Scene(root);
        stage1.setScene(scene1);
        stage1.show();

    }

    @FXML
    private void loginUser(String loginText, String loginPassword) throws SQLException, IOException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setLogin(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        while(result.next()) {
            counter++;
            String fake = result.getString(3);
            FileWriter writer = new FileWriter("D:\\kursach\\activeClient.txt", false);
            writer.write(fake);
            writer.flush();
        }

        if (counter >= 1){
            Stage stage1 = new Stage();
            stage1.setTitle("Машины");
            Parent root = FXMLLoader.load(getClass().getResource("Cars.fxml"));
            Scene scene1 = new Scene(root);
            stage1.setScene(scene1);
            stage1.show();
        } else {
            login.setStyle("-fx-border-color: red;");
            login.setStyle("-fx-background-color: red;");
            password.setStyle("-fx-border-color: red;");
            password.setStyle("-fx-background-color: red;");
            Shake userLoginAnim = new Shake(login);
            Shake userPasswordAnim = new Shake(password);
            userLoginAnim.playAnim();
            userPasswordAnim.playAnim();
            String musicFile = "C:\\Users\\Admin\\Downloads\\bip.mp3";     // For example
            Media sound = new Media(new File(musicFile).toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();

        }

    }

    @FXML
    public void clearField(ActionEvent actionEvent){
        login.setStyle("-fx-background-color: white;");
        login.setStyle("-fx-border-color: black;");
        login.clear();
        password.clear();
        password.setStyle("-fx-background-color: white;");
        password.setStyle("-fx-border-color: black;");}


}
