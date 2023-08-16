package com.digital.mstransactionbank.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Entity(name = "clients")
@AllArgsConstructor
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String documentNumber;
    @NotNull
    private String name;

    @OneToMany(mappedBy = "client")
    private List<Account> accounts;

    public static  Client createInstance(String documentNumber, String name) {
        return new Client(documentNumber, name);
    }

    private Client(String documentNumber, String name) {
        this.documentNumber = documentNumber;
        this.name = name;
    }
}
