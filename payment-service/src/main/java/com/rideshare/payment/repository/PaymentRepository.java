package com.rideshare.payment.repository;

import com.rideshare.payment.model.Payment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PaymentRepository {

    private final JdbcTemplate jdbc;

    public PaymentRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public int createPayment(Payment payment) {
        String sql = "INSERT INTO payments(user_id, bookingId, amount, paymentMethod, paymentDate, status, stripe_payment_intent_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbc.update(sql,
                payment.getUserId(),
                payment.getBookingId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                Timestamp.valueOf(payment.getPaymentDate()),
                payment.getStatus(),
                payment.getStripePaymentIntentId()
        );

        Integer paymentId = jdbc.queryForObject(
                "SELECT LAST_INSERT_ID()",
                Integer.class
        );

        return paymentId;
    }

    public void updateStatus(int id, String status) {
        String sql = "UPDATE payments SET status = ? WHERE id = ?";
        jdbc.update(sql, status, id);
    }

    public List<Payment> getAllPayments() {
        String sql = "SELECT * FROM payments";

        return jdbc.query(sql, (rs, rowNum) ->
                new Payment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("bookingId"),
                        rs.getDouble("amount"),
                        rs.getString("paymentMethod"),
                        rs.getTimestamp("paymentDate").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getString("stripe_payment_intent_id")
                )
        );
    }

    public Payment getPaymentById(int id) {
        String sql = "SELECT * FROM payments WHERE id = ?";

        return jdbc.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new Payment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getInt("bookingId"),
                        rs.getDouble("amount"),
                        rs.getString("paymentMethod"),
                        rs.getTimestamp("paymentDate").toLocalDateTime(),
                        rs.getString("status"),
                        rs.getString("stripe_payment_intent_id")
                )
        );
    }
}
