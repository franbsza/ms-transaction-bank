package com.digital.mstransactionbank.application;


import org.springframework.stereotype.Component;

@Component
public interface ValidationStrategy {

    void isValid(boolean value);
}