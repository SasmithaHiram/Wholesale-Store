package controller.order;

import db.DBConnection;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailController {
    public boolean addOrderDetail(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isAddOrderDetail = addOrderDetail(orderDetail);

            if (!isAddOrderDetail) {
                return false;
            }
        }
        return true;
    }

    public boolean addOrderDetail(OrderDetail orderDetail) {
        String SQL = "INSERT INTO orderdetail VALUES(?, ?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, orderDetail.getOrderId());
            preparedStatement.setObject(2, orderDetail.getItemCode());
            preparedStatement.setObject(3, orderDetail.getQty());
            preparedStatement.setObject(4, orderDetail.getUnitPrice());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    
}
