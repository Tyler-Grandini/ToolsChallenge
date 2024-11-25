package com.paymentResources.service;

import com.paymentResources.dto.StatusTransaction;
import com.paymentResources.dto.Transaction;
import com.paymentResources.exception.InvalidTransactionException;
import com.paymentResources.exception.TransactionNotFoundException;
import com.paymentResources.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction makePayment(Transaction initialTransaction) {
        validateTransaction(initialTransaction);

        initialTransaction.getTransactionDetails().setNsu(123456789);
        initialTransaction.getTransactionDetails().setAuthorizationCode(147258369);
        initialTransaction.getTransactionDetails().setStatusTransaction(StatusTransaction.AUTHORIZED);

        return transactionRepository.save(initialTransaction);
    }

    private void validateTransaction(Transaction transaction) {
        if (transaction == null) {
            throw new InvalidTransactionException("Transaction data cannot be null!");
        }

        Map<String,Object> fieldsToValidate = Map.of(
            "Payment Id", transaction.getPaymentId(),
            "Encrypted Card Number", transaction.getEncryptedCardNumber(),
            "Transaction Details", transaction.getTransactionDetails(),
            "Payment Mode", transaction.getPaymentMode()
        );

        fieldsToValidate.forEach((field, value) -> {
            if (value == null) {
                throw new InvalidTransactionException(field + " cannot be null!");
            }
        });

        checkSimilarPaymentExists(transaction);
    }

    private void checkSimilarPaymentExists(Transaction transaction) {
        if (transactionRepository.findByEncryptedCardNumberAndTransactionDetails_TransactionalValue(
            transaction.getEncryptedCardNumber(),
            transaction.getTransactionDetails().getTransactionalValue()).isPresent()) {
            throw new InvalidTransactionException("A similar transaction already exists!");
        }
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
}