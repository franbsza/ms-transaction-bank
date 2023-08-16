package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.dtos.AccountDTO;
import com.digital.mstransactionbank.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountServiceTest {

    AccountService accountService;
    AccountRepository accountRepository;

    @BeforeEach
    void setup() {
        accountRepository = mock(AccountRepository.class);
        accountService = new AccountService(accountRepository);
    }

    @Test
    void createAccount() {
        AccountDTO accountDTO = AccountDTO.builder()
                                        .number("1234")
                                        .availableLimit(1000.0)
                                        .activeCard(true)
                                        .clientName("João")
                                        .documentNumber("12345678910")
                                        .build();
        Account account = Account.createInstance(accountDTO);

        when(accountRepository.save(account))
                .thenReturn(account);

        AccountDTO  accountResponseDTO = accountService.createAccount(accountDTO);
        assertEquals(accountDTO, accountResponseDTO);
    }
}
