package com.store.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
public class ProductDAO {

    private JdbcTemplate jdbcTemplate;
    private static final String driverClassName = "com.mysql.jdbc.Driver";
    private static final String url = "jdbc:mysql://localhost:3306/db_store";
    private static final String dbUsername = "springuser";
    private static final String dbPassword = "ThePassword";


    public ProductDAO() {
        this.jdbcTemplate = new JdbcTemplate(this.getDataSource());
    }


    public ProductDAO(JdbcTemplate jdbcTemp) {
        this.jdbcTemplate = jdbcTemp;
    }

    public Response getAllProducts(){
        Collection<Product> products = new ArrayList<Product>();
        this.jdbcTemplate.query(
                "SELECT * FROM products", new Object[] { },
                (rs, rowNum) -> new Product(rs.getInt("itemId"), rs.getString("name"), rs.getFloat("msrp"), rs.getFloat("salePrice"), rs.getInt("upc"), rs.getString("shortDescription"), rs.getString("brandName"), rs.getString("size"), rs.getString("color"), rs.getString("gender"))
        ).forEach(product -> products.add(product));
        if(products.size() == 0)
        {
            return Response.status(404).build();
        }
        return Response.status(200).entity(products).build();
    }

    public Response getItemByKeyword(String keyword){
        Collection<Product> products = new ArrayList<Product>();
        String query = "SELECT * FROM products WHERE ("
                + "name LIKE '%" + keyword + "%' OR "
                + "shortDescription LIKE '%" + keyword + "%' OR "
                + "brandName LIKE '%" + keyword + "%' OR "
                + "size LIKE '%" + keyword + "%' OR "
                + "color LIKE '%" + keyword + "%' OR "
                + "gender LIKE '%" + keyword + "%');";
        this.jdbcTemplate.query(
                query, new Object[] {},
                (rs, rowNum) -> new Product(rs.getInt("itemId"), rs.getString("name"), rs.getFloat("msrp"), rs.getFloat("salePrice"), rs.getInt("upc"), rs.getString("shortDescription"), rs.getString("brandName"), rs.getString("size"), rs.getString("color"), rs.getString("gender"))
        ).forEach(product -> products.add(product));
        if(products.size() == 0)
        {
            return Response.status(404).build();
        }
        return Response.status(200).entity(products).build();
    }

    public Response getItemById(int itemId){
        if(!isProductExists(itemId))
        {
            return Response.status(404).build();
        }
        String query = "SELECT * FROM products WHERE itemId = ?";
        Product product = this.jdbcTemplate.queryForObject(
                query,
                new Object[]{itemId},
                new RowMapper<Product>() {
                    public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                        Product product1 = new Product(rs.getInt("itemId"), rs.getString("name"), rs.getFloat("msrp"), rs.getFloat("salePrice"), rs.getInt("upc"), rs.getString("shortDescription"), rs.getString("brandName"), rs.getString("size"), rs.getString("color"), rs.getString("gender"));
                        return product1;
                    }
                });
        return Response.status(200).entity(product).build();
    }

    public boolean isProductExists(int id) {
        String sql = "SELECT count(*) FROM products WHERE itemId = ?";
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
