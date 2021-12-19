package com.example.endofgame.controller;

import com.example.endofgame.dto.ErrorResponse;
import com.example.endofgame.exception.BusinessException;
import com.example.endofgame.exception.DeletingNonExistentObject;
import com.example.endofgame.exception.InputValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BusinessControllerAdvice {

    // if there is value property You don't have to type it
//    @ExceptionHandler(value = DeletingNonExistentObject.class)
    @ExceptionHandler(DeletingNonExistentObject.class)
    public ResponseEntity<ErrorResponse> handleNonExistentCategoryEntities(DeletingNonExistentObject exc) {
        ErrorResponse response = createGenericErrorResponse(HttpStatus.NOT_FOUND, exc);
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleGenericClientError(BusinessException exc) {
        ErrorResponse response = createGenericErrorResponse(HttpStatus.BAD_REQUEST, exc);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleNotValidParameters(MethodArgumentNotValidException exc) {
        ErrorResponse response = createGenericErrorResponse(HttpStatus.BAD_REQUEST, new InputValidationException(exc));
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse createGenericErrorResponse(HttpStatus status, BusinessException exc) {
        return new ErrorResponse(
                LocalDateTime.now(),
                "You're do it wrong!!!",
                exc.getMessage(),
                status.value(),
                ServletUriComponentsBuilder.fromCurrentRequest().build().getPath()
        );
    }
}
