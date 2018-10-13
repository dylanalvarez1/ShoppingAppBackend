package com.store.dao;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import com.store.model.*;
import java.util.Collection;
import java.util.ArrayList;

@Repository
public class ProductDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/db_store";
    private static final String dbUsername = "springuser";
    private static final String dbPassword = "ThePassword";


    public ProductDAO() {
        this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
    }

    //@Autowired
    public ProductDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Collection<Product> getAllProducts(){
        Collection<Product> products = new ArrayList<Product>();
        this.jdbcTemplate.query(
                "SELECT * FROM products", new Object[] { },
                (rs, rowNum) -> new Product(rs.getInt("itemId"), rs.getString("name"), rs.getFloat("msrp"), rs.getFloat("salePrice"), rs.getInt("upc"), rs.getString("shortDescription"), rs.getString("brandName"), rs.getString("size"), rs.getString("color"), rs.getString("gender"))
        ).forEach(product -> products.add(product));
        return products;
    }

    public Collection<Product> getItemByKeyword(String keyword){
        Collection<Product> products = new ArrayList<Product>();
        //String query = "SELECT * FROM products WHERE MATCH (name, shortDescription, brandName, size, color, gender) AGAINST ('" +keyword +"');";
        String query = "SELECT * FROM products WHERE ("
                + "name LIKE '%" + keyword + "%' OR "
                + "shortDescription LIKE '%" + keyword + "%' OR "
                + "brandName LIKE '%" + keyword + "%' OR "
                + "size LIKE '%" + keyword + "%' OR "
                + "color LIKE '%" + keyword + "%' OR "
                + "gender LIKE '%" + keyword + "%');";
        this.jdbcTemplate.query(
                query, new Object[] { },
                (rs, rowNum) -> new Product(rs.getInt("itemId"), rs.getString("name"), rs.getFloat("msrp"), rs.getFloat("salePrice"), rs.getInt("upc"), rs.getString("shortDescription"), rs.getString("brandName"), rs.getString("size"), rs.getString("color"), rs.getString("gender"))
        ).forEach(product -> products.add(product));
        return products;
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
