package com.paymentResources.dto;

import com.paymentResources.model.Transaction;
import lombok.Data;

@Data
public class TransactionResponse {
    Transaction transaction;
}