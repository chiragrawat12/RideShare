package com.rideshare.payment.service;

import com.rideshare.payment.model.Payment;
import com.rideshare.payment.observer.PaymentStatusSubject;
import com.rideshare.payment.paymentgateway.PaymentProcessor;
import com.rideshare.payment.repository.PaymentRepository;
import com.rideshare.payment.validation.PaymentValidator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final PaymentProcessor stripeProcessor;
    private final PaymentStatusSubject subject;
    private final PaymentValidator validator;

    public PaymentService(PaymentRepository repo, @Qualifier("stripeProcessor") PaymentProcessor stripeProcessor,
                          PaymentStatusSubject subject, PaymentValidator validator) {
        this.repo = repo;
        this.stripeProcessor = stripeProcessor;
        this.subject = subject;
        this.validator = validator;
    }

    public String processPayment(Payment payment) {

        validator.validate(payment);

        try {
            long amountInCents = (long) (payment.getAmount() * 100);

            String intentId = stripeProcessor.createPaymentIntent(amountInCents, "eur",
                    "Ride payment for booking " + payment.getBookingId());

            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentMethod("STRIPE");
            payment.setStatus("SUCCESS");
            payment.setStripePaymentIntentId(intentId);

            int payment_id = repo.createPayment(payment);

            subject.notifyObservers(payment_id, payment.getUserId(), "SUCCESS");

            return "Payment successful";

        } catch (Exception e) {

            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentMethod("STRIPE");
            payment.setStatus("FAILED");
            payment.setStripePaymentIntentId(null);

            int payment_id = repo.createPayment(payment);

            subject.notifyObservers(payment_id, payment.getUserId(), "FAILED");

            return "Payment failed: " + e.getMessage();
        }
    }

    public List<Payment> getAllPayments() {
        return repo.getAllPayments();
    }

    public Payment getPayment(int id) {
        return repo.getPaymentById(id);
    }
}
