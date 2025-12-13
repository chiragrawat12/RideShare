package com.rideshare.payment.observer;

import org.springframework.stereotype.Component;

@Component
public class RideStatusUpdateObserver implements PaymentObserver {

    @Override
    public void onPaymentStatusChanged(int paymentId, int userId, String newStatus) {
        System.out.println(
                "Updating ride status for payment " + paymentId + " → " + newStatus
        );
    }
}