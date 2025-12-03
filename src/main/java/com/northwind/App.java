package com.northwind;

import com.northwind.data.CustomerDao;
import com.northwind.model.Customer;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        System.out.println("hi " + args[0] + args[1]);

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        CustomerDao customerDao = new CustomerDao(dataSource);

        List<Customer> customers = customerDao.getAll();

        System.out.println(customers);

    }
}
