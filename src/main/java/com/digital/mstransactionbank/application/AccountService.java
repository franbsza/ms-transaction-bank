package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.dtos.AccountDTO;
import com.digital.mstransactionbank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        Account account = accountRepository.save(Account.createInstance(accountDTO));
        return AccountDTO.createInstance(account);
    }
}
