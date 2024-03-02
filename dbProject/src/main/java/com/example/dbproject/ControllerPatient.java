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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControllerPatient implements Initializable {
    private Stage stage;

    @FXML
    private AnchorPane myscene;

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
    private TextField myblood;
    @FXML
    private ComboBox<String> mycombo;
    @FXML
    private ComboBox<String> combobank;
    public void MakeConnectionPatient() throws SQLException {
        String firstname = myfirstname.getText();
        String lastname = mylastname.getText();
        LocalDate date = mybirthdate.getValue();
        String phone = myphone.getText();
        String address = myaddress.getText();
        String bloodPressure = myblood.getText();
        String bloodType = mycombo.getSelectionModel().getSelectedItem().toString();
        String bankname = combobank.getSelectionModel().getSelectedItem().toString();
        int id = 1;



        if (firstname.isEmpty() ||  lastname.isEmpty() || phone.isEmpty() || address.isEmpty() || bloodPressure.isEmpty()
                || date == null || bloodType.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("all feilds are requires");
            alert.showAndWait();
        } else {
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
            String username = "root";
            String password = "DATABASE.MYSQL";

            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {


                    String insertQuery = "INSERT INTO patient (id,firstname, lastname, dateb, phone," +
                            " address, bloodPressure,bloodType,bankName) VALUES (?,?,?,?,?,?,?,?,?)";


                    try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                        preparedStatement.setInt(1, id);
                        preparedStatement.setString(2, firstname);
                        preparedStatement.setString(3, lastname);
                        preparedStatement.setDate(4, java.sql.Date.valueOf(date));
                        preparedStatement.setString(5, phone);
                        preparedStatement.setString(6, address);
                        preparedStatement.setString(7, bloodPressure);
                        preparedStatement.setString(8, bloodType);
                        preparedStatement.setString(9, bankname);


                        int rowsAffected = preparedStatement.executeUpdate();


                        if (rowsAffected > 0) {
                            createProcedure(connection);

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
                        myblood.setText("");
                        mycombo.getSelectionModel().clearSelection();
                        combobank.getSelectionModel().clearSelection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

            }
        }


    public void submitData(ActionEvent event) throws SQLException {

        MakeConnectionPatient();
    }


    public void cancle(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"ALL DATA ARE CANCELD");
        Optional<ButtonType> buttonType = alert.showAndWait();
        if(buttonType.get() == ButtonType.OK) {
            String firstname = myfirstname.getText();
            String lastname = mylastname.getText();
            LocalDate date = mybirthdate.getValue();
            String phone = myphone.getText();
            String address = myaddress.getText();
            String blood = myblood.getText();


            myfirstname.setText("");
            mylastname.setText("");
            mybirthdate.setValue(null);
            myphone.setText("");
            myaddress.setText("");
            myblood.setText("");

        }else {}

    }

    public void createProcedure(Connection connection) {
        try {

            Statement statement = connection.createStatement();

            String bloodType = mycombo.getSelectionModel().getSelectedItem().toString();
            String bankName = combobank.getSelectionModel().getSelectedItem().toString();
            String createProcedureSQL = "CREATE PROCEDURE procedurePatient(IN bank VARCHAR(10),IN type VARCHAR(10)) " +
                    "BEGIN " +
                    "    SELECT COUNT(bloodType) AS sum FROM bloodbank WHERE bloodBank_name = bank AND bloodType = type; " +
                    "END";

            statement.executeUpdate(createProcedureSQL);

            String callProcedureSQL = "CALL procedurePatient('" + bankName + "','" + bloodType + "')";
            statement.execute(callProcedureSQL);

            ResultSet resultSet = statement.getResultSet();
            int sum = 0;
            if (resultSet.next()) {
                sum = resultSet.getInt("sum");
            }

            if(sum > 0) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("there is " + sum + "of type "+bloodType +" in "+ bankName);
                alert.showAndWait();

                String updatebloodBank = "DELETE FROM bloodbank " +
                        "WHERE bloodBank_name = '" + bankName + "' " +
                        "AND bloodType = '" + bloodType + "' " +
                        "LIMIT 1;\n";

                statement.executeUpdate(updatebloodBank);

            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("there is no blood of type " + bloodType +" in "+ bankName);
                alert.showAndWait();
            }

            String dropProcedureSQL = "DROP PROCEDURE IF EXISTS procedurePatient";
            statement.executeUpdate(dropProcedureSQL);

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> list = FXCollections.observableArrayList("AB","A+","A-","B+","B-","O+","O-");
        mycombo.setItems(list);
        ObservableList<String> bank = FXCollections.observableArrayList("bank 1","bank 2","bank 3");
        combobank.setItems(bank);

    }

    public void LogOutPatient(ActionEvent event) throws IOException {
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
