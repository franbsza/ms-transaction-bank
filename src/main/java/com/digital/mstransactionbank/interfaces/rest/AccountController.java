package com.digital.mstransactionbank.interfaces.rest;

import com.digital.mstransactionbank.application.AccountService;
import com.digital.mstransactionbank.dtos.AccountDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/v1/accounts", produces = "application/json")
public class AccountController {

    private final AccountService accountService;

    @Operation(summary = "Create an account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The product was created"),
            @ApiResponse(responseCode = "400", description = "The request contains invalid parameters.",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)) })})
    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountDTO account) {
        AccountDTO accountDTO = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountDTO);
    }
}
