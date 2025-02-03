package com.david.mbaimbai.exceptions;


import com.david.mbaimbai.constants.Constants;
import com.david.mbaimbai.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(final ResourceNotFoundException resourceNotFoundException,
                                                                            final WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .apiPath(webRequest.getDescription(false))
                        .errorTime(LocalDateTime.now())
                        .statusCode(Constants.STATUS_404)
                        .statusErrorMessage(resourceNotFoundException.getMessage())
                        .build());
    }

    @ExceptionHandler(InvalidUserException.class)
    public ResponseEntity<ErrorResponseDto> AdminUserException(final InvalidUserException adminUserException,
                                                               final WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .apiPath(webRequest.getDescription(false))
                        .errorTime(LocalDateTime.now())
                        .statusCode(Constants.STATUS_404)
                        .statusErrorMessage(adminUserException.getMessage())
                        .build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(final Exception exception,
                                                            final WebRequest webRequest) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDto.builder()
                        .apiPath(webRequest.getDescription(false))
                        .errorTime(LocalDateTime.now())
                        .statusCode(Constants.STATUS_500)
                        .statusErrorMessage(Constants.STATUS_500_MESSAGE)
                        .build());
    }
}
