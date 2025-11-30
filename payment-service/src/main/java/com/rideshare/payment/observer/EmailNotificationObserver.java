package com.rideshare.payment.observer;

public class EmailNotificationObserver implements PaymentObserver {

    @Override
    public void onPaymentStatusChanged(int paymentId, String newStatus) {
        System.out.println("Sending email: Payment " + paymentId + " is now " + newStatus);
    }
}
