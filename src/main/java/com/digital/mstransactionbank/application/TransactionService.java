package com.digital.mstransactionbank.application;

import com.digital.mstransactionbank.domain.Transaction;
import com.digital.mstransactionbank.dtos.ResponseDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import com.digital.mstransactionbank.repository.TransactionRepository;
import lombok.AllArgsConstructor;

import java.time.ZonedDateTime;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;

    public ResponseDTO authTransaction(TransactionDTO transactionDTO) {
        transactionDTO.setTransactionTime(ZonedDateTime.now());

        ResponseDTO response = accountService.accountValidation(transactionDTO);
        if(response.isStatus()){
            transactionRepository.save(Transaction.createInstance(transactionDTO));
            return response;
        }
       return response;
    }
}
