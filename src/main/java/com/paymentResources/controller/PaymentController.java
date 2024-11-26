package com.paymentResources.controller;

import com.paymentResources.model.Transaction;
import com.paymentResources.dto.TransactionResponse;
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
    private PaymentService paymentServicee;

    private TransactionResponse transactionResponse = new TransactionResponse();

    @PostMapping
    public ResponseEntity<TransactionResponse> makePayment(@RequestBody Transaction initialTransaction) {
        Transaction finalTransaction = paymentServicee.makePayment(initialTransaction);
        transactionResponse.setTransaction(finalTransaction);
        return ResponseEntity.ok(transactionResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findPayment(@PathVariable("id") UUID paymentId) {
        Transaction finalTransaction =  paymentServicee.findPayment(paymentId);
        transactionResponse.setTransaction(finalTransaction);
        return ResponseEntity.ok(transactionResponse);
    }

    @GetMapping("/all-payments")
    public ResponseEntity<List<TransactionResponse>> findAllPayments() {
        List<Transaction> transactionData = paymentServicee.findAllPayments();

        List<TransactionResponse> responseList = transactionData.stream().map(
        transaction -> {
            TransactionResponse response = new TransactionResponse();
            response.setTransaction(transaction);
            return response;
        }).toList();

        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/refund/{id}")
    public ResponseEntity<TransactionResponse> refundPayment(@PathVariable("id") UUID paymentId) {
        Transaction finalTransaction = paymentServicee.refundPayment(paymentId);
        transactionResponse.setTransaction(finalTransaction);
        return ResponseEntity.ok(transactionResponse);
    }
}