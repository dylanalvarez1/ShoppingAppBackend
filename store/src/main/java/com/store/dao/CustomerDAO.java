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

    //@Autowired
    public CustomerDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Customer createCustomer(Customer customer){
        this.jdbcTemplate.update(
                "INSERT INTO customers (fname, lname, username, email) values (?, ?, ?, ?)",
                customer.getFname(), customer.getLname(), customer.getUsername(), customer.getEmail());
        return customer;
    }

    public Customer updateCustomer(Customer customer){
        this.jdbcTemplate.update(
                "UPDATE customers SET fname = ?, lname = ?, email = ? WHERE username = ?",
                customer.getFname(), customer.getLname(), customer.getEmail(), customer.getUsername());
        return customer;
    }

    public Customer getCustomer(String username) {
        Customer returnCustomer = this.jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE username = ?",
                new Object[]{username},
                new RowMapper<Customer>() {
                    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Customer customer = new Customer(rs.getString("fname"), rs.getString("lname"), rs.getString("username"), rs.getString("email"));
                        return customer;
                    }
                });
        return returnCustomer;
    }

    public boolean deleteCustomer(String username){
        boolean success = false;
        try
        {
            this.jdbcTemplate.update(
                    "DELETE FROM customers WHERE username = ?", username);
            success = true;
        }
        catch (RuntimeException runtimeException)
        {
            System.err.println("An exception occurred while trying to delete user: " + username);
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
