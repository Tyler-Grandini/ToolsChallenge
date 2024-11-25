package com.paymentResources.controller;

import com.paymentResources.dto.Transaction;
import com.paymentResources.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService  paymentServicee;

    @PostMapping
    public ResponseEntity<Transaction> makePayment(@RequestBody Transaction initialTransaction) {
        return ResponseEntity.ok(paymentServicee.makePayment(initialTransaction));
    }

    @GetMapping("/refund/{id}")
    public ResponseEntity<Transaction> refundPayment(@PathVariable @RequestParam UUID paymentId) {
        return ResponseEntity.ok(paymentServicee.refundPayment(paymentId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findPayment(@PathVariable @RequestParam UUID paymentId) {
        return ResponseEntity.ok(paymentServicee.findPayment(paymentId));
    }

    @GetMapping("/all-payments")
    public ResponseEntity<List<Transaction>> findAllPayments() {
        return ResponseEntity.ok(paymentServicee.findAllPayments());
    }
}