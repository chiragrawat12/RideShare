package com.rideshare.payment.service;

import com.rideshare.payment.model.Payment;
import com.rideshare.payment.paymentgateway.PaymentProcessor;
import com.rideshare.payment.repository.PaymentRepository;
//import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repo;
    private final PaymentProcessor stripeProcessor;

    public PaymentService(PaymentRepository repo, @Qualifier("stripeProcessor") PaymentProcessor stripeProcessor) {
        this.repo = repo;
        this.stripeProcessor = stripeProcessor;
    }

    public String processPayment(Payment payment) {
        try {
            long amountInCents = (long) (payment.getAmount() * 100);

            String clientSecret = stripeProcessor.createPaymentIntent(
                    amountInCents,
                    "eur",
                    "Ride payment for booking " + payment.getBookingId()
            );

            payment.setPaymentDate(LocalDateTime.now());
            payment.setStatus("PENDING");
            payment.setPaymentMethod("STRIPE");
            payment.setStripePaymentIntentId(clientSecret);

            repo.createPayment(payment);

            return clientSecret;

        } catch (Exception e) {
            e.printStackTrace();
            return "Error processing payment: " + e.getMessage();
        }
    }

    public void updatePaymentStatus(int id, String status) {
        repo.updateStatus(id, status);
    }

    public List<Payment> getAllPayments() {
        return repo.getAllPayments();
    }

    public Payment getPayment(int id) {
        return repo.getPaymentById(id);
    }
}
