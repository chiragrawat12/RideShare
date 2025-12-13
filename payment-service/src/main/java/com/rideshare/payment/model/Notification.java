package com.rideshare.payment.model;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private int paymentId;
    private int userId;
    private String status;
    private String message;
    private LocalDateTime notifiedAt;

    public Notification(int paymentId, int userId, String status, String message, LocalDateTime notifiedAt) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.status = status;
        this.message = message;
        this.notifiedAt = notifiedAt;
    }

    // getters only (no setters needed)
    public int getPaymentId() { return paymentId; }
    public int getUserId() { return userId; }
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getNotifiedAt() { return notifiedAt; }
}
