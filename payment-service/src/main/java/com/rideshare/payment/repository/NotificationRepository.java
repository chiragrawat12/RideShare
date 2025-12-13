package com.rideshare.payment.repository;

import com.rideshare.payment.model.Notification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class NotificationRepository {

    private final JdbcTemplate jdbc;

    public NotificationRepository(JdbcTemplate jdbcTemplate) {
        this.jdbc = jdbcTemplate;
    }

    public void save(Notification notification) {

        String sql = """
            INSERT INTO notify (payment_id, user_id, status, message, notified_at)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbc.update(
                sql,
                notification.getPaymentId(),
                notification.getUserId(),
                notification.getStatus(),
                notification.getMessage(),
                Timestamp.valueOf(notification.getNotifiedAt())
        );
    }
}
