package com.paymentResources.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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