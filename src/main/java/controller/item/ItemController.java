package controller.item;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.OrderDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemService{
    @Override
    public boolean addItem(Item item) {
        String SQL = "INSERT INTO item VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, item.getCode());
            preparedStatement.setObject(2, item.getDiscription());
            preparedStatement.setObject(3, item.getUnitPrice());
            preparedStatement.setObject(4, item.getStock());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateItem(Item item) {
        String SQL = "UPDATE item SET description=?, unitPrice=? , qtyOnHand=? WHERE code=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, item.getDiscription());
            preparedStatement.setObject(2, item.getUnitPrice());
            preparedStatement.setObject(3, item.getStock());
            preparedStatement.setObject(4, item.getCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean deleteItem(String code) {
        String SQL = "DELETE from item WHERE code='" + code + "'";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            return connection.createStatement().executeUpdate(SQL) > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Item searchItem(String code) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("select * from item where code='" + code + "'");
            resultSet.next();
            return new Item(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getInt(4)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Item> getItems() {
        try {
            ArrayList<Item> itemList = new ArrayList<>();
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM item");

            while (resultSet.next()) {
                itemList.add(
                new Item(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getInt(4)
                ));
            }
            return itemList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ObservableList<String> getItem() {
        ObservableList<String> itemCodeList = FXCollections.observableArrayList();
        List<Item> itemList = getItems();
        itemList.forEach(item -> itemCodeList.add(item.getCode()));
        return itemCodeList;

    }

    public boolean updateStock(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            boolean isUpdateStock = updateStock(orderDetail);

            if (!isUpdateStock) {
                return false;
            }
        }
        return true;

    }

    public boolean updateStock(OrderDetail orderDetail) {
        String SQL = "UPDATE item SET qtyOnHand = qtyOnHand - ? WHERE code = ?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, orderDetail.getQty());
            preparedStatement.setObject(2, orderDetail.getItemCode());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
