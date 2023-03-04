package com.example.kyrsach;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.random.RandomGenerator;

public class CarsController {
    @FXML
    private final ObservableList<Car> carData = FXCollections.observableArrayList();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Car, String> Brand;

    @FXML
    private TextField BrandText;

    @FXML
    private TableColumn<Car, String> Cost;

    @FXML
    private TextField CostText;

    @FXML
    private TableColumn<Car, String> Model;

    @FXML
    private TextField ModelText;

    @FXML
    private Button buy;

    @FXML
    private TableView<Car> carsTable;

    @FXML
    private Button find;

    @FXML
    private Button order;

    @FXML
    private TextField BrandFind;

    @FXML
    private TextField ModelFind;

    private Stage dialogStage;

    @FXML
    void initialize() throws IOException {
        File file = new File("D:\\kursach\\cars.txt");
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[(int)file.length()];
        fis.read(byteArray);String data = new String(byteArray);
        String[] stringArray = data.split("\n");
        for (int i = 0; i < stringArray.length; i++) {
            String[] mas = stringArray[i].split(" ");
            carData.add(new Car(mas[0], mas[1], Double.parseDouble(mas[2])));
        }
        System.out.println(carData);

        Brand.setCellValueFactory(new PropertyValueFactory<>("Brand"));
        Model.setCellValueFactory(new PropertyValueFactory<>("Model"));
        Cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
        carsTable.setItems(carData);

        showProductDetails((Car) null);

        carsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
                -> showProductDetails(newValue));
    }

    @FXML
    public void showProductDetails(Car car){
        if(car != null){
            String fake = String.valueOf(car.getBrand());
            BrandText.setText(fake);
            fake = String.valueOf(car.getModel());
            ModelText.setText(fake);
            fake = String.valueOf(car.getCost());
            CostText.setText(fake);
        } else {
            BrandText.setText("");
            ModelText.setText("");
            CostText.setText("");
        }
    }


    @FXML
    public void search(ActionEvent actionEvent){
        FilteredList<Car> filteredData = new FilteredList<>(carData, p -> true);
        BrandFind.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }


                if (myObject.getBrand().equals(newValue)) {
                    return true;
                    // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Car> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(carsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        carsTable.setItems(sortedData);
    }



    @FXML
    public void search1(ActionEvent actionEvent){
        FilteredList<Car> filteredData = new FilteredList<>(carData, p -> true);
        ModelFind.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }


                if (myObject.getModel().equals(newValue)) {
                    return true;
                    // Filter matches first name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<Car> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(carsTable.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        carsTable.setItems(sortedData);
    }



    @FXML
    public void zakaz(ActionEvent actionEvent) throws IOException {
        Car car = new Car();
        boolean flag = false;
        if(isInputValid()){
            for (Car el:
                    carData) {
                if(!el.getModel().equals(ModelText.getText())){
                    flag = true;
                } else {
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            car.setBrand(BrandText.getText());
            car.setModel(ModelText.getText());
            RandomGenerator rnd = new Random();
            int fake = rnd.nextInt(4000,25000);
            car.setCost(fake);
            carData.add(car);
            FileWriter writer = new FileWriter("D:\\kursach\\cars.txt", true);
            writer.write(car.toString());
            writer.write("\n");
            writer.flush();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Заказ авто");
            alert.setHeaderText("Отказано");
            alert.setContentText("Данный автомобиль уже имеется в салоне");
            alert.showAndWait();
        }
    }


    private boolean isInputValid(){
        String errorMessage = "";
        if(Objects.equals(BrandText.getText(), "") || BrandText.getText().length() == 0){
            errorMessage += "Нет доступной марки\n";
        }
        if(Objects.equals(ModelText.getText(), "") || ModelText.getText().length() == 0){
            errorMessage += "Нет доступной модели\n";
        }

        if(errorMessage.length() == 0){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Некорректные поля!");
            alert.setHeaderText("Внесите конкретную информацию");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
        return false;
    }



    public static void updateFile(ObservableList<Car> observableList) throws IOException {
        FileWriter writer = new FileWriter("D:\\kursach\\cars.txt", false);
        for(Car el: observableList){
            writer.write(el.toString());
            writer.write("\n");
        }
        writer.flush();
    }

    @FXML
    public void take(ActionEvent actionEvent) throws IOException {
        int selectedindex = carsTable.getSelectionModel().getSelectedIndex();
        if(selectedindex >= 0){
            File file = new File("D:\\kursach\\activeClient.txt");
            FileInputStream fis = new FileInputStream(file);
            byte[] byteArray = new byte[(int)file.length()];
            fis.read(byteArray);
            String data = new String(byteArray);
            String[] stringArray = data.split("\r\n");
            String login = stringArray[0];
            LocalDate date = LocalDate.now();
            LocalTime time = LocalTime.now();
            FileWriter writer = new FileWriter("D:\\kursach\\journal.txt", true);
            writer.write(login + " " + carsTable.getItems().get(selectedindex).toString() + " " + date + " " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond());
            writer.write("\n");
            writer.flush();
            carsTable.getItems().remove(selectedindex);
            updateFile(carsTable.getItems());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(null);
            alert.setTitle("Не выделено!");
            alert.setHeaderText("Не выбран товар ежжи");
            alert.setContentText("Выберите товар в таблице");
            alert.showAndWait();
        }
    }


}
