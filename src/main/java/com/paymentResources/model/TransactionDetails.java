package com.paymentResources.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Embeddable
public class TransactionDetails {
    private BigDecimal transactionalValue;
    private LocalDateTime dateTransaction;
    private String establishment;
    private Integer nsu;
    private Integer authorizationCode;

    @Enumerated(EnumType.STRING)
    private StatusTransaction statusTransaction;
}