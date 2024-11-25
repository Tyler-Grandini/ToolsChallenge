package com.paymentResources.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Transaction {

    private String encryptedCardNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    @Embedded
    TransactionDetails transactionDetails;

    @Embedded
    private PaymentMode paymentMode;
}