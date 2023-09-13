package com.digital.mstransactionbank.application;

public class HighTransaction implements ValidationStrategy {
    @Override
    public void isValid(boolean value) {
        if (value) {
            throw new IllegalArgumentException("Transaction denied: transaction limit exceeded");
        }
    }
}
