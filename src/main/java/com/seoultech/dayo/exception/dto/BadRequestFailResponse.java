package com.seoultech.dayo.exception.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class BadRequestFailResponse {

    private final int status;

    private final String message;

    @Builder
    public BadRequestFailResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
