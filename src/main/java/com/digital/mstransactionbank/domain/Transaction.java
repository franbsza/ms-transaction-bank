package com.digital.mstransactionbank.domain;

import com.digital.mstransactionbank.dtos.TransactionDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.ZonedDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Double value;

    @NotNull
    private ZonedDateTime transactionTime;

    @ManyToOne
    private Account account;

    private Long vendorId;

    public static Transaction createInstance(TransactionDTO transactionDTO) {
        return new Transaction(transactionDTO);
    }

    public Transaction(TransactionDTO transactionDTO) {
        this.value = transactionDTO.getValue();
        this.transactionTime = transactionDTO.getTransactionTime();
        this.vendorId = transactionDTO.getVendorId();
        this.account = Account.builder()
                                .id(transactionDTO.getAccountId())
                                .build();
    }
}
