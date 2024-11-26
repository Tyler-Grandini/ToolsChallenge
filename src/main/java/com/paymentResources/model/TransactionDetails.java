package com.paymentResources.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TransactionDetails {
    @Schema(description = "Transaction value", example = "500.50")
    private BigDecimal transactionalValue;

    @Schema(description = "Establishment name", example = "Dog World Petshop")
    private String establishment;

    @Schema(description = "NSU", example = "0")
    private Integer nsu;

    @Schema(description = "Authorization code", example = "0")
    private Integer authorizationCode;

    @Schema(description = "Transaction date", example = "01/05/2021 18:30:00")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the transaction", example = "NULL")
    private StatusTransaction statusTransaction;
}