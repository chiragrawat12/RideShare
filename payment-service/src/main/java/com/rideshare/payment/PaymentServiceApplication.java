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
        System.out.println("Checking & creating payments table...");

        String sql = """
                CREATE TABLE IF NOT EXISTS payments (
                    id INT NOT NULL AUTO_INCREMENT,
                    user_id INT NOT NULL,
                    bookingId INT NOT NULL,
                    amount DECIMAL(8,2) NOT NULL,
                    paymentMethod VARCHAR(25) NOT NULL,
                    paymentDate DATETIME NOT NULL,
                    status VARCHAR(25) NOT NULL,
                    stripe_payment_intent_id VARCHAR(100) DEFAULT NULL,
                    PRIMARY KEY (id)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
                """;

        jdbcTemplate.execute(sql);

        System.out.println("Payments table created or already exists.");
    }
}
