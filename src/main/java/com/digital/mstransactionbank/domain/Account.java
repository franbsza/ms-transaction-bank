package com.digital.mstransactionbank.domain;

import com.digital.mstransactionbank.dtos.AccountDTO;
import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ToString(exclude = {"client", "transactions"})
@Builder
@Entity(name = "accounts")
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 16)
    private String number;

    @NotNull
    @Positive
    private Double availableLimit;

    @NotNull
    private boolean activeCard;

    @Valid
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    public static Account createInstance(AccountDTO accountDTO) {
        return new Account(accountDTO);
    }

    private Account(AccountDTO accountDTO) {
        this.id = accountDTO.getId();
        this.number = accountDTO.getNumber();
        this.availableLimit = accountDTO.getAvailableLimit();
        this.activeCard = accountDTO.isActiveCard();
        this.client =  Client.createInstance(accountDTO.getDocumentNumber(), accountDTO.getClientName());
        this.transactions = accountDTO.getTransactions();
    }
}
