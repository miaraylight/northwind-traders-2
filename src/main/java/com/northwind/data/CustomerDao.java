package com.northwind.data;

import com.northwind.model.Customer;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Customer> getAll() {
        List<Customer> customers = new ArrayList<>();

        String query = "SELECT * FROM customers ";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            try (ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    Customer customer = new Customer(
                            resultSet.getString("CustomerID"),
                            resultSet.getString("CompanyName"),
                            resultSet.getString("ContactName"),
                            resultSet.getString("ContactTitle"),
                            resultSet.getString("Address"),
                            resultSet.getString("City"),
                            resultSet.getString("Region"),
                            resultSet.getString("PostalCode"),
                            resultSet.getString("Country"),
                            resultSet.getString("Phone"),
                            resultSet.getString("Fax"));

                    customers.add(customer);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
