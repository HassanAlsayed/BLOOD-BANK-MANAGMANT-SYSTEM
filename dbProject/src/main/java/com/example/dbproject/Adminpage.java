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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Adminpage    {

    private Stage stage;

    @FXML
    private TextField idForSearch;
    @FXML
    private TextField idDelete;
    @FXML
    private TextField combobank;


    @FXML
    private TextField donorbloodpressure;

    @FXML
    private TextField donorphone;

    @FXML
    private TextField donorweight;
    @FXML
    private TextField donorId;

    public ObservableList<ObservableList<String>> datab;

    public Adminpage() throws SQLException {
    }


    public ObservableList<ObservableList<String>> data;

    public void showDoner(ActionEvent event) throws IOException {


        TableView<ObservableList<String>> tableView = new TableView<>();

        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
        String username = "root";
        String password = "DATABASE.MYSQL";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT * FROM donor";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                tableView.getColumns().add(column);
            }

            data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                data.add(row);
            }

            tableView.setItems(data);
            Scene scene = new Scene(tableView);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BLOOD BANK MANAGEMENT SYSTEM");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ObservableList<String>> datap;

    public void showPatient() throws IOException {


        TableView<ObservableList<String>> tableView = new TableView<>();

        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
        String username = "root";
        String password = "DATABASE.MYSQL";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT * FROM patient";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                tableView.getColumns().add(column);
            }

            datap = FXCollections.observableArrayList();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                datap.add(row);
            }

            tableView.setItems(datap);
            Scene scene = new Scene(tableView);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BLOOD BANK MANAGEMENT SYSTEM");
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ObservableList<String>> dataSearch;

    public void searchDonorById() throws SQLException {
        Connection connection = null;
        try {
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
            String username = "root";
            String password = "DATABASE.MYSQL";
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String textValue = idForSearch.getText();
            int id = Integer.parseInt(textValue);

            try (Statement statement = connection.createStatement()) {
                String createProcedure = "CREATE PROCEDURE searchDonor(IN idSearch INT) " +
                        "BEGIN " +
                        "    SELECT * FROM donor WHERE id = idSearch; " +
                        "END";
                statement.executeUpdate(createProcedure);

                String callProcedureSQL = "CALL searchDonor(" + id + ")";
                try (ResultSet resultSet = statement.executeQuery(callProcedureSQL)) {
                    idForSearch.setText("");
                    TableView<ObservableList<String>> searchTable = new TableView<>();
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                        searchTable.getColumns().add(column);
                    }

                    dataSearch = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                            row.add(resultSet.getString(i));
                        }
                        dataSearch.add(row);
                    }
                    searchTable.setItems(dataSearch);

                    Scene scene = new Scene(searchTable);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("BLOOD BANK MANAGEMENT SYSTEM");
                    stage.show();
                }

                String dropProcedureSQL = "DROP PROCEDURE IF EXISTS searchDonor";
                statement.executeUpdate(dropProcedureSQL);
            }
        } finally {

            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deleteDonor() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbankmanagmentsystem", "root", "DATABASE.MYSQL")) {
            String textValue = idDelete.getText();
            int ID = Integer.parseInt(textValue);

            Statement statement = connection.createStatement();
            String createProcedureSQL = "CREATE PROCEDURE deleteDonor() " +
                    "BEGIN " +
                    "DELETE FROM bloodbank"+
                    "  WHERE id_d=" + ID + "; " +
                    "  DELETE FROM donor " +
                    "  WHERE id=" + ID + "; " +
                    "END";
            statement.executeUpdate(createProcedureSQL);

            String callProcedureSQL =  "CALL deleteDonor()";
            statement.execute(callProcedureSQL);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();
            idDelete.setText("");

            try (PreparedStatement dropProcedureStatement = connection.prepareStatement("DROP PROCEDURE IF EXISTS deleteDonor")) {
                dropProcedureStatement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateDonor() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem", "root", "DATABASE.MYSQL")) {


            String textValue = donorId.getText();
            int ID = Integer.parseInt(textValue);
            String bank = combobank.getText();
            String bloodp = donorbloodpressure.getText();
            String weight = donorweight.getText();
            String phone = donorphone.getText();

            Statement statement = connection.createStatement();
            String createProcedureSQL = "CREATE PROCEDURE updateDonor(IN phoneN VARCHAR(20), IN WEIGHT VARCHAR(20), IN BLOODP VARCHAR(10), IN Bank VARCHAR(20)) " +
                            "BEGIN " +
                            "  UPDATE donor " +
                            "  SET phone=phoneN, weight=WEIGHT, bloodPressure=BLOODP, bankName=Bank " +
                            "  WHERE id=" + ID + "; " +
                            "END";
                statement.executeUpdate(createProcedureSQL);

                String callProcedureSQL =  "CALL updateDonor('" + phone + "','" + weight + "','" + bloodp + "','" + bank + "')";
                statement.execute(callProcedureSQL);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Successfully updated");
                alert.showAndWait();

            String dropProcedureSQL = "DROP PROCEDURE IF EXISTS updateDonor";
            statement.executeUpdate(dropProcedureSQL);
                donorId.setText("");
                combobank.setText("");
                donorphone.setText("");
                donorbloodpressure.setText("");
                donorweight.setText("");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DonorInfo(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("donorInfo.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void PatientInfo(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("patientInfo.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void searchPatientById() throws SQLException {
        Connection connection = null;
        try {
            String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
            String username = "root";
            String password = "DATABASE.MYSQL";
            connection = DriverManager.getConnection(jdbcUrl, username, password);

            String textValue = idForSearch.getText();
            int id = Integer.parseInt(textValue);

            try (Statement statement = connection.createStatement()) {

                String callProcedureSQL = "CALL searchPatient(" + id + ")";
                try (ResultSet resultSet = statement.executeQuery(callProcedureSQL)) {
                    idForSearch.setText("");
                    TableView<ObservableList<String>> searchTable = new TableView<>();
                    for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                        final int j = i;
                        TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                        column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                        searchTable.getColumns().add(column);
                    }

                    dataSearch = FXCollections.observableArrayList();
                    while (resultSet.next()) {
                        ObservableList<String> row = FXCollections.observableArrayList();
                        for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                            row.add(resultSet.getString(i));
                        }
                        dataSearch.add(row);
                    }
                    searchTable.setItems(dataSearch);

                    Scene scene = new Scene(searchTable);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("BLOOD BANK MANAGEMENT SYSTEM");
                    stage.show();
                }
            }
        } finally {

            if (connection != null) {
                connection.close();
            }
        }
    }

    public void deletePatient() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/bloodbankmanagmentsystem", "root", "DATABASE.MYSQL")) {
            String textValue = idDelete.getText();
            int ID = Integer.parseInt(textValue);

            Statement statement = connection.createStatement();
            String createProcedureSQL = "CREATE PROCEDURE deletePatient() " +
                    "BEGIN " +
                    "DELETE FROM patient"+
                    "  WHERE id=" + ID + "; " +
                    "END";
            statement.executeUpdate(createProcedureSQL);


            String callProcedureSQL =  "CALL deletePatient()";
            statement.execute(callProcedureSQL);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully deleted");
            alert.showAndWait();
            idDelete.setText("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updatePatient() {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem", "root", "DATABASE.MYSQL")) {


            String textValue = donorId.getText();
            int ID = Integer.parseInt(textValue);
            String bank = combobank.getText();
            String bloodp = donorbloodpressure.getText();
            String phone = donorphone.getText();

            Statement statement = connection.createStatement();
            String createProcedureSQL = "CREATE PROCEDURE updatePatient(IN phoneN VARCHAR(20),IN BLOODP VARCHAR(10), IN Bank VARCHAR(20)) " +
                    "BEGIN " +
                    "  UPDATE patient " +
                    "  SET phone=phoneN,bloodPressure=BLOODP, bankName=Bank " +
                    "  WHERE id =" + ID + "; " +
                    "END";
            statement.executeUpdate(createProcedureSQL);

            String callProcedureSQL =  "CALL updatePatient('" + phone + "','" + bloodp + "','" + bank + "')";
            statement.execute(callProcedureSQL);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully updated");
            alert.showAndWait();

            String dropProcedureSQL = "DROP PROCEDURE IF EXISTS updatePatient";
            statement.executeUpdate(dropProcedureSQL);
            donorId.setText("");
            combobank.setText("");
            donorphone.setText("");
            donorbloodpressure.setText("");

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void showRegsitration(){


        TableView<ObservableList<String>> tableView = new TableView<>();

        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
        String username = "root";
        String password = "DATABASE.MYSQL";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            String query = "SELECT * FROM register";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            for (int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(resultSet.getMetaData().getColumnName(i + 1));
                column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j)));
                tableView.getColumns().add(column);
            }

            datap = FXCollections.observableArrayList();
            while (resultSet.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                    row.add(resultSet.getString(i));
                }
                datap.add(row);
            }

            tableView.setItems(datap);
            Scene scene = new Scene(tableView);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("BLOOD BANK MANAGEMENT SYSTEM");
            stage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("adminp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }



    }




