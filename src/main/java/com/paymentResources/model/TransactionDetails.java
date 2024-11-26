package com.paymentResources.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private BigDecimal transactionalValue;
    private String establishment;
    private Integer nsu;
    private Integer authorizationCode;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dateTransaction;

    @Enumerated(EnumType.STRING)
    private StatusTransaction statusTransaction;
}