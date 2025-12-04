package com.northwind;

import com.northwind.data.CustomerDao;
import com.northwind.data.ProductDao;
import com.northwind.model.Customer;
import com.northwind.model.Product;
import org.apache.commons.dbcp2.BasicDataSource;

public class App {
    public static void main(String[] args) {
        System.out.println("hi " + args[0] + args[1]);

        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");
        dataSource.setUsername(args[0]);
        dataSource.setPassword(args[1]);

        CustomerDao customerDao = new CustomerDao(dataSource);
        ProductDao productDao = new ProductDao(dataSource);

//        List<Customer> customers = customerDao.getAll();
//        Customer customerX = customerDao.find("WOLZA");
//        System.out.println(customerX);

        Product newProduct = new Product(
                null,
                "Test Product",   // ProductName (NOT NULL)
                1,                // SupplierID (nullable)
                1,                // CategoryID (nullable)
                "10 boxes",       // QuantityPerUnit
                15.50,            // UnitPrice
                (short) 100,      // UnitsInStock
                (short) 20,       // UnitsOnOrder
                (short) 10,       // ReorderLevel
                false             // Discontinued
        );


        Product toUpd = productDao.add(newProduct);
        toUpd.setProductName("Meow Meow");
        System.out.println(productDao.update(toUpd));
        System.out.println("Product deleted? " + productDao.deleteById(toUpd.getProductId()));
    }
}
