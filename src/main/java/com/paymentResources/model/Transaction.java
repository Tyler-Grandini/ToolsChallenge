package com.paymentResources.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Schema(description = "Encrypted card number", example = "4444********1234")
    private String encryptedCardNumber;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "Payment ID", example = "ba3c44ce-c421-4033-a6b3-0fb6b93908bd")
    private UUID paymentId;

    @Embedded
    TransactionDetails transactionDetails;

    @Embedded
    private PaymentMode paymentMode;
}