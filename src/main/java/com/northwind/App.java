package com.northwind;

import com.northwind.data.CustomerDao;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        System.out.println("hi " + args[0] + args[1]);

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        CustomerDao customerDao = new CustomerDao(dataSource);

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(
                             "SELECT CompanyName, ContactName FROM customers " +
                                     "WHERE CompanyName LIKE ?");
        ) {
            preparedStatement.setString(1, "%ba%");
            try (ResultSet resultSet = preparedStatement.executeQuery()
            ) {
                while (resultSet.next()) {
                    System.out.printf("name = %s, name2 = %s;\n",
                            resultSet.getString(1), resultSet.getString(2));
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
