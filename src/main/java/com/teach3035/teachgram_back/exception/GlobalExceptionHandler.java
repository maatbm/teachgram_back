package com.teach3035.teachgram_back.exception;

import com.teach3035.teachgram_back.dto.res.ExceptionResDTO;
import com.teach3035.teachgram_back.exception.custom.DuplicateFieldException;
import com.teach3035.teachgram_back.exception.custom.PostNotBelongsToUserException;
import com.teach3035.teachgram_back.exception.custom.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ExceptionResDTO badCredentialsExecptionHandler(BadCredentialsException e) {
        return new ExceptionResDTO(HttpStatus.UNAUTHORIZED.name(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResDTO resourceNotFoundExceptionHandler(ResourceNotFoundException e) {
        return new ExceptionResDTO(HttpStatus.NOT_FOUND.name(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateFieldException.class)
    public ExceptionResDTO duplicateFieldExceptionHandler(DuplicateFieldException e) {
        return new ExceptionResDTO(HttpStatus.CONFLICT.name(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(PostNotBelongsToUserException.class)
    public ExceptionResDTO postNotBelongsToUserExceptionHandler(PostNotBelongsToUserException e){
        return new ExceptionResDTO(HttpStatus.FORBIDDEN.name(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        return errors;
    }
}
