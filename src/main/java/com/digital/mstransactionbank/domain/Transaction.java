package com.digital.mstransactionbank.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.Instant;

@Data
@Entity(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private Double value;

    @NotNull
    private Instant transactionTime;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Vendor vendor;
}
