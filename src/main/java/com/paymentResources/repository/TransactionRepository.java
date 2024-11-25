package com.paymentResources.repository;

import com.paymentResources.dto.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction,UUID> {
    Optional<Transaction> findByEncryptedCardNumberAndTransactionDetails_TransactionalValue(
        Integer encryptedCardNumber,
        BigDecimal transactionalValue
    );}