package controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import java.util.Optional;

public class CustomerFormController {

    @FXML
    private TableColumn colAddress;

    @FXML
    private TableColumn colId;

    @FXML
    private TableColumn colName;

    @FXML
    private TableColumn colSalary;

    @FXML
    private TableView tbCustomers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtID;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSalary;

    @FXML
    void btnAddCustomerAction(ActionEvent event) {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty() || txtAddress.getText().isEmpty() || txtSalary.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "ALL FIE LD MUST BE FILLED OUT").show();
        }

        boolean isCustomerAdd = new CustomerController().addCustomer(new Customer(
                        txtID.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                )
        );

        if (isCustomerAdd) {
            new Alert(Alert.AlertType.INFORMATION, "CUSTOMER ADDED").show();
            txtID.clear();
            txtName.clear();
            txtAddress.clear();
            txtSalary.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "CUSTOMER NOT ADDED").show();
        }

    }

    @FXML
    void btnDeleteCustomerAction(ActionEvent event) {
        Optional<ButtonType> buttonAlert = new Alert(Alert.AlertType.CONFIRMATION, "ARE YOU SURE YOU WANT TO DELETE THIS", ButtonType.YES, ButtonType.NO).showAndWait();
        ButtonType buttonType = buttonAlert.orElse(ButtonType.NO);

        if (buttonType == ButtonType.YES) {
            if (new CustomerController().deleteCustomer(txtID.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "CUSTOMER DELETED SUCCESSFULLY").show();
                txtID.clear();
                txtName.clear();
                txtAddress.clear();
                txtSalary.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "FAILED TO DELETE CUSTOMER. PLEASE TRY AGAIN").show();
            }
        }

    }

    @FXML
    void btnReloadCustomersAction(ActionEvent event) {
    loadTable();

    }

    @FXML
    void btnSearchCustomerAction(ActionEvent event) {
        Customer searchedCustomer = new CustomerController().searchCustomer(txtID.getText());

        if (searchedCustomer == null) {
            new Alert(Alert.AlertType.INFORMATION, "CUSTOMER NOT FOUND").show();
        }

        txtName.setText(searchedCustomer.getName());
        txtAddress.setText(searchedCustomer.getAddress());
        txtSalary.setText(String.valueOf(searchedCustomer.getSalary()));

    }

    @FXML
    void btnUpdateCustomerAction(ActionEvent event) {
        boolean isCustomerUpdate = new CustomerController().updateCustomer(
                new Customer(
                        txtID.getText(),
                        txtName.getText(),
                        txtAddress.getText(),
                        Double.parseDouble(txtSalary.getText())
                )
        );

        if (isCustomerUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "CUSTOMER UPDATED SUCCESSFULLY").show();
            txtID.clear();
            txtName.clear();
            txtAddress.clear();
            txtSalary.clear();
        } else {
            new Alert(Alert.AlertType.ERROR, "FAILED TO UPDATE CUSTOMER").show();
        }

    }

    public void loadTable() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        ObservableList<Customer> customerObservableList= FXCollections.observableArrayList();

        new CustomerController().getAll().forEach(customer -> {
            customerObservableList.add(customer);
        });

        tbCustomers.setItems(customerObservableList);
    }

}
