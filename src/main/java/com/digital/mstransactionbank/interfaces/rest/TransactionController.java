package com.digital.mstransactionbank.interfaces.rest;

import com.digital.mstransactionbank.application.TransactionService;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/transactions", produces = "application/json")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(consumes = "application/json")
    public HttpStatus authTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        if (transactionService.authTransaction(transactionDTO))
            return HttpStatus.OK;
        return HttpStatus.UNPROCESSABLE_ENTITY;
    }
}
