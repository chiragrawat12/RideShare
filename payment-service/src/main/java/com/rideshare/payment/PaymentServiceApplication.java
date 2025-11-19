package com.rideshare.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class PaymentServiceApplication implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Listing columns from table...");

        jdbcTemplate.query("SHOW CREATE TABLE payments", rs -> {
            System.out.println(rs.getString(2));
        });

//        jdbcTemplate.query(sql, new Object[]{tableName}, rs -> {
//            System.out.println("Column: " + rs.getString("COLUMN_NAME"));
//        });
    }
}
