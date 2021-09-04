package com.epam.esm.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * Class for catching exceptions and handling them
 */
@RestControllerAdvice
public class GlobalErrorHandler {

    private static final String INVALID_DATA_MESSAGE = "Invalid data";
    private static final String ACCESS_DENY_MESSAGE = "Access denied";

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleBadCredentialsException(BadCredentialsException exception) {
        return new ResponseEntity<>(new ErrorHandler(INVALID_DATA_MESSAGE, 422), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ErrorHandler> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(new ErrorHandler(ACCESS_DENY_MESSAGE, 401), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleExceptionJWT(
            Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 403), HttpStatus.FORBIDDEN);
    }

}
