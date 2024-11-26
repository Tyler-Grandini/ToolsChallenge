package com.paymentResources.dto;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class Transaction {

    private Integer encryptedCardNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentId;

    @Embedded
    TransactionDetails transactionDetails;

    @Embedded
    private PaymentMode paymentMode;
}