package com.paymentResources.service;

import com.paymentResources.exception.InvalidTransactionException;
import com.paymentResources.exception.TransactionNotFoundException;
import com.paymentResources.model.StatusTransaction;
import com.paymentResources.model.Transaction;
import com.paymentResources.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction makePayment(Transaction initialTransaction) {
        validateTransaction(initialTransaction);

        initialTransaction.getTransactionDetails().setTransactionalValue(reduceValue(initialTransaction.getTransactionDetails().getTransactionalValue()));
        initialTransaction.getTransactionDetails().setNsu(123456789);
        initialTransaction.getTransactionDetails().setAuthorizationCode(147258369);
        initialTransaction.getTransactionDetails().setStatusTransaction(StatusTransaction.AUTHORIZED);

        return transactionRepository.save(initialTransaction);
    }

    private void validateTransaction(Transaction initialTransaction) {
        if (initialTransaction == null) {
            throw new InvalidTransactionException("Transaction data cannot be null!");
        }

        if (initialTransaction.getEncryptedCardNumber() == null) {
            throw new InvalidTransactionException("Encrypted Card Number cannot be null!");
        }

        if (initialTransaction.getTransactionDetails() == null) {
            throw new InvalidTransactionException("Transaction Details cannot be null!");
        }

        if (initialTransaction.getPaymentMode() == null) {
            throw new InvalidTransactionException("Payment Mode cannot be null!");
        }

        checkSimilarPaymentExists(initialTransaction);
    }


    private void checkSimilarPaymentExists(Transaction initialTransaction) {
        transactionRepository.findByEncryptedCardNumberAndTransactionDetails_TransactionalValue(
           initialTransaction.getEncryptedCardNumber(),
           initialTransaction.getTransactionDetails().getTransactionalValue()
        ).ifPresent(existingTransaction -> {
            throw new InvalidTransactionException(
                String.format("A similar transaction already exists! Transaction ID: %s",
                existingTransaction.getPaymentId()));
        });
    }

    public Transaction refundPayment(UUID paymentId) {
        Transaction transaction =
        transactionRepository.findById(paymentId)
        .orElseThrow(() -> new TransactionNotFoundException("No transactions found for the payment identified by " + paymentId));

        transaction.getTransactionDetails().setStatusTransaction(StatusTransaction.UNAUTHORIZED);

        return transactionRepository.save(transaction);
    }

    public Transaction findPayment(UUID paymentId) {
        return transactionRepository.findById(paymentId)
        .orElseThrow(() -> new TransactionNotFoundException("No transactions found for the payment identified by " + paymentId));
    }

    public List<Transaction> findAllPayments() {
        List<Transaction> transactions = transactionRepository.findAll();
        if(transactions.isEmpty()) {
           throw new TransactionNotFoundException("No payment transactions found!");
        }
        return transactions;
    }

    private BigDecimal reduceValue(BigDecimal transactionalValue) {
        if(transactionalValue.equals(new BigDecimal("500.5"))) {
            return new BigDecimal("50.00");
        }
        return transactionalValue;
    }
}