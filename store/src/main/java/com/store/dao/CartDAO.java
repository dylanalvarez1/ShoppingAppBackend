package com.store.dao;

import com.store.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
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
                "INSERT INTO carts (user, purchased) values (?, ?)",
                user, false);
    }

    public void insertIntoCart(int productId, String username) {
        if(!isCartExists(username)) {
            //If the cart does not already exist, create it first
           this.createCart(username);
        }

        //Find the correct cart for the user
        Cart cart = this.getCartByUsername(username);

        //Insert into orders the item and the cartId
        this.jdbcTemplate.update(
                "INSERT INTO orders (itemId, cartId) values (?, ?)",
                productId, cart.getId());

    }

    public void purchaseCart(int cartId) {
        this.jdbcTemplate.update(
                "UPDATE carts SET purchased = ? WHERE cartId = ?",
                true, cartId);
    }

    public boolean isCartExists(String username) {
        String sql = "SELECT count(*) FROM carts WHERE user = ? AND purchased = ?";
        boolean result = false;

        int count = this.jdbcTemplate.queryForObject(
                sql, new Object[] {username, false}, Integer.class);

        if (count > 0) {
            result = true;
        }

        return result;
    }


    public Cart getCartByUsername(String username) {
        Cart cart = this.jdbcTemplate.queryForObject(
                "SELECT * FROM carts WHERE user = ? AND purchased = ?",
                new Object[]{username, false},
                new RowMapper<Cart>() {
                    public Cart mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Cart cart1 = new Cart(rs.getInt("cartId"), rs.getString("user"));
                        return cart1;
                    }
                });
        return cart;
    }

    public Cart getCartById(int cartId) {
        Cart cart = this.jdbcTemplate.queryForObject(
                "SELECT * FROM carts WHERE cartId = ? AND purchased = ?",
                new Object[]{cartId, false},
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
        Cart cart = this.getCartByUsername(username);

        //Get the DAO for product info retrieval
        ProductDAO productDAO = new ProductDAO();

        //Get the orders (based on the cart id, each cart is identified by an id, and has one user)
        this.jdbcTemplate.query(
                "SELECT * FROM orders WHERE cartId = ?", new Object[] {cart.getId()},
                (rs, rowNum) -> new Order(rs.getInt("orderId"), rs.getInt("itemId"), rs.getInt("cartId"))
        ).forEach(order -> {
            //This should do the query for every item and add it to products,
           products.add(productDAO.getItemById(order.getItemId()));
        });
        return products;
    }

    public boolean deleteItemInCart(int cartId, int productId){
        boolean success = false;
        Cart cart = getCartById(cartId);

        try
        {
            if(isCartExists(cart.getUser()))
            {
                this.jdbcTemplate.update(
                        "DELETE FROM orders WHERE cartId = ? AND itemId = ?", cartId, productId);
                success = true;
            }
            else {
                System.err.println("An error occured: this cart has already been purchased, you cannot remove an order from it");
            }
        }
        catch (RuntimeException runtimeException)
        {
            System.err.println("An exception occurred while trying to delete " + productId +" in cart of user: " + cartId);
        }
        


        return success;
    }

    public Collection<Integer> getCartIdsByProduct(int productId) {
        Collection<Integer> carts = new ArrayList<Integer>();
        //Use orders (which have a productId) to find a cart
        this.jdbcTemplate.query(
                "SELECT cartId FROM orders where itemId = ?", new Object[] {productId},
                (rs, rowNum) -> new Integer(rs.getInt("cartId"))
        ).forEach(cart -> carts.add(cart));
        return carts;
    }

    public Collection<Customer> listCustomersByPurchase(int productId) {
        Collection<Customer> customers = new ArrayList<Customer>();

        //select cartIds where orders have the product that was purchased
        Collection<Integer> cartIds = this.getCartIdsByProduct(productId);
        Collection<Cart> purchasedCarts = new ArrayList<Cart>();

        //select users from carts where products were purchased
        cartIds.forEach(cartId -> {
            this.jdbcTemplate.query("SELECT * FROM carts WHERE cartId = ? AND purchased = ?", new Object[] {cartId, true},
                    (rs,rowNum) -> new Cart(rs.getInt("cartId"), rs.getString("user"))).forEach(newCart -> purchasedCarts.add(newCart));
        });

        purchasedCarts.forEach(cart -> {
            this.jdbcTemplate.query(
                    "SELECT * FROM customers WHERE username = ?", new Object[] {cart.getUser()},
                    (rs, rowNum) -> new Customer(rs.getString("fname"), rs.getString("lname"), rs.getString("username"), rs.getString("email"))
            ).forEach(customer -> {
                customers.add(customer);
            });
        });

        return customers;
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
