package com.rideshare.payment.observer;

public interface PaymentObserver {
    void onPaymentStatusChanged(int paymentId, int userId, String newStatus);
}
