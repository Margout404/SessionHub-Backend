package com.GA.gymApp.exceptions;

import com.GA.gymApp.exceptions.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(Exceptions.ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(Exceptions.ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ErrorResponse(404,
                        ex.getMessage(),
                        LocalDateTime.now())
        );
    }


    @ExceptionHandler(Exceptions.ConflictException.class)
    public ResponseEntity<ErrorResponse> handleConflict(Exceptions.ConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(
                        new ErrorResponse(
                                409,
                                ex.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(Exceptions.BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(Exceptions.BadRequestException ex) {
        return ResponseEntity.badRequest()
                .body(
                        new ErrorResponse(
                                400,
                                ex.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(Exceptions.AuthorizationException.class)
    public ResponseEntity<ErrorResponse> handleAuth(Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ErrorResponse(
                                401,
                                ex.getMessage(),
                                LocalDateTime.now()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity.status(500)
                .body(
                        new ErrorResponse(
                                500,
                                "Internal Server Error",
                                LocalDateTime.now()
                        )
                );
    }
}
