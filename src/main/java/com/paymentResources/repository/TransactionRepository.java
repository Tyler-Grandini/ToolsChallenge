package com.paymentResources.repository;

import com.paymentResources.dto.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction,UUID> {
}