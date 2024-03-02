package com.example.dbproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Register {

    private Stage stage;

    @FXML
    private Button backButton;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private RadioButton patient;

    @FXML
    private RadioButton donor;


    public void MakeConnection() throws SQLException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
        String username = "root";
        String password = "DATABASE.MYSQL";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {

            String email = usernameField.getText();
            String pass = passwordField.getText();
            String confirm = confirmPasswordField.getText();

            String role = "";

            if(donor.isSelected())
            {
                role = donor.getText();
            }else{
                role = patient.getText();
            }
            if (!pass.equals(confirm)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("wrong password");
                alert.showAndWait();
                passwordField.setText("");
                confirmPasswordField.setText("");

            } else {
            String insertQuery = "INSERT INTO register (email,password,confirmPassword,role) VALUES (?,?,?,?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, email);
                preparedStatement.setString(2, pass);
                preparedStatement.setString(3, confirm);
                preparedStatement.setString(4, role);

                int rowsAffected = preparedStatement.executeUpdate();



                    if (rowsAffected > 0) {

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Successfully registration");
                        alert.showAndWait();

                    } else {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Failed to submit data");
                        alert.showAndWait();
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void login(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("logInpage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

}