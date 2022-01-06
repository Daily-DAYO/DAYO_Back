package com.seoultech.dayo.exception.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UnauthorizedFailResponse {


    private final int status;

    private final String message;

    @Builder
    public UnauthorizedFailResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
