package com.rideshare.payment.controller;

import com.rideshare.payment.model.Payment;
import com.rideshare.payment.observer.PaymentStatusSubject;
import com.rideshare.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;
    private final PaymentStatusSubject subject;

    public PaymentController(PaymentService service, PaymentStatusSubject subject) {
        this.service = service;
        this.subject = subject;
    }

    @PostMapping("/pay")
    public String payWithStripe(@RequestBody Payment payment) {
        return service.processPayment(payment);
    }

    @GetMapping("/all")
    public List<Payment> getPayments() {
        return service.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable int id) {
        return service.getPayment(id);
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam int paymentId, @RequestParam String status) {

        service.updatePaymentStatus(paymentId, status);

        subject.notifyObservers(paymentId, status);

        return "Payment " + paymentId + " updated to: " + status;
    }

}
