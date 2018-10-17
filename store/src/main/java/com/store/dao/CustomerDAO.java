package com.store.dao;

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

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.ArrayList;

@Repository
public class CustomerDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/db_store";
    private static final String dbUsername = "springuser";
    private static final String dbPassword = "ThePassword";


    public CustomerDAO() {
        this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
    }


    public CustomerDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Response createCustomer(Customer customer){
        if(this.isCustomerExists(customer.getUsername()))
        {
            //This means that this username already exists
            return Response.status(409).build();
        }
        else
        { //If the user is not in the database, insert it
            this.jdbcTemplate.update(
                    "INSERT INTO customers (fname, lname, username, email) values (?, ?, ?, ?)",
                    customer.getFname(), customer.getLname(), customer.getUsername(), customer.getEmail());
        }

        return Response.status(201).build();
    }

    public Response updateCustomer(Customer customer){
        if(!this.isCustomerExists(customer.getUsername()))
        {
            return Response.status(404).build();
        }

        this.jdbcTemplate.update(
                "UPDATE customers SET fname = ?, lname = ?, email = ? WHERE username = ?",
                customer.getFname(), customer.getLname(), customer.getEmail(), customer.getUsername());
        return Response.status(200).build();
    }

    public Response getCustomer(String username) {
        if(!this.isCustomerExists(username))
        {
            return Response.status(404).build();
        }
        Customer returnCustomer = this.jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE username = ?",
                new Object[]{username},
                new RowMapper<Customer>() {
                    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Customer customer = new Customer(rs.getString("fname"), rs.getString("lname"), rs.getString("username"), rs.getString("email"));
                        return customer;
                    }
                });
        return Response.status(200).entity(returnCustomer).build();
    }

    public Response deleteCustomer(String username){
        if(!this.isCustomerExists(username))
        {
            return Response.status(404).build();
        }
        try
        {
            this.jdbcTemplate.update(
                    "DELETE FROM customers WHERE username = ?", username);
            return Response.status(204).build();

        }
        catch (RuntimeException runtimeException)
        {
            System.err.println("An exception occurred while trying to delete user: " + username);
            return Response.status(404).build();
        }

    }

    public boolean isCustomerExists(String username) {
        String sql = "SELECT count(*) FROM customers WHERE username = ?";
        boolean result = false;

        int count = this.jdbcTemplate.queryForObject(
                sql, new Object[] {username}, Integer.class);

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
