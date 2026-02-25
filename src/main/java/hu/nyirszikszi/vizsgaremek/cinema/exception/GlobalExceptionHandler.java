package hu.nyirszikszi.vizsgaremek.cinema.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentials(
            InvalidCredentialsException exception
    ){
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(response);
    }




}
