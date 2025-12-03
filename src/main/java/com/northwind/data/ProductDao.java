package com.northwind.data;

import com.northwind.model.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {
    private DataSource dataSource;

    public ProductDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    // read
    public List<Product> getAll() {
        List<Product> products = new ArrayList<>();

        String query = "SELECT * FROM products";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {

                Product product = new Product(
                        resultSet.getInt("ProductID"),
                        resultSet.getString("ProductName"),
                        resultSet.getInt("SupplierID"),
                        resultSet.getInt("CategoryID"),
                        resultSet.getString("QuantityPerUnit"),
                        resultSet.getDouble("UnitPrice"),
                        resultSet.getShort("UnitsInStock"),
                        resultSet.getShort("UnitsOnOrder"),
                        resultSet.getShort("ReorderLevel"),
                        resultSet.getBoolean("Discontinued")
                );

                products.add(product);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public Product find(int id) {
        Product product = null;
        String query = "SELECT * FROM products WHERE ProductID = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    product = new Product(
                            resultSet.getInt("ProductID"),
                            resultSet.getString("ProductName"),
                            resultSet.getInt("SupplierID"),
                            resultSet.getInt("CategoryID"),
                            resultSet.getString("QuantityPerUnit"),
                            resultSet.getDouble("UnitPrice"),
                            resultSet.getShort("UnitsInStock"),
                            resultSet.getShort("UnitsOnOrder"),
                            resultSet.getShort("ReorderLevel"),
                            resultSet.getBoolean("Discontinued"));
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    // create
    public Product create(Product product) {
        String query = """
        INSERT INTO products
            (ProductName, SupplierID, CategoryID, QuantityPerUnit,
             UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setObject(2, product.getSupplierId());
            preparedStatement.setObject(3, product.getCategoryId());
            preparedStatement.setString(4, product.getQuantityPerUnit());
            preparedStatement.setDouble(5, product.getUnitPrice());
            preparedStatement.setShort(6, product.getUnitsInStock());
            preparedStatement.setShort(7, product.getUnitsOnOrder());
            preparedStatement.setShort(8, product.getReorderLevel());
            preparedStatement.setBoolean(9, product.getDiscontinued());

            preparedStatement.executeUpdate();

            try (ResultSet rs = preparedStatement.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    product.setProductId(generatedId);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }
}
