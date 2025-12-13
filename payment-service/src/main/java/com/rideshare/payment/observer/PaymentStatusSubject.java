package com.rideshare.payment.observer;

import java.util.ArrayList;
import java.util.List;

public class PaymentStatusSubject {

    private final List<PaymentObserver> observers = new ArrayList<>();

    public void addObserver(PaymentObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(PaymentObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(int paymentId, int userId, String status) {
        for (PaymentObserver observer : observers) {
            observer.onPaymentStatusChanged(paymentId, userId, status);
        }
    }
}
