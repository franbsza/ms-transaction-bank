package com.digital.mstransactionbank.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResponseDTO {
    private boolean status;
    private String message;
}