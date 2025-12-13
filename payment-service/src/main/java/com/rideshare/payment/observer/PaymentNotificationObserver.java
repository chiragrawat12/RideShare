package com.rideshare.payment.observer;

import com.rideshare.payment.model.Notification;
//import com.rideshare.payment.model.Payment;
import com.rideshare.payment.repository.NotificationRepository;
import com.rideshare.payment.repository.PaymentRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PaymentNotificationObserver implements PaymentObserver {

    private final NotificationRepository notificationRepo;
    private final PaymentRepository paymentRepo;

    public PaymentNotificationObserver(NotificationRepository notificationRepo, PaymentRepository paymentRepo) {
        this.notificationRepo = notificationRepo;
        this.paymentRepo = paymentRepo;
    }

    @Override
    public void onPaymentStatusChanged(int paymentId, int userId, String status) {

        Notification notification = new Notification(paymentId, userId, status, "Payment status updated to " + status, LocalDateTime.now());

        notificationRepo.save(notification);

        System.out.println("Notification saved for payment " + paymentId + " and user " + userId);
    }
}
