package controller.customer;

import db.DBConnection;
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
        return null;
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

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }
}
