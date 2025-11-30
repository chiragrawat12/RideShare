package com.rideshare.payment.paymentgateway;

public interface PaymentProcessor {
    String createPaymentIntent(long amountInCents, String currency, String description) throws Exception;
}