package com.paymentResources.dto;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
@Embeddable
public class PaymentMode {

    @Enumerated(EnumType.STRING)
    private TypePayment typePayment;

    private int parcels;
}