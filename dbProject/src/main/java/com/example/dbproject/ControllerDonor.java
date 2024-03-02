package com.example.dbproject;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public  class ControllerDonor implements Initializable {
    private Stage stage;

    @FXML
    private ImageView logout;

    @FXML
    private Button mybtn;

    @FXML
    private TextField myfirstname;
    @FXML
    private TextField mylastname;
    @FXML
    private DatePicker mybirthdate;
    @FXML
    private TextField myphone;
    @FXML
    private TextField myaddress;
    @FXML
    private TextField myweight;
    @FXML
    private TextField myblood;
    @FXML
    private RadioButton male, female;
    @FXML
    private RadioButton yes, no;
    @FXML
    private ComboBox<String> comboblood;
    @FXML
    private ComboBox<String> mycombobank;
    @FXML
    private AnchorPane myscene;


    public void MakeConnectionDonor(ActionEvent event) throws SQLException {
        String firstname = myfirstname.getText();
        String lastname = mylastname.getText();
        LocalDate date = mybirthdate.getValue();
        String phone = myphone.getText();
        String address = myaddress.getText();
        String weight = myweight.getText();
        String bloodPressure = myblood.getText();
        String bloodType = comboblood.getSelectionModel().getSelectedItem().toString();
        String gender = "";
        String sufer_from_disease = "";
        String bankname = mycombobank.getSelectionModel().getSelectedItem().toString();
        int id = 1;
        int id_blood = 1;

        if (male.isSelected()) {
            gender = male.getText();
        } else if (female.isSelected()) {
            gender = female.getText();
        }
        if (yes.isSelected()) {
            sufer_from_disease = yes.getText();
        } else if (no.isSelected()) {
            sufer_from_disease = no.getText();

        }
        if (firstname.isEmpty() || lastname.isEmpty() || phone.isEmpty() || address.isEmpty() || bloodPressure.isEmpty()
                || weight.isEmpty()
                || date == null || gender.isEmpty() || sufer_from_disease.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("all feilds are requires");
            alert.showAndWait();
        } else {
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
            String username = "root";
            String password = "DATABASE.MYSQL";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

                if(LocalDate.now().getYear() - date.getYear() < 18)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("you must should 18+");
                    alert.showAndWait();
                }else {

                    String insertQuery = "INSERT INTO donor (id,firstname, lastname, dateb, phone," +
                            " address, weight, bloodPressure, gender,bloodType, sufer_from_disease,bankName) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";


                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, firstname);
                        preparedStatement.setString(3, lastname);
                        preparedStatement.setDate(4, java.sql.Date.valueOf(date));
                        preparedStatement.setString(5, phone);
                        preparedStatement.setString(6, address);
                        preparedStatement.setString(7, weight);
                        preparedStatement.setString(8, bloodPressure);
                        preparedStatement.setString(9, gender);
                        preparedStatement.setString(10, bloodType);
                        preparedStatement.setString(11, sufer_from_disease);
                        preparedStatement.setString(12, bankname);

                        if (yes.isSelected()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("sorry you can not donate you are sufer from disease");
                            alert.showAndWait();
                            myfirstname.setText("");
                            mylastname.setText("");
                            mybirthdate.setValue(null);
                            myphone.setText("");
                            myaddress.setText("");
                            myweight.setText("");
                            myblood.setText("");
                            male.setSelected(false);
                            female.setSelected(false);
                            yes.setSelected(false);
                            no.setSelected(false);
                            return;

                        }
                        int rowsAffected = preparedStatement.executeUpdate();


                        if (rowsAffected > 0) {

                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Successfully submitted");
                            alert.showAndWait();

                        } else {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Failed to submit data");
                            alert.showAndWait();
                        }

                        myfirstname.setText("");
                        mylastname.setText("");
                        mybirthdate.setValue(null);
                        myphone.setText("");
                        myaddress.setText("");
                        myweight.setText("");
                        myblood.setText("");
                        male.setSelected(false);
                        female.setSelected(false);
                        yes.setSelected(false);
                        no.setSelected(false);
                        comboblood.getSelectionModel().clearSelection();
                        mycombobank.getSelectionModel().clearSelection();

                        String insertBloodBank = "INSERT INTO bloodbank (id_blood,id_d,bloodBank_name,bloodType) VALUES (?,?,?,?)";
                        try (PreparedStatement Statement = connection.prepareStatement(insertBloodBank)) {
                            Statement.setInt(1, id_blood);
                            Statement.setInt(2, id);
                            Statement.setString(3, bankname);
                            Statement.setString(4, bloodType);
                            Statement.executeUpdate();
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }

    }
    public void submitData(ActionEvent event) throws SQLException, IOException {

        MakeConnectionDonor(event);
    }


    public void cancle(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "ALL DATA ARE CANCELD");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            String firstname = myfirstname.getText();
            String lastname = mylastname.getText();
            LocalDate date = mybirthdate.getValue();
            String phone = myphone.getText();
            String address = myaddress.getText();
            String weight = myweight.getText();
            String blood = myblood.getText();
            String gender = "";
            String sufer = "";

            myfirstname.setText("");
            mylastname.setText("");
            mybirthdate.setValue(null);
            myphone.setText("");
            myaddress.setText("");
            myweight.setText("");
            myblood.setText("");
            male.setSelected(false);
            female.setSelected(false);
            yes.setSelected(false);
            no.setSelected(false);

        }
        else
        {

        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> bank = FXCollections.observableArrayList("bank 1", "bank 2", "bank 3");
        mycombobank.setItems(bank);
        ObservableList<String> list = FXCollections.observableArrayList("AB", "A+", "A-", "B+", "B-", "O+", "O-");
        comboblood.setItems(list);
    }

    public void LogOutDonor(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "are you sure you want to logout");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.OK) {
            Parent parent = FXMLLoader.load(getClass().getResource("logInpage.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } else {

        }
    }

}










