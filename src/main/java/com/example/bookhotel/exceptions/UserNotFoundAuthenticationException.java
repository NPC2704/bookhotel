package com.example.bookhotel.exceptions;

import org.springframework.security.core.AuthenticationException;


public class UserNotFoundAuthenticationException extends AuthenticationException {
    public UserNotFoundAuthenticationException(String message) {
        super(message);
    }
}
