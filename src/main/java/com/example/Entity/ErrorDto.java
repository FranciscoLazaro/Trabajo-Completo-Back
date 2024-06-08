package com.example.Entity;

import lombok.Builder;

@Builder
public class ErrorDto {
    private String errorMsg;
    private String errorClass;
}