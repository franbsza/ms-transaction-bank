package com.digital.mstransactionbank.domain;

import java.util.List;

public class Account {
    private Long number;
    private Double availableLimit;
    private boolean activeCard;
    private List<Transaction> transactions;
}
