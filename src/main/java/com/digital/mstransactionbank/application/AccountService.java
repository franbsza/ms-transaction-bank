package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.AccountDTO;
import com.digital.mstransactionbank.dtos.ResponseDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private static final int HIGH_TRANSACTION_LIMIT = 3;
    private static final int DUPLICATE_TRANSACTION_LIMIT = 2;
    private static final int VENDOR_LIMIT = 1;
    private static final int SECONDS_LIMIT = 120;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        accountRepository.findAccountByNumber(accountDTO.getDocumentNumber())
                .ifPresent(account -> {
                    throw new IllegalArgumentException("Account already exists "+ account.getId());
                });

        Account accountResponse = accountRepository.save(Account.createInstance(accountDTO));
        return AccountDTO.createInstance(accountResponse);
    }

    public ResponseDTO accountValidation(TransactionDTO transactionDTO) {

        Optional<Account> account = accountRepository.findById(transactionDTO.getAccountId());
        if (account.isEmpty()) {
            return ResponseDTO.builder().status(false).message("Transaction denied: account not found").build();
        }

        Account accountResponse = account.get();

        validation(new AccountValid(), accountResponse.isActiveCard());
        validation(new AvailableLimit(),transactionDTO.getValue() >= accountResponse.getAvailableLimit());

        validateTransactions(accountResponse, transactionDTO);

        updateLimit(accountResponse.getAvailableLimit() - transactionDTO.getValue(), transactionDTO.getAccountId());
        return ResponseDTO.builder().status(true).message("Transaction approved").build();
    }

    public void validation(ValidationStrategy strategy, boolean value) {
        strategy.isValid(value);
    }

    void updateLimit(Double newLimit, Long id){
        accountRepository.updateAvailableLimit(newLimit, id);
    }

    boolean isVendorDuplicated(Long vendorId, List<Transaction> transactions){

        return transactions.stream()
                .filter(t -> t.getVendorId()
                        .equals(vendorId)).count() > VENDOR_LIMIT;
    }

    long countTransactions(ZonedDateTime transactionTime, List<Transaction> transactions){
        return transactions.stream()
                .map(t -> t.getTransactionTime().plusSeconds(SECONDS_LIMIT))
                .filter(transactionTime::isBefore)
                .count();
    }

    void validateTransactions(Account accountResponse, TransactionDTO transactionDTO){
        if (!accountResponse.getTransactions().isEmpty()) {
            long countTransactions = countTransactions(transactionDTO.getTransactionTime(), accountResponse.getTransactions());

            this.validation(new HighTransaction(), isVendorDuplicated(transactionDTO.getVendorId(), accountResponse.getTransactions())
                    && countTransactions >= DUPLICATE_TRANSACTION_LIMIT);
            this.validation(new HighTransaction(), countTransactions >= HIGH_TRANSACTION_LIMIT);
        }
    }
}
