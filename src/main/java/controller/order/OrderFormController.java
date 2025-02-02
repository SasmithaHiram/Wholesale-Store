package controller.order;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import controller.DashboardFormController;
import controller.customer.CustomerController;
import controller.item.ItemController;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import model.Customer;
import model.Item;
import model.Order;
import model.OrderDetail;
import model.TM.CartTM;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static java.lang.String.format;

public class OrderFormController implements Initializable {

    public JFXTextField txtOrderId;
    @FXML
    private JFXComboBox cmbCustomerId;

    @FXML
    private JFXComboBox cmbItemCode;

    @FXML
    private TableColumn colDiscription;

    @FXML
    private TableColumn colItemCode;

    @FXML
    private TableColumn colQty;

    @FXML
    private TableColumn colTotal;

    @FXML
    private TableColumn colUnitPrice;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblTime;

    @FXML
    private TableView<CartTM> tbCart;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtDiscription;

    @FXML
    private TextField txtName;

    @FXML
    private JFXTextField txtQty;

    @FXML
    private TextField txtStock;

    @FXML
    private TextField txtUnitPrice;

    private void setDateAndTime() {
        Date date = new Date();
        SimpleDateFormat Dateformat = new SimpleDateFormat("yyyy-MM-dd");
        String format = Dateformat.format(date);
        lblDate.setText(format);

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime now = LocalTime.now();
            lblTime.setText(now.getHour()+":"+now.getMinute()+":"+now.getSecond());
        }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }

    private void loadCustomersId() {
        ObservableList<String> allCustomersId = new CustomerController().getAllCustomersId();
        cmbCustomerId.setItems(allCustomersId);

    }
    
    private void loadItemCode() {
        cmbItemCode.setItems(new ItemController().getItem());
    }

    ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @FXML
    void btnAddToCartAction(ActionEvent event) {
        String code = cmbItemCode.getValue().toString();
        String discription = txtDiscription.getText();
        Integer qty = Integer.parseInt(txtQty.getText());
        Double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        Double total = qty * unitPrice;

        cartTMS.add(new CartTM(code, discription, qty, unitPrice, total));

        tbCart.setItems(cartTMS);

        calTotal();
    }

    private void calTotal() {
        Double total = 0.0;
        for (CartTM cartTM : tbCart.getItems()) {
            total += cartTM.getTotal();
        }
        lblNetTotal.setText(total.toString());
    }
    @FXML
    void btnPlaceOrderAction(ActionEvent event) throws SQLException {
        String orderId = txtOrderId.getText();
        String date = lblDate.getText();
        String customerId = cmbCustomerId.getValue().toString();

        List<OrderDetail> orderDetails = new ArrayList<>();

        cartTMS.forEach(cartTM -> {
            orderDetails.add(
            new OrderDetail(
                    orderId,
                    cartTM.getCode(),
                    cartTM.getQty(),
                    cartTM.getUnitPrice()
            )
            );
        });

        Order order = new Order(orderId, date, customerId, orderDetails);

        if (new OrderController().placeOrder(order)) {
            new Alert(Alert.AlertType.INFORMATION, "Order Placed", ButtonType.OK).show();

        } else {
            new Alert(Alert.AlertType.ERROR, "Order Not Placed", ButtonType.OK).show();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDiscription.setCellValueFactory(new PropertyValueFactory<>("discription"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        setDateAndTime();
        loadCustomersId();
        loadItemCode();

        cmbCustomerId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                searchCustomerData(newValue.toString());
            }
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                searchItemData(newValue.toString());
            }
        });
    }

    private void searchItemData(String code) {
       Item item = new ItemController().searchItem(code);
       txtStock.setText(item.getStock().toString());
       txtDiscription.setText(item.getDiscription());
       txtUnitPrice.setText(item.getUnitPrice().toString());
    }

    private void searchCustomerData(String newValue) {
        Customer customer = new CustomerController().searchCustomer(newValue);

        txtName.setText(customer.getName());
        txtAddress.setText(customer.getAddress());
    }

    public void btnCommitAction(ActionEvent actionEvent) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        connection.commit();
    }
}

