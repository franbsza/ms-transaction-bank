package com.digital.mstransactionbank.interfaces.rest;

import com.digital.mstransactionbank.application.TransactionService;
import com.digital.mstransactionbank.dtos.ResponseDTO;
import com.digital.mstransactionbank.dtos.TransactionDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/transactions", produces = "application/json")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ResponseDTO> authTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {

        ResponseDTO response = transactionService.authTransaction(transactionDTO);

        if (response.isSuccess())
            return ResponseEntity.ok(response);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    }
}
