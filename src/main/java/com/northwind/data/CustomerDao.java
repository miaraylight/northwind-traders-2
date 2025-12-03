package com.northwind.data;

import javax.sql.DataSource;

public class CustomerDao {
    DataSource dataSource;

    public CustomerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
