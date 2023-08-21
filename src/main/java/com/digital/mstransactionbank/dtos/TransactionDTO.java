package com.digital.mstransactionbank.dtos;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

@Data
@Builder
public class TransactionDTO {

    @NotNull(message = "Value is required")
    @Positive
    private Double value;

    @Hidden
    private ZonedDateTime transactionTime;

    @NotNull
    private Long accountId;

    @NotNull
    private Long vendorId;
}
