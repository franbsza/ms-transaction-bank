package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.AccountRepository;
import com.digital.mstransactionbank.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceTest {

    TransactionService transactionService;
    AccountRepository accountRepository;
    TransactionRepository transactionRepository;
    TransactionDTO transactionDTO;
    Account account;
    Transaction transaction1;
    Transaction transaction2;
    Transaction transaction3;
    Transaction transaction4;

    @BeforeEach
    void setup(){
        transactionRepository = mock(TransactionRepository.class);
        accountRepository = mock(AccountRepository.class);
        transactionService = new TransactionService(transactionRepository, accountRepository);

        transactionDTO = TransactionDTO.builder()
                .value(100.0)
                .transactionTime(ZonedDateTime.now())
                .vendorId(1L)
                .accountId(1L)
                .build();

        account = Account.builder()
                .id(1L)
                .activeCard(true)
                .availableLimit(1000.0)
                .transactions(List.of())
                .build();

        transaction1 = Transaction.builder()
                .id(2L)
                .value(100.0)
                .transactionTime(ZonedDateTime.of(2023, 8, 20, 10, 9, 30, 0, ZonedDateTime.now().getZone()))
                .account(account)
                .vendorId(1L)
                .build();

        transaction2 = Transaction.builder()
                .id(3L)
                .value(100.0)
                .transactionTime(ZonedDateTime.of(2023, 8, 20, 10, 9, 0, 0, ZonedDateTime.now().getZone()))
                .account(account)
                .vendorId(2L)
                .build();

        transaction3 = Transaction.builder()
                .id(4L)
                .value(100.0)
                .transactionTime(ZonedDateTime.of(2023, 8, 20, 10, 8, 45, 0, ZonedDateTime.now().getZone()))
                .account(account)
                .vendorId(3L)
                .build();

        transaction4 = Transaction.builder()
                .id(4L)
                .value(100.0)
                .transactionTime(ZonedDateTime.of(2023, 8, 20, 10, 8, 10, 0, ZonedDateTime.now().getZone()))
                .account(account)
                .vendorId(4L)
                .build();


        account.setTransactions(List.of(transaction1, transaction2, transaction3, transaction4));
    }

    @Test
    @DisplayName("should return false when account does not exist")
    void authTransaction_accountDoesNotExist(){
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(transactionService.authTransaction(transactionDTO));
    }

    @Test
    @DisplayName("should return false when account is not active")
    void authTransaction_accountIsNotActive(){
        Account account = Account.builder()
                .id(1L)
                .activeCard(false)
                .build();

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertFalse(transactionService.authTransaction(transactionDTO));
    }

    @Test
    @DisplayName("should return false when account has no limit")
    void authTransaction_accountHaveNoLimitSufficient(){
        Account account = Account.builder()
                .id(1L)
                .activeCard(true)
                .availableLimit(0.0)
                .build();
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertFalse(transactionService.authTransaction(transactionDTO));
    }

    @Test
    @DisplayName("should return true when account has no transactions yet")
    void authTransaction_accountHasNoTransactionsYet(){

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertTrue(transactionService.authTransaction(transactionDTO));
        verify(accountRepository).updateAvailableLimit( account.getAvailableLimit() - transactionDTO.getValue(), 1L);
        verify(transactionRepository).save(Mockito.any(Transaction.class));
    }

    @Test
    @DisplayName("should return false when transaction is duplicated")
    void authTransaction_transactionDuplicated(){

        transactionDTO.setTransactionTime(ZonedDateTime.of(2023, 8, 20, 10, 10, 0, 0, ZonedDateTime.now().getZone()));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertFalse(transactionService.authTransaction(transactionDTO));
    }

    @Test
    @DisplayName("should return false when transaction is frequent")
    void authTransaction_highFrequentTransaction(){

        transactionDTO.setTransactionTime(ZonedDateTime.of(2023, 8, 20, 10, 10, 0, 0, ZonedDateTime.now().getZone()));

        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        assertFalse(transactionService.authTransaction(transactionDTO));
    }
}
