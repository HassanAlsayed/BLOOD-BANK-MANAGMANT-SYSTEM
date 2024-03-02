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
import java.sql.*;

public class hundleLogIn {

    private Stage stage;
    @FXML
    TextField usernameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Button signupButton;

    public void signup(ActionEvent event) throws IOException
    {
        Parent parent = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    public void checkRegister(ActionEvent event) throws SQLException, IOException {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/bloodbankmanagmentsystem";
        String username = "root";
        String password = "DATABASE.MYSQL";

        String email = usernameField.getText();
        String pass = passwordField.getText();



        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            Statement statement = connection.createStatement();

            String sql =
                    "    SELECT role FROM register " +
                    " WHERE email='" + email + "' AND password='" + pass + "';";
            ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()) {
                    String role = resultSet.getString("role");
                    switch (role) {
                        case "Donor":
                            switchScene("donor.fxml", event);
                            break;
                        case "Patient":
                            switchScene("patient.fxml", event);
                            break;
                        case "admin":
                            switchScene("adminp.fxml", event);
                            break;
                        default:
                            showAlert("You should register before");
                            break;
                    }
                } else {
                    showAlert("You should register before");
                }
            } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
        private void switchScene(String fxmlFile, ActionEvent event) throws IOException {
            Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        private void showAlert(String content) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(content);
            alert.showAndWait();
        }

}



