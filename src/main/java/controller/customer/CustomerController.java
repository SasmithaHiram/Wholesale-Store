package controller.customer;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerController implements CustomerService{
    @Override
    public boolean addCustomer(Customer customer) {
        return false;
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
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
        return false;
    }
}
