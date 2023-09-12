package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.ResponseDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public ResponseDTO authTransaction(TransactionDTO transactionDTO) {
        transactionDTO.setTransactionTime(ZonedDateTime.now());

        ResponseDTO response = accountService.accountValidation(transactionDTO);
        if(response.isSuccess()){
            transactionRepository.save(Transaction.createInstance(transactionDTO));
            return response;
        }
       return response;
    }
}
