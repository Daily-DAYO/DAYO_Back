package com.seoultech.dayo.exception;

import com.seoultech.dayo.exception.dto.BadRequestFailResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {
            NotExistFolderException.class,
            NotExistMemberException.class
    })
    public ResponseEntity<BadRequestFailResponse> badRequest(Exception e) {
        return ResponseEntity.badRequest()
                .body(BadRequestFailResponse.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message(e.getMessage())
                    .build()
                );
    }



}
