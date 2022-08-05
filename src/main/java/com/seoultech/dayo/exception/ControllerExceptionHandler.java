package com.seoultech.dayo.exception;

import com.seoultech.dayo.exception.dto.BadRequestFailResponse;
import com.seoultech.dayo.exception.dto.ForbiddenFailResponse;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.exception.dto.UnauthorizedFailResponse;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(value = {
      InvalidFolderAccess.class,
      InvalidPostAccess.class,
      ExistEmailException.class,
      IncorrectPasswordException.class
  })
  public ResponseEntity<BadRequestFailResponse> badRequest(Exception e) {
    return ResponseEntity.badRequest()
        .body(BadRequestFailResponse.builder()
            .status(HttpStatus.BAD_REQUEST.value())
            .message(e.getMessage())
            .build()
        );
  }

  @ExceptionHandler(value = {
      NotExistFolderException.class,
      NotExistMemberException.class,
      NotExistPostException.class,
      NotExistFollowerException.class,
      NotExistFollowException.class,
      NotExistPostCategoryException.class,
      NotExistEmailException.class,
      NotExistFolderException.class,
      NotExistHeartException.class,
      NotExistBookmarkException.class,
      NotExistCommentException.class
  })
  public ResponseEntity<NotFoundFailResponse> notFound(Exception e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(NotFoundFailResponse.builder()
            .status(HttpStatus.NOT_FOUND.value())
            .message(e.getMessage())
            .build()
        );
  }

  @ExceptionHandler(value = {
      RuntimeException.class
  })
  public ResponseEntity<BadRequestFailResponse> otherError(Exception e) {
    log.error(Arrays.toString(e.getStackTrace()));
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(BadRequestFailResponse.builder()
            .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .message(e.getMessage())
            .build()
        );
  }

  @ExceptionHandler(value = {
      BindException.class,
      MethodArgumentNotValidException.class
  })
  public ResponseEntity validationError(BindException e) {
    BindingResult bindingResult = e.getBindingResult();

    StringBuilder builder = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
      builder.append("[");
      builder.append(fieldError.getField());
      builder.append("](은)는 ");
      builder.append(fieldError.getDefaultMessage());
      builder.append(" 입력된 값: [");
      builder.append(fieldError.getRejectedValue());
      builder.append("]\n");
    }
    return ResponseEntity.badRequest().body(builder.toString());
  }


}
