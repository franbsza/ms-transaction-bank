package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.dtos.AccountDTO;
import com.digital.mstransactionbank.dtos.ResponseDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityNotFoundException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static helper.TestHelper.mockAccountDTO;
import static helper.TestHelper.mockTransactionDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountAccountServiceTest {

    AccountService accountService;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void createAccount() {
        AccountDTO accountDTO =  mockAccountDTO();
        Account account = Account.createInstance(accountDTO);

        when(accountRepository.findAccountByNumber(accountDTO.getDocumentNumber()))
                .thenReturn(Optional.empty());
        when(accountRepository.save(account))
                .thenReturn(account);

        AccountDTO  accountResponseDTO = accountService.createAccount(accountDTO);
        assertEquals(accountDTO, accountResponseDTO);
    }

    @Test
    void createAccount_accountAlreadyExists() {
        AccountDTO accountDTO =  mockAccountDTO();
        Account account = Account.createInstance(accountDTO);

        when(accountRepository.findAccountByNumber(accountDTO.getDocumentNumber()))
                .thenReturn(Optional.of(account));

        assertThrows(IllegalArgumentException.class, () -> accountService.createAccount(accountDTO));
    }

    @Test
    void accountValidation_shouldThrowException() {
        TransactionDTO transactionDTO = mockTransactionDTO();

        when(accountRepository.findById(transactionDTO.getAccountId()))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> accountService.accountValidation(transactionDTO));
    }

    @Test
    void updateLimit() {
        Double newLimit = 1000.0;
        Long id = 1L;

        accountService.updateLimit(newLimit, id);

        verify(accountRepository).updateAvailableLimit(newLimit, id);
    }

    @Test
    void transactionValidation_valid_transaction() {
        Account account = Account.createInstance(AccountDTO.builder()
                        .id(1L)
                        .number("1234")
                        .availableLimit(5000.0)
                        .activeCard(true)
                        .clientName("João")
                        .documentNumber("12345678910")
                        .transactions(List.of())
                        .build());

        TransactionDTO transactionDTO = TransactionDTO.builder()
                .transactionTime(ZonedDateTime.now())
                .accountId(1L)
                .vendorId(1L)
                .value(100.0)
                .build();

        when(accountRepository.findById(transactionDTO.getAccountId())).thenReturn(Optional.of(account));

        ResponseDTO response = accountService.accountValidation(transactionDTO);
        assertTrue(response.isStatus());
        verify(accountRepository).updateAvailableLimit(account.getAvailableLimit() - transactionDTO.getValue(), transactionDTO.getAccountId());
    }
}
