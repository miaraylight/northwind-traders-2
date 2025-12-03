package com.northwind.data;

import com.northwind.model.Customer;

import javax.sql.DataSource;
import java.sql.*;
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
             ResultSet resultSet = preparedStatement.executeQuery()
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
        catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }

    public Customer find(String id) {
        Customer customer = null;
        String query = "SELECT * FROM customers WHERE CustomerID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {

            preparedStatement.setString(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                if (resultSet.next()) {
                    customer = new Customer(
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
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public Customer create(Customer customer) {
        String query = """
            INSERT INTO Customers
                (CompanyName, ContactName, ContactTitle, Address, City,
                 Region, PostalCode, Country, Phone, Fax)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            preparedStatement.setString(1, customer.getCompanyName());
            preparedStatement.setString(2, customer.getContactName());
            preparedStatement.setString(3, customer.getContactTitle());
            preparedStatement.setString(4, customer.getAddress());
            preparedStatement.setString(5, customer.getCity());
            preparedStatement.setString(6, customer.getRegion());
            preparedStatement.setString(7, customer.getPostalCode());
            preparedStatement.setString(8, customer.getCountry());
            preparedStatement.setString(9, customer.getPhone());
            preparedStatement.setString(10, customer.getFax());

            preparedStatement.executeUpdate();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()
            ) {
                if(resultSet.next()) {
                    String generatedId = resultSet.getString(1);
                    customer.setCustomerId(generatedId);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
}
