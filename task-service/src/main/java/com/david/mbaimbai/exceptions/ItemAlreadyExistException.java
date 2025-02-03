package com.david.mbaimbai.exceptions;

public class ItemAlreadyExistException extends RuntimeException {
    public ItemAlreadyExistException(String simpleName,
                                     String email) {
        super("%s with name %s already exist".formatted(simpleName, email));
    }
}
