package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;
import org.jasypt.util.text.BasicTextEncryptor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginFormController {

    @FXML
    private JFXTextField txtLEmail;

    @FXML
    private JFXPasswordField txtPassword;

    @FXML
    void btnLoginAction(ActionEvent event) throws SQLException, IOException {
        String key = "#5541Asd";
        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String SQL = "SELECT * FROM users WHERE email = '" + txtLEmail.getText() + "'";
        Connection connection = DBConnection.getInstance().getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        if (resultSet.next()) {
            User user = new User(
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4)
            );

            if (basicTextEncryptor.decrypt(user.getPassword()).equals(txtPassword.getText())) {
                Stage stage = new Stage();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-form.fxml"))));
                stage.show();
            }else {
                new Alert(Alert.AlertType.ERROR, "Invalid Password", ButtonType.OK).show();
            }
            System.out.println(user);
        }else{
            new Alert(Alert.AlertType.ERROR, "User Not Found", ButtonType.OK).show();
        }

    }

    @FXML
    void hyperRegisterAction(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/register-form.fxml"))));
        stage.show();

    }

}

