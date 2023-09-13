package com.digital.mstransactionbank.application;

public class AvailableLimit implements ValidationStrategy {
    @Override
    public void isValid(boolean value) {
        if(value){
            throw new IllegalArgumentException("Transaction denied: insufficient funds");
        }
    }
}
