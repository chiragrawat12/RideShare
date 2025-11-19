package com.rideshare.payment.model;

import java.time.LocalDateTime;

public class Payment {

    private int id;
    private int userId;
    private int bookingId;
    private double amount;
    private String paymentMethod;
    private LocalDateTime paymentDate;
    private String status;
    private String stripePaymentIntentId;

    public Payment() {}

    public Payment(int id, int userId, int bookingId, double amount, String paymentMethod,
                   LocalDateTime paymentDate, String status, String stripePaymentIntentId) {
        this.id = id;
        this.userId = userId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
        this.status = status;
        this.stripePaymentIntentId = stripePaymentIntentId;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getStripePaymentIntentId() { return stripePaymentIntentId; }
    public void setStripePaymentIntentId(String stripePaymentIntentId) {
        this.stripePaymentIntentId = stripePaymentIntentId;
    }
}
