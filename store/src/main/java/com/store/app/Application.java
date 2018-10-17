package com.store.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import com.store.dao.*;
import com.store.model.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        //Populate the database by running a sql script
        /*
        log.info("create database");
        jdbcTemplate.execute("source C:/Users/Dylan/Desktop/School/programming/WebAppDevProjects/IndividualProject1/store/src/scripts/createDBTables");

        log.info("populate database");
        jdbcTemplate.execute("source C:/Users/Dylan/Desktop/School/programming/WebAppDevProjects/IndividualProject1/store/src/scripts/populateDBTables");
        */


    }
}
