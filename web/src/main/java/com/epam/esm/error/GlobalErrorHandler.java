package com.epam.esm.error;

import com.epam.esm.security.JwtAuthenticationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalErrorHandler {

    private static final String INVALID_DATA_MESSAGE = "Invalid data";
    private static final String ACCESS_DENY_MESSAGE = "Access denied";

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(new ErrorHandler(INVALID_DATA_MESSAGE, 60), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorHandler> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorHandler(ACCESS_DENY_MESSAGE, 70), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptionJWT(
            Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 70), HttpStatus.FORBIDDEN);
    }

}
