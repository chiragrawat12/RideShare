package com.rideshare.payment.config;

import com.rideshare.payment.observer.PaymentStatusSubject;
import com.rideshare.payment.observer.RideStatusUpdateObserver;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.stripe.Stripe;

@Configuration
public class StripeConfig {

    @Value("${stripe.secret}")
    private String stripeSecret;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeSecret;
    }

    @Bean
    public PaymentStatusSubject paymentStatusSubject() {
        PaymentStatusSubject subject = new PaymentStatusSubject();

//        subject.addObserver(new EmailNotificationObserver());
        subject.addObserver(new RideStatusUpdateObserver());

        return subject;
    }
}
