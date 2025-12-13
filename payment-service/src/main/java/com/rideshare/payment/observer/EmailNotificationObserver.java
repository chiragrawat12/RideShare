package com.rideshare.payment.observer;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationObserver implements PaymentObserver {

    @Override
    public void onPaymentStatusChanged(int paymentId, int userId, String newStatus) {
        System.out.println("Sending email: Payment " + paymentId + " is now " + newStatus);
    }
}