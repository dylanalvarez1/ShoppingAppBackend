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

import javax.ws.rs.core.Response;
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


    public CartDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public void createCart(String user){
        this.jdbcTemplate.update(
                "INSERT INTO carts (user, purchased) values (?, ?)",
                user, false);
    }

    public Response insertIntoCart(int productId, String username) {
        //Assure the person who's cart this is is valid
        CustomerDAO customerDAO = new CustomerDAO();
        if(!customerDAO.isCustomerExists(username))
        {
            return Response.status(404).build();
        }
        if(!isValidCartExists(username)) {
            //If the cart does not already exist, create it first
           this.createCart(username);
        }

        //Make sure only valid products are inserted
        ProductDAO productDAO = new ProductDAO();
        if(!productDAO.isProductExists(productId))
        {
            return Response.status(404).build();
        }

        //Find the correct cart for the user
        Cart cart = this.getCartByUsername(username);

        //Insert into orders the item and the cartId
        this.jdbcTemplate.update(
                "INSERT INTO orders (itemId, cartId) values (?, ?)",
                productId, cart.getId());

        return Response.status(201).build();
    }

    public Response purchaseCart(int cartId) {
        //error checking
        Cart cart = getCartById(cartId);
        if(!isCartExists(cartId))
        {
            return Response.status(404).build();
        }
        if(cart == null)
        {
            return Response.status(409).build();
        }

        if(!isValidCartExists(cart.getUser()))
        {
            return Response.status(409).build();
        }
        this.jdbcTemplate.update(
                "UPDATE carts SET purchased = ? WHERE cartId = ?",
                true, cartId);
        return Response.status(200).build();
    }

    public boolean isValidCartExists(String username) {
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
        CustomerDAO customerDAO = new CustomerDAO();
        if(!customerDAO.isCustomerExists(username))
        {
            return null;
        }
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
        if(!isCartExists(cartId))
        {
            return null;
        }
        try{
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
        catch(RuntimeException runtime) {
             return null;
        }

    }

    public Response getProductsByUser(String username){
        Collection<Product> products = new ArrayList<Product>();

        //Get the correct cart based on the username passed in
        Cart cart = this.getCartByUsername(username);

        if(cart == null) {
            return Response.status(404).build();
        }
        if(!isCartExists(cart.getId()))
        {
            return Response.status(404).build();
        }

        //Get the DAO for product info retrieval and error checking
        ProductDAO productDAO = new ProductDAO();

        //Get the orders (based on the cart id, each cart is identified by an id, and has one user)
        this.jdbcTemplate.query(
                "SELECT * FROM orders WHERE cartId = ?", new Object[] {cart.getId()},
                (rs, rowNum) -> new Order(rs.getInt("orderId"), rs.getInt("itemId"), rs.getInt("cartId"))
        ).forEach(order -> {
            //This should do the query for every item and add it to products,
           products.add((Product) productDAO.getItemById(order.getItemId()).getEntity());
        });
        if(products.size() == 0)
        {
            return Response.status(404).build();
        }
        return Response.status(200).entity(products).build();
    }

    public Response deleteItemInCart(int cartId, int productId){
        //Error handling for correct response codes
        if(!isCartExists(cartId))
        {
            return Response.status(404).build();
        }
        Cart cart = getCartById(cartId);
        if(cart == null)
        {
            return Response.status(404).build();
        }
        if(!isValidCartExists(cart.getUser())) {
            return Response.status(404).build();
        }
        ProductDAO productDAO = new ProductDAO();
        if(!productDAO.isProductExists(productId))
        {
            return Response.status(404).build();
        }
        try
        {
            if(isValidCartExists(cart.getUser()))
            {
                this.jdbcTemplate.update(
                        "DELETE FROM orders WHERE cartId = ? AND itemId = ?", cartId, productId);

            }
            else {
                System.err.println("An error occured: this cart has already been purchased, you cannot remove an order from it");
                return Response.status(404).build();
            }
        }
        catch (RuntimeException runtimeException)
        {
            System.err.println("An exception occurred while trying to delete " + productId +" in cart of user: " + cartId);
            return Response.status(404).build();
        }

        return Response.status(204).build();


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

    public Response listCustomersByPurchase(int productId) {
        ProductDAO productDAO = new ProductDAO();
        if(!productDAO.isProductExists(productId))
        {
            return Response.status(404).build();
        }
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

        if(customers.size() == 0)
        {
            Response.status(404).build();
        }

        return Response.status(200).entity(customers).build();
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

    public boolean isOrderExists(int id) {
        String sql = "SELECT count(*) FROM orders WHERE orderId = ?";
        boolean result = false;

        int count = this.jdbcTemplate.queryForObject(
                sql, new Object[] {id}, Integer.class);

        if (count > 0) {
            result = true;
        }

        return result;
    }

    public boolean isCartExists(int id) {
        String sql = "SELECT count(*) FROM carts WHERE cartId = ?";
        boolean result = false;

        int count = this.jdbcTemplate.queryForObject(
                sql, new Object[] {id}, Integer.class);

        if (count > 0) {
            result = true;
        }

        return result;
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
