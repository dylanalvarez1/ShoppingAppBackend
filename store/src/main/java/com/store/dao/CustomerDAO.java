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
                "INSERT INTO customers (id, fname, lname, username, email) values (?, ?, ?, ?, ?)",
                customer.getId(), customer.getFname(), customer.getLname(), customer.getUsername(), customer.getEmail());
        return customer;
    }

    public String getCustomer(String username) {
        Customer returnCustomer = this.jdbcTemplate.queryForObject(
                "SELECT * FROM customers WHERE username = ?",
                new Object[]{username},
                new RowMapper<Customer>() {
                    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Customer customer = new Customer(rs.getInt("id"), rs.getString("fname"), rs.getString("lname"), rs.getString("username"), rs.getString("email"));
                        return customer;
                    }
                });
        return returnCustomer.toString();
    }


    /**
     public Collection<Album> getAllAlbums(){
     Collection<Album> albums = new ArrayList<Album>();
     this.jdbcTemplate.query(
     "SELECT * FROM albums", new Object[] { },
     (rs, rowNum) -> new Album(rs.getInt("id"), rs.getString("title"))
     ).forEach(album -> albums.add(album));

     return albums;
     }
     **/

    /***NOTE: For simplicity, other CRUD operations have been removed from this example.***/

    public DriverManagerDataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;

    }



}