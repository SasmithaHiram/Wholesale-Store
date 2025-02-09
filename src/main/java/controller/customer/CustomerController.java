package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        String SQL = "INSERT INTO customer VALUES (?, ?, ?, ?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getId());
            preparedStatement.setObject(2, customer.getName());
            preparedStatement.setObject(3, customer.getAddress());
            preparedStatement.setObject(4, customer.getSalary());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean updateCustomer(Customer customer) {
        String SQL = "UPDATE customer SET name=?, address=?, salary=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL);
            preparedStatement.setObject(1, customer.getName());
            preparedStatement.setObject(2, customer.getAddress());
            preparedStatement.setObject(3, customer.getSalary());
            preparedStatement.setObject(4, customer.getId());
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Customer searchCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from customer where id = '" + id + "'");
            resultSet.next();

            return new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getDouble(4)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Customer> getAll() {
        ArrayList<Customer> customerArrayList = new ArrayList<>();

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery("select * from customer");

            while (resulSet.next()) {
                Customer customer = new Customer(resulSet.getString(1),
                        resulSet.getString(2),
                        resulSet.getString(3),
                        resulSet.getDouble(4));
                customerArrayList.add(customer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerArrayList;

    }

    public ObservableList<String> getAllCustomersId() {
        List<Customer> all = getAll();
        ObservableList<String> customerIdList = FXCollections.observableArrayList();
        all.forEach(customer -> {
            customerIdList.add(customer.getId());
        });
        return customerIdList;

    }

    @Override
    public boolean deleteCustomer(String id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
           return connection.createStatement().executeUpdate("DELETE from customer WHERE id='" + id + "'") > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
