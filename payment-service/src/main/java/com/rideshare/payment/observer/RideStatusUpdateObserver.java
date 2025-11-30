package com.rideshare.payment.observer;

public class RideStatusUpdateObserver implements PaymentObserver {

    @Override
    public void onPaymentStatusChanged(int paymentId, String newStatus) {
        System.out.println("Updating ride status for payment " + paymentId + " → " + newStatus);
    }
}
