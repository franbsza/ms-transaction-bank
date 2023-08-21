package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.AccountRepository;
import com.digital.mstransactionbank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    public boolean authTransaction(TransactionDTO transactionDTO) {
        Optional<Account> account = accountRepository.findById(transactionDTO.getAccountId());

        if(account.isPresent()){

            if(!account.get().isActiveCard() ||
                    account.get().getAvailableLimit() < transactionDTO.getValue())
                return false;

            if (!account.get().getTransactions().isEmpty()) {
                long countTransactions = countTransactions(transactionDTO.getTransactionTime(), account.get().getTransactions());

                if( countTransactions > 3 ||
                        (isTransactionDuplicated(transactionDTO.getVendorId(), account.get().getTransactions()) && countTransactions > 1) ) {
                    return false;
                }
            }
            accountRepository.updateAvailableLimit(account.get().getAvailableLimit() - transactionDTO.getValue(), account.get().getId());
            transactionRepository.save(Transaction.createInstance(transactionDTO));
            return true;
        }
       return false;
    }


    boolean isTransactionDuplicated(Long vendorId, List<Transaction> transactions){

       return transactions.stream()
               .filter(t -> t.getVendorId()
                .equals(vendorId)).count() > 1;
    }

    long countTransactions(ZonedDateTime transactionTime, List<Transaction> transactions){
        return transactions.stream()
                        .map(t -> t.getTransactionTime().plusSeconds(120))
                        .filter(transactionTime::isBefore)
                        .count();
    }
}
