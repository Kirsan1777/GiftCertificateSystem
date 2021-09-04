package com.epam.esm.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Class for catching an exception JwtAuthenticationException
 */
public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}