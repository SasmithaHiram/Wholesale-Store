package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterFormController {

        @FXML
        private JFXPasswordField txtConfirmPassword;

        @FXML
        private JFXTextField txtEmail;

        @FXML
        private JFXPasswordField txtPassword;

        @FXML
        private JFXTextField txtUsername;

        @FXML
        void btnRegisterCustomer(ActionEvent event) throws SQLException {
            String SQL = "INSERT INTO USERS (username, email, password) VALUES(?,?,?)";

            if(txtPassword.getText().equals(txtConfirmPassword.getText())) {
                Connection connection = DBConnection.getInstance().getConnection();
                ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM USERS WHERE email = '" + txtEmail.getText() + "'");

                if(!resultSet.next()) {
                    User user = new User(txtUsername.getText(), txtEmail.getText(), txtPassword.getText());
                    PreparedStatement preparedStatement = connection.prepareStatement(SQL);
                    preparedStatement.setString(1, user.getUsername());
                    preparedStatement.setString(2, user.getEmail());
                    preparedStatement.setString(3, user.getPassword());
                    preparedStatement.executeUpdate();
                }else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                }
//                else{
//                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                }
            }
        }

}
