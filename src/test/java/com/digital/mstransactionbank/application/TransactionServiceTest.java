package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.ResponseDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    TransactionService service;

    TransactionRepository repository;
    AccountService accountService;

    @BeforeEach
    void setup() {
        repository = mock(TransactionRepository.class);
        accountService = mock(AccountService.class);
        service = new TransactionService(repository, accountService);
    }

    @Test
    void authTransaction_valid_transaction() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .transactionTime(ZonedDateTime.now())
                .accountId(1L)
                .vendorId(1L)
                .value(1000.0)
                .build();

        ResponseDTO response = ResponseDTO.builder().success(true).build();

        when(accountService.accountValidation(transactionDTO)).thenReturn(response);

        ResponseDTO result = service.authTransaction(transactionDTO);
        assertTrue(result.isSuccess());
        verify(repository, times(1)).save(any(Transaction.class));
    }


    @Test
    void authTransaction_invalid_transaction() {
        TransactionDTO transactionDTO = TransactionDTO.builder()
                .transactionTime(ZonedDateTime.now())
                .accountId(1L)
                .vendorId(1L)
                .value(1000.0)
                .build();

        ResponseDTO response = ResponseDTO.builder().success(false).build();

        when(accountService.accountValidation(transactionDTO)).thenReturn(response);

        ResponseDTO result = service.authTransaction(transactionDTO);
        assertFalse(result.isSuccess());
        verifyZeroInteractions(repository);
    }
}