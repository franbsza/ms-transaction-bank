package com.digital.mstransactionbank.dtos;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.domain.Vendor;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
public class TransactionDTO {

    private Long id;

    @NotNull
    @Positive
    private Double value;

    @NotNull
    private Instant transactionTime;

    @NotEmpty
    @Valid
    private Account account;

    @NotEmpty
    @Valid
    private Vendor vendor;
}
