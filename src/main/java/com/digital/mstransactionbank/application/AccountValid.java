package com.digital.mstransactionbank.application;


public class AccountValid implements ValidationStrategy {

    @Override
    public void isValid(boolean value) {
        if (!value) {
            throw new IllegalArgumentException("Transaction denied: card not active");
        }
    }
}