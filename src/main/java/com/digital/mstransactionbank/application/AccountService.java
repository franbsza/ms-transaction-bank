package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.AccountDTO;
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
                .ifPresent((account) -> {
                    throw new IllegalArgumentException("Account already exists "+ account.getId());
                });

        Account accountResponse = accountRepository.save(Account.createInstance(accountDTO));
        return AccountDTO.createInstance(accountResponse);
    }


    void updateLimit(Double newLimit, Long id){
        accountRepository.updateAvailableLimit(newLimit, id);
    }

    boolean isTransactionDuplicated(Long vendorId, List<Transaction> transactions){

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

    public boolean accountValidation(TransactionDTO transactionDTO) {

       Optional<Account> account = accountRepository.findById(transactionDTO.getAccountId());

        if (account.isPresent() && account.get().isActiveCard() &&
                account.get().getAvailableLimit() >= transactionDTO.getValue() &&
                transactionValidation(account.get(), transactionDTO)) {
                     updateLimit(transactionDTO.getValue(), transactionDTO.getAccountId());
                     return true;
                }

        return false;
    }

    boolean transactionValidation(Account account, TransactionDTO transactionDTO) {
        if (!account.getTransactions().isEmpty()) {
            long countTransactions = countTransactions(transactionDTO.getTransactionTime(), account.getTransactions());

            return countTransactions <= HIGH_TRANSACTION_LIMIT &&
                    (!isTransactionDuplicated(transactionDTO.getVendorId(), account.getTransactions()) || countTransactions <= DUPLICATE_TRANSACTION_LIMIT);
        }
        return true;
    }
}
