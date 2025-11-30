package com.rideshare.payment.observer;

public interface PaymentObserver {
    void onPaymentStatusChanged(int paymentId, String newStatus);
}
