package com.store.dao;

import com.store.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.store.model.*;
import com.store.dao.*;
import java.util.Collection;
import java.util.ArrayList;



@Repository
public class CartDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/db_store";
    private static final String dbUsername = "springuser";
    private static final String dbPassword = "ThePassword";


    public CartDAO() {
        this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
    }

    //@Autowired
    public CartDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public void createCart(String user){
        this.jdbcTemplate.update(
                "INSERT INTO carts (user) values (?)",
                user);
    }

    public void insertIntoCart(int productId, String username) {
        //String query = "INSERT INTO carts (cartId, user) SELECT * FROM (SELECT '" + productId + "', '"+ username + "') AS tmp WHERE NOT EXISTS ( SELECT user FROM carts WHERE user = '" + username + "') LIMIT 1;";
        if(!isCartExists(username)) {
            //If the cart does not already exist, create it first
           this.createCart(username);
        }

        //Find the correct cart for the user
        Cart cart = this.getCart(username);

        //Insert into orders the item and the cartId
        this.jdbcTemplate.update(
                "INSERT INTO orders (itemId, cartId) values (?, ?)",
                productId, cart.getId());

    }

    public boolean isCartExists(String username) {
        String sql = "SELECT count(*) FROM carts WHERE username = ?";
        boolean result = false;

        int count = this.jdbcTemplate.queryForObject(
                sql, new Object[] { username }, Integer.class);

        if (count > 0) {
            result = true;
        }

        return result;
    }

    /*
    public Customer updateCustomer(Customer customer){
        this.jdbcTemplate.update(
                "UPDATE customers SET fname = ?, lname = ?, email = ? WHERE username = ?",
                customer.getFname(), customer.getLname(), customer.getEmail(), customer.getUsername());
        return customer;
    }
    */

    public Cart getCart(String username) {
        Cart cart = this.jdbcTemplate.queryForObject(
                "SELECT * FROM carts WHERE user = ?",
                new Object[]{username},
                new RowMapper<Cart>() {
                    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Cart cart1 = new Cart(rs.getInt("cartId"), rs.getString("user"));
                        return cart1;
                    }
                });
        return cart;
    }

    public Collection<Product> getProductsByUser(String username){
        Collection<Product> products = new ArrayList<Product>();

        //Get the correct cart based on the username passed in
        Cart cart = this.getCart(username);

        //Get the orders (based on the cart id, each cart is identified by an id, and has one user)
        this.jdbcTemplate.query(
                "SELECT * FROM orders WHERE cartId = ?", new Object[] {cart.getId()},
                (rs, rowNum) -> new Product(rs.getInt("itemId"), rs.getString("name"), rs.getFloat("msrp"), rs.getFloat("salePrice"), rs.getInt("upc"), rs.getString("shortDescription"), rs.getString("brandName"), rs.getString("size"), rs.getString("color"), rs.getString("gender"))
        ).forEach(product -> products.add(product));
        return products;
    }

    public boolean deleteCart(String username){
        boolean success = false;
        try
        {
            this.jdbcTemplate.update(
                    "DELETE FROM carts WHERE user = ?", username);
            success = true;
        }
        catch (RuntimeException runtimeException)
        {
            System.err.println("An exception occurred while trying to delete cart of user: " + username);
        }
        return success;
    }

    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;

    }



}
