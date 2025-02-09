package controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import java.util.Optional;

public class ItemFormController {

    @FXML
    private TableColumn colCode;

    @FXML
    private TableColumn colDescription;

    @FXML
    private TableColumn colQuantityOnHand;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private TableView tbItems;

    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtQuantityOnHand;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    void btnAddItemAction(ActionEvent event) {
        if (txtCode.getText().isEmpty() || txtDescription.getText().isEmpty() || txtUnitPrice.getText().isEmpty() || txtQuantityOnHand.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "ALL FIELD MUST BE FILLED OUT").show();
        }

        boolean isAddItem = new ItemController().addItem(
                new Item(
                        txtCode.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQuantityOnHand.getText())
                )
        );

        if (isAddItem) {
            new Alert(Alert.AlertType.INFORMATION, "ITEM ADDED").show();
            txtCode.clear();
            txtDescription.clear();
            txtUnitPrice.clear();
            txtQuantityOnHand.clear();
        }

    }

    @FXML
    void btnDeleteItemAction(ActionEvent event) {
        Optional<ButtonType> buttonAlert = new Alert(Alert.AlertType.CONFIRMATION, "ARE YOU SURE YOU WANT TO DELETE THIS", ButtonType.YES, ButtonType.NO).showAndWait();
        ButtonType buttonType = buttonAlert.orElse(ButtonType.NO);

        if (buttonType == ButtonType.YES) {
            if (new ItemController().deleteItem(txtCode.getText())) {
                new Alert(Alert.AlertType.INFORMATION, "ITEM DELETED SUCCESSFULLY").show();
                txtCode.clear();
                txtDescription.clear();
                txtUnitPrice.clear();
                txtQuantityOnHand.clear();
            } else {
                new Alert(Alert.AlertType.ERROR, "FAILED TO DELETE ITEM. PLEASE TRY AGAIN").show();
            }
        }

    }

    public void loadTable() {
        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("discription"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQuantityOnHand.setCellValueFactory(new PropertyValueFactory<>("stock"));

        ObservableList<Item> customerObservableList= FXCollections.observableArrayList();

        new ItemController().getItems().forEach(item -> {
            customerObservableList.add(item);
        });

        tbItems.setItems(customerObservableList);

    }

    @FXML
    void btnReloadItemsAction(ActionEvent event) {
        loadTable();

    }

    @FXML
    void btnSearchItemAction(ActionEvent event) {
        Item searchedItem = new ItemController().searchItem(txtCode.getText());

        if (searchedItem == null) {
            new Alert(Alert.AlertType.INFORMATION, "ITEM NOT FOUND").show();
        }

        txtDescription.setText(searchedItem.getDiscription());
        txtUnitPrice.setText(String.valueOf(searchedItem.getUnitPrice()));
        txtQuantityOnHand.setText(String.valueOf(searchedItem.getStock()));

    }

    @FXML
    void btnUpdateItemAction(ActionEvent event) {
        boolean isUpdateItem = new ItemController().updateItem(
                new Item(
                        txtCode.getText(),
                        txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()),
                        Integer.parseInt(txtQuantityOnHand.getText())
                )
        );

        if (isUpdateItem) {
            new Alert(Alert.AlertType.INFORMATION, "ITEM UPDATED SUCCESSFULLY").show();
            txtCode.clear();
            txtDescription.clear();
            txtUnitPrice.clear();
            txtQuantityOnHand.clear();
        }
    }

}
