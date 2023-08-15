package com.digital.mstransactionbank.domain;

import java.time.Instant;

public class Transaction {
    private Long id;
    private Double value;
    private Instant transactionTime;
    private Account account;
    private Vendor vendor;
}
