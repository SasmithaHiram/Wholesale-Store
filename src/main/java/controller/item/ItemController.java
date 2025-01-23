package controller.item;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ItemController implements ItemService{
    @Override
    public boolean addItem() {
        return false;
    }

    @Override
    public boolean updateItem() {
        return false;
    }

    @Override
    public boolean deleteItem() {
        return false;
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
}
