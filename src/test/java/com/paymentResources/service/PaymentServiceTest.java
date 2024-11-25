package com.paymentResources.service;

import com.paymentResources.model.*;
import com.paymentResources.exception.InvalidTransactionException;
import com.paymentResources.exception.TransactionNotFoundException;
import com.paymentResources.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private TransactionRepository transactionRepository;

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
    }

    @Test
    public void whenPaymentIsSuccessful() {
        // Given
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // When
        Transaction result = paymentService.makePayment(transaction);

        // Then
        assertNotNull(result);
        assertEquals(StatusTransaction.AUTHORIZED, result.getTransactionDetails().getStatusTransaction());
        assertEquals(transaction, result);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void whenPaymentTransactionIsNullAndGeneratesError() {
        // Given
        Transaction nullTransaction = null;

        // When / Then
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class, () -> paymentService.makePayment(nullTransaction));
        assertEquals("Transaction data cannot be null!", exception.getMessage());
    }

    @Test
    public void whenPaymentIsSuccessfullyRefunded() {
        // Given
        when(transactionRepository.findById(paymentId)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        // When
        Transaction result = paymentService.refundPayment(paymentId);

        // Then
        assertNotNull(result);
        assertEquals(StatusTransaction.UNAUTHORIZED, result.getTransactionDetails().getStatusTransaction());
        verify(transactionRepository, times(1)).findById(paymentId);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    public void whenRefundPaymentNotFound() {
        // Given
        when(transactionRepository.findById(paymentId)).thenReturn(Optional.empty());

        // When / Then
        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> paymentService.refundPayment(paymentId));
        assertEquals("No transactions found for the payment identified by " + paymentId, exception.getMessage());
    }

    @Test
    public void whenPaymentCanBeFoundSuccessfully() {
        // Given
        when(transactionRepository.findById(paymentId)).thenReturn(Optional.of(transaction));

        // When
        Transaction result = paymentService.findPayment(paymentId);

        // Then
        assertNotNull(result);
        assertEquals(transaction, result);
        verify(transactionRepository, times(1)).findById(paymentId);
    }

    @Test
    public void whenFindPaymentNotFound() {
        // Given
        when(transactionRepository.findById(paymentId)).thenReturn(Optional.empty());

        // When / Then
        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> paymentService.findPayment(paymentId));
        assertEquals("No transactions found for the payment identified by " + paymentId, exception.getMessage());
    }

    @Test
    public void whenPaymentsCanBeFoundSuccessfully() {
        // Given
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        // When
        List<Transaction> result = paymentService.findAllPayments();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(transaction, result.get(0));
        verify(transactionRepository, times(1)).findAll();
    }

    @Test
    public void whenFindPaymentsNotFound() {
        // Given
        when(transactionRepository.findAll()).thenReturn(List.of());

        // When / Then
        TransactionNotFoundException exception = assertThrows(TransactionNotFoundException.class, () -> paymentService.findAllPayments());
        assertEquals("No payment transactions found!", exception.getMessage());
    }

    @Test
    public void whenValidateTransactionWithNullTransaction() {
        // Given
        Transaction nullTransaction = null;

        // When / Then
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class, () -> paymentService.makePayment(nullTransaction));
        assertEquals("Transaction data cannot be null!", exception.getMessage());
    }

    @Test
    public void whenValidateTransactionWithNullFields() {
        // Given
        transaction.setEncryptedCardNumber(null);

        // When / Then
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class, () -> paymentService.makePayment(transaction));
        assertEquals("encryptedCardNumber cannot be null!", exception.getMessage());
    }

    @Test
    public void whenValidateTransactionWithExistingSimilarTransaction() {
        // Given
        when(transactionRepository.findByEncryptedCardNumberAndTransactionDetails_TransactionalValue(
                transaction.getEncryptedCardNumber(), transaction.getTransactionDetails().getTransactionalValue()))
                .thenReturn(Optional.of(transaction));

        // When / Then
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class, () -> paymentService.makePayment(transaction));
        assertEquals("A similar transaction already exists!", exception.getMessage());
    }
}
