package com.digital.mstransactionbank.dtos;

import com.digital.mstransactionbank.domain.Account;
import com.digital.mstransactionbank.domain.Transaction;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    @Hidden
    private Long id;

    @NotNull(message = "Account number is required")
    @Size(max = 16, message = "Account number must be 16 characters")
    private String number;

    @NotNull(message = "Available limit is required")
    @Positive(message = "Available limit must be positive")
    private Double availableLimit;

    private boolean activeCard;

    @NotNull(message = "Client name is required")
    private String clientName;
    @NotNull(message = "Document number is required")
    private String documentNumber;

    @Hidden
    private List<Transaction> transactions;

    public static AccountDTO createInstance(Account account){
        return new AccountDTO(account);
    }

    private AccountDTO (Account account){
        this.id = account.getId();
        this.number = account.getNumber();
        this.availableLimit = account.getAvailableLimit();
        this.activeCard = account.isActiveCard();
        this.clientName = account.getClient().getName();
        this.documentNumber = account.getClient().getDocumentNumber();
        this.transactions = account.getTransactions();
    }
}
