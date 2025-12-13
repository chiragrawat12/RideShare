package com.rideshare.payment.validation;

import com.rideshare.payment.model.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentValidator {

    public void validate(Payment payment) {

        if (payment.getUserId() <= 0) {
            throw new IllegalArgumentException("Invalid userId");
        }

        if (payment.getBookingId() <= 0) {
            throw new IllegalArgumentException("Invalid bookingId");
        }

        if (payment.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }
}
