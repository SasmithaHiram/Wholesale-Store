package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;

public class DashboardFormController {

        @FXML
        private AnchorPane loadFormContent;

        @FXML
        void btnCustomerFormAction(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("/view/customer-form.fxml");

            assert resource != null;

            Parent load = FXMLLoader.load(resource);
            loadFormContent.getChildren().clear();
            loadFormContent.getChildren().add(load);

        }

        @FXML
        void btnItemFormAction(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("/view/item-form.fxml");

            assert resource != null;

            Parent load = FXMLLoader.load(resource);
            loadFormContent.getChildren().clear();
            loadFormContent.getChildren().add(load);
            
        }

        @FXML
        void btnOrderFormAction(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("/view/order-form.fxml");

            assert resource != null;

            Parent load = FXMLLoader.load(resource);
            loadFormContent.getChildren().clear();
            loadFormContent.getChildren().add(load);

        }


        @FXML
        void btnRegisterNewUserAction(ActionEvent event) throws IOException {
            URL resource = this.getClass().getResource("/view/register-form.fxml");

            assert resource != null;

            Parent load = FXMLLoader.load(resource);
            loadFormContent.getChildren().clear();
            loadFormContent.getChildren().add(load);
        }

}

