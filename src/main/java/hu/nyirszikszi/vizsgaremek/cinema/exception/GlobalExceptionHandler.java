package hu.nyirszikszi.vizsgaremek.cinema.exception;


import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import hu.nyirszikszi.vizsgaremek.cinema.dto.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@Hidden
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


    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateEmails(
            DuplicateEmailException exception
    ){
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateUsernames(
            DuplicateUsernameException exception
    ){
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(
            UserNotFoundException exception
    ){
        ErrorResponse response = new ErrorResponse(
                exception.getMessage(),
                LocalDateTime.now()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }






}
