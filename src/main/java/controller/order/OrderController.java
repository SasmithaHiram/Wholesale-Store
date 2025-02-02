package controller.order;

import controller.item.ItemController;
import db.DBConnection;
import model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    public boolean placeOrder(Order order) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "INSERT INTO orders VALUES(?, ? ,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, order.getOrderId());
            preparedStatement.setObject(2, order.getDate());
            preparedStatement.setObject(3, order.getCustomerID());
            boolean isOrderAdd = preparedStatement.executeUpdate() > 0;

            if (isOrderAdd) {
                boolean isOrderDetailAdd = new OrderDetailController().addOrderDetail(order.getOrderDetails());
                if (isOrderDetailAdd) {
                    boolean isUpdateStock = new ItemController().updateStock(order.getOrderDetails());
                    if (isUpdateStock) {
                        connection.commit();
                        return true;
                    }
                }
            }
        } finally {
            connection.setAutoCommit(true);
        }
        connection.rollback();
        return false;
    }

}
