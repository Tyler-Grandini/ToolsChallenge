package com.paymentResources.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class PaymentMode {

    @Enumerated(EnumType.STRING)
    @Schema(description = "Payment type", example = "PAY_IN_FULL")
    private TypePayment typePayment;

    @Schema(description = "Number of parcels", example = "1")
    private int parcels;
}