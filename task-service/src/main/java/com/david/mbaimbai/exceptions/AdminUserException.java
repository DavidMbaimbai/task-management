package com.david.mbaimbai.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdminUserException extends RuntimeException {
    public AdminUserException(final String message) {
        super(message);
    }
}
