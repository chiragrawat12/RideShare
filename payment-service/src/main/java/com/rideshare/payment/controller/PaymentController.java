package com.rideshare.payment.controller;

import com.rideshare.payment.model.Payment;
import com.rideshare.payment.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
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
}
