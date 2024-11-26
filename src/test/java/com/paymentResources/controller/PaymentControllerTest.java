package com.paymentResources.controller;

import com.paymentResources.dto.TransactionResponse;
import com.paymentResources.model.*;
import com.paymentResources.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {
    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private TransactionResponse transactionResponse;

    private Transaction transaction;

    private UUID paymentId;

    @BeforeEach
    public void setUp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateString = "01/05/2021 18:30:00";
        LocalDateTime dateTransaction = LocalDateTime.parse(dateString, formatter);

        TransactionDetails transactionDetails = new TransactionDetails();
        transactionDetails.setTransactionalValue(new BigDecimal("500.50"));
        transactionDetails.setDateTransaction(dateTransaction );
        transactionDetails.setEstablishment("Petshop Mundo Cão");
        transactionDetails.setNsu(1234567890);
        transactionDetails.setAuthorizationCode(147258369);
        transactionDetails.setStatusTransaction(StatusTransaction.AUTHORIZED);

        PaymentMode paymentMode = new PaymentMode();
        paymentMode.setTypePayment(TypePayment.PAY_IN_FULL);
        paymentMode.setParcels(1);

        paymentId = UUID.fromString("123e4567-e89b-12d3-a410-002356890001");

        transaction = new Transaction();
        transaction.setEncryptedCardNumber("4444********1234");
        transaction.setPaymentId(paymentId);
        transaction.setTransactionDetails(transactionDetails);
        transaction.setPaymentMode(paymentMode);

        transactionResponse = new TransactionResponse();
        transactionResponse.setTransaction(transaction);
    }

    @Test
    public void whenPaymentIsSuccessful() {
        // Given
        when(paymentService.makePayment(any(TransactionResponse.class))).thenReturn(transaction);

        // When
        ResponseEntity<TransactionResponse> response = paymentController.makePayment(transactionResponse);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(paymentService, times(1)).makePayment(any(TransactionResponse.class));
    }

    @Test
    public void whenPaymentCanBeFoundSuccessfully() {
        // Given
        when(paymentService.findPayment(paymentId)).thenReturn(transaction);

        // When
        ResponseEntity<TransactionResponse> response = paymentController.findPayment(paymentId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(paymentService, times(1)).findPayment(paymentId);
    }

    @Test
    public void whenPaymentsCanBeFoundSuccessfully() {
        // Given
        when(paymentService.findAllPayments()).thenReturn(List.of(transaction));

        // When
        ResponseEntity<List<TransactionResponse>> response = paymentController.findAllPayments();

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(Objects.requireNonNull(response.getBody()).isEmpty());
        verify(paymentService, times(1)).findAllPayments();
    }

    @Test
    public void whenPaymentIsSuccessfullyRefunded() {
        // Given
        when(paymentService.refundPayment(paymentId)).thenReturn(transaction);

        // When
        ResponseEntity<TransactionResponse> response = paymentController.refundPayment(paymentId);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(paymentService, times(1)).refundPayment(paymentId);
    }
}
